package org.example.eventgenerator.consumer;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.example.eventgenerator.common.event.Event;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Event Consumer.<br/>
 * Consumes events and write its to JSON file.
 */
public class EventConsumer implements Runnable {
    private final Logger logger = Logger.getLogger(EventConsumer.class.getSimpleName());
    private final ListeningExecutorService queueService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
    private final BlockingQueue<Event> eventQueue;
    private final AtomicBoolean isShoutDownSubmitted = new AtomicBoolean(false);
    private final String eventsFileName;

    /**
     * Constructs the consumer for the specified  event queue and file name.
     *
     * @param eventQueue     queue for consume events
     * @param eventsFileName file name to write events
     */
    public EventConsumer(final BlockingQueue<Event> eventQueue, final String eventsFileName) {
        this.eventQueue = eventQueue;
        this.eventsFileName = eventsFileName;
    }

    @Override
    public void run() {
        try (FileWriter eventsFileWriter = new FileWriter(eventsFileName)) {
            JsonFileEventSerializer eventSerializer = new JsonFileEventSerializer(eventsFileWriter);
            while (!(isShoutDownSubmitted.get() && eventQueue.isEmpty())) {
                final Event event = eventQueue.poll(1, TimeUnit.SECONDS);
                if (event != null) {
                    logger.log(Level.INFO, "Received {0} event with interactionId={1}", new Object[]{event.getEventType(), event.getInteractionId()});
                    eventSerializer.serialize(event);
                }
            }
            eventSerializer.close();
            queueService.shutdown();
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Exception: ", e);
        }

    }

    /**
     * Submits shout down signal.
     * Consumer will be shouted down when all events will are consumed.
     */
    public void submitShoutDown() {
        isShoutDownSubmitted.set(true);
    }
}
