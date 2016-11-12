package com.parkhomenko.parallel;

import java.util.concurrent.*;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class Main {

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(30);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
        producerExecutor.execute(producer);

        ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();
        consumerExecutor.execute(consumer);

        ExecutorService coordinatorExecutor = Executors.newSingleThreadExecutor();
        Coordinator coordinator = new Coordinator(producerExecutor, consumerExecutor, consumer, startTime);
        coordinatorExecutor.execute(coordinator);
    }
}
