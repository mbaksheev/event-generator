package org.example.eventgenerator.producer;

import org.example.eventgenerator.common.event.Event;

import java.util.concurrent.BlockingQueue;

/**
 * Abstract producer.
 */
public abstract class AbstractEventProducer {
    protected final BlockingQueue<Event> eventQueue;

    protected AbstractEventProducer(final BlockingQueue<Event> eventQueue) {
        this.eventQueue = eventQueue;
    }

    public abstract long getDelaySeconds();
}
