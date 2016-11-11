package com.parkhomenko;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10); //10 for testing
        Signal exitSignal = new Signal();
        Producer producer = new Producer(queue, exitSignal);
        Consumer consumer = new Consumer(queue, exitSignal);

        ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
        producerExecutor.execute(producer);

        ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();
        consumerExecutor.execute(consumer);

        producerExecutor.shutdown();
        consumerExecutor.shutdown();
    }
}
