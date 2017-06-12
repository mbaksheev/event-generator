package org.example.eventgenerator.producer;

import org.example.eventgenerator.common.event.Event;
import org.example.eventgenerator.common.event.EventType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Test
public class StartEventProducerTest {

    /**
     * Tests Start event producer. Verifies that event generated properly and placed into queue.
     *
     * @throws Exception does not matter here
     */
    @Test
    public void testProduceTest() throws Exception {
        BlockingQueue<Event> blockingQueue = new LinkedBlockingQueue<>();
        StartEventProducer eventProducer = new StartEventProducer(blockingQueue);
        Event generatedEvent = eventProducer.call();

        Assert.assertTrue(blockingQueue.contains(generatedEvent));
        Assert.assertEquals(generatedEvent.getEventType(), EventType.START);
        Assert.assertNotNull(generatedEvent.getInteractionId());
        Assert.assertNotNull(generatedEvent.getEventTimeStamp());
        //and all other fields verifications...
    }
}
