package org.example.eventgenerator.producer;

import org.example.eventgenerator.common.event.Event;
import org.example.eventgenerator.common.event.EventType;
import org.example.eventgenerator.producer.event.ProducedEventBuilder;
import org.joda.time.DateTime;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Produces 'START' event:
 * <ol>
 * <li>Generates 'START' event</li>
 * <li>Put the event in the specified queue</li>
 * <li>Returns the event</li>
 * </ol>
 */
public class StartEventProducer extends AbstractEventProducer implements Callable<Event> {

    public StartEventProducer(final BlockingQueue<Event> eventQueue) {
        super(eventQueue);
    }

    @Override
    public Event call() throws Exception {
        final DateTime currentTime = DateTime.now();
        final Event startEvent = new ProducedEventBuilder()
                .setRandomInteractionId()
                .setEventType(EventType.START)
                .setEventTimeStamp(currentTime)
                .setCreateTime(currentTime)
                .setRandomServiceType()
                .setRandomOriginationPage()
                .setRandomOriginationChannel()
                .build();
        eventQueue.put(startEvent);
        return startEvent;
    }

    /**
     * There is no delay for START event, so method returns 0 delay
     * @return the delay.
     */
    @Override
    public long getDelaySeconds() {
        return 0;
    }
}
