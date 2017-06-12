package org.example.eventgenerator.producer;

import org.example.eventgenerator.common.event.Event;
import org.example.eventgenerator.common.event.EventType;
import org.example.eventgenerator.producer.event.ProducedEventBuilder;
import org.joda.time.DateTime;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Produces 'JOIN' event based on 'START' event:
 * <ol>
 * <li>Generates 'JOIN' event</li>
 * <li>Put the event in the specified queue</li>
 * <li>Returns the event</li>
 * </ol>
 */
public class JoinEventProducer extends AbstractEventProducer implements Callable<Event> {
    private final Event startEvent;

    public JoinEventProducer(final Event startEvent, final BlockingQueue<Event> eventQueue) {
        super(eventQueue);
        this.startEvent = startEvent;
    }

    @Override
    public Event call() throws Exception {
        final DateTime currentTime = DateTime.now();
        final Event joinEvent = new ProducedEventBuilder(startEvent)
                .setEventType(EventType.JOIN)
                .setEventTimeStamp(currentTime)
                .setDeliveryTime(currentTime)
                .setRandomAgentId()
                .build();
        eventQueue.put(joinEvent);
        return joinEvent;
    }

    /**
     * Produces random delay between 3 and 10 seconds.
     *
     * @return the delay
     */
    @Override
    public long getDelaySeconds() {
        return ThreadLocalRandom.current().nextLong(3, 11);
    }
}
