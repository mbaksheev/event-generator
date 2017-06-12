package org.example.eventgenerator.producer;

import com.google.common.util.concurrent.*;
import org.example.eventgenerator.common.event.Event;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Produces specified number of interactions with specified rate.<br/>
 * Each interaction has three life stages: 'START', 'JOIN', 'END'. At every stage transition, an event is produced and placed into specified queue.
 */
public class InteractionsProducer implements Runnable {
    private final Logger logger = Logger.getLogger(InteractionsProducer.class.getSimpleName());

    private final ListeningScheduledExecutorService eventService = MoreExecutors.listeningDecorator(Executors.newSingleThreadScheduledExecutor());
    private final int maxNumberOfInteractions;
    private final int interactionsRate;
    private final BlockingQueue<Event> eventQueue;
    private final Callback callback;

    /**
     * Constructs the producer.
     *
     * @param maxNumberOfInteractions max number of interactions to produce
     * @param interactionsRate        rate of producing (interactions per second)
     * @param eventQueue              queue where produced events will be placed
     * @param callback                callback on all interactions have been submitted
     */
    public InteractionsProducer(final int maxNumberOfInteractions,
                                final int interactionsRate,
                                final BlockingQueue<Event> eventQueue,
                                final Callback callback) {
        this.maxNumberOfInteractions = maxNumberOfInteractions;
        this.interactionsRate = interactionsRate;
        this.eventQueue = eventQueue;
        this.callback = callback;
    }

    /**
     * Produces interaction. <br/>
     * Each interaction has three life stages: 'START', 'JOIN', 'END'. At every stage transition, an event is produced and placed into specified queue.
     *
     * @return listenable future UUID of produced interaction
     */
    private ListenableFuture<UUID> produceInteractionAsync() {
        logger.info("Start new interaction");

        //Produce start event
        final StartEventProducer startEventProducer = new StartEventProducer(eventQueue);
        final ListenableFuture<Event> futureStartEvent
                = eventService.schedule(startEventProducer, startEventProducer.getDelaySeconds(), TimeUnit.SECONDS);

        //Produce join event based on start event
        final ListenableFuture<Event> futureJoinEvent = Futures.transformAsync(futureStartEvent, startEvent -> {

            logger.log(Level.INFO, "START event with interactionId={0} has been produced", startEvent.getInteractionId());

            final JoinEventProducer joinEventProducer = new JoinEventProducer(startEvent, eventQueue);
            return eventService.schedule(joinEventProducer, joinEventProducer.getDelaySeconds(), TimeUnit.SECONDS);
        }, MoreExecutors.directExecutor());

        //Produce end event based on join event
        final ListenableFuture<Event> futureEndEvent = Futures.transformAsync(futureJoinEvent, joinEvent -> {

            logger.log(Level.INFO, "JOIN event with interactionId={0} has been produced", joinEvent.getInteractionId());
            final EndEventProducer endEventProducer = new EndEventProducer(joinEvent, eventQueue);
            return eventService.schedule(endEventProducer, endEventProducer.getDelaySeconds(), TimeUnit.SECONDS);
        }, MoreExecutors.directExecutor());


        return Futures.transform(futureEndEvent, endEvent -> {

            logger.log(Level.INFO, "End event with interactionId={0} has been produced", endEvent.getInteractionId());

            return endEvent.getInteractionId();
        }, MoreExecutors.directExecutor());
    }

    @Override
    public void run() {
        try {
            final long interactionsDelayMs = 1000L / interactionsRate;
            final CountDownLatch inProgressInteractions = new CountDownLatch(maxNumberOfInteractions);

            for (int i = 0; i < maxNumberOfInteractions; i++) {
                final ListenableFuture<UUID> futureInteraction = produceInteractionAsync();
                Futures.addCallback(futureInteraction, new FutureCallback<UUID>() {

                    @Override
                    public void onSuccess(@Nullable final UUID interactionId) {
                        logger.log(Level.INFO, "Interaction with id={0} has been produced", interactionId);
                        inProgressInteractions.countDown();
                    }

                    @Override
                    public void onFailure(final Throwable t) {
                        logger.log(Level.SEVERE, "Exception: ", t);
                        inProgressInteractions.countDown();
                    }
                }, MoreExecutors.directExecutor());

                Thread.sleep(interactionsDelayMs);
            }

            inProgressInteractions.await();
            eventService.shutdown();
            callback.onComplete();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Exception: ", e);
            callback.onComplete();
        }

    }
}
