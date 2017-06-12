package org.example.eventgenerator.producer;

import org.example.eventgenerator.common.event.Event;
import org.example.eventgenerator.common.event.EventType;
import org.example.eventgenerator.producer.event.ProducedEventBuilder;
import org.joda.time.DateTime;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Produces 'END' event based on 'JOIN' event:
 * <ol>
 * <li>Generates 'END' event</li>
 * <li>Put the event in the specified queue</li>
 * <li>Returns the event</li>
 * </ol>
 */
public class EndEventProducer extends AbstractEventProducer implements Callable<Event> {
    private final Event joinEvent;

    public EndEventProducer(final Event joinEvent, final BlockingQueue<Event> eventQueue) {
        super(eventQueue);
        this.joinEvent = joinEvent;
    }

    @Override
    public Event call() throws Exception {
        final DateTime currentTime = DateTime.now();
        final Event endEvent = new ProducedEventBuilder(joinEvent)
                .setEventType(EventType.END)
                .setEventTimeStamp(currentTime)
                .setEndTime(currentTime)
                .setRandomEndReason()
                .build();
        eventQueue.put(endEvent);
        return endEvent;
    }

    /**
     * Returns random delay between 15 and 60 seconds.
     *
     * @return the delay
     */
    @Override
    public long getDelaySeconds() {
        return ThreadLocalRandom.current().nextLong(15, 61);
    }
}
