package org.example.eventgenerator;

import org.example.eventgenerator.common.event.Event;
import org.example.eventgenerator.consumer.EventConsumer;
import org.example.eventgenerator.producer.InteractionsProducer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main class for event generator.
 */
public class EventGeneratorApp {
    /**
     * Starts producing and consuming events.
     *
     * @param args: <ol>
     *              <li>Integer: N Number of interactions to generate events for</li>
     *              <li>Integer: R Rate of generation “start” events per second (how many new interactions must be generated per every second)</li>
     *              </ol>
     */
    public static void main(String[] args) {

        //Input verification
        if (args.length < 2) {
            System.out.println("Incorrect input arguments");
            System.out.println("First argument should be a positive integer");
            System.out.println("Second argument should be a positive integer");
            return;
        }
        int numInteractions;
        try {
            numInteractions = Integer.parseInt(args[0]);
            if (numInteractions < 0) {
                System.out.println("First argument should be a positive integer");
                return;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("First argument should be a positive integer");
            return;
        }
        int rate;
        try {
            rate = Integer.parseInt(args[1]);
            if (rate < 1) {
                System.out.println("Second argument should be a positive integer");
                return;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Second argument should be a positive integer");
            return;
        }

        //Events generation

        final BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

        final EventConsumer eventConsumer = new EventConsumer(eventQueue, "results.json");

        final InteractionsProducer interactionsProducer
                = new InteractionsProducer(numInteractions, rate, eventQueue, eventConsumer::submitShoutDown);

        new Thread(interactionsProducer).start();
        new Thread(eventConsumer).start();
    }
}
