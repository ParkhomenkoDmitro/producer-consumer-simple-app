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
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(50); //50 is a size for common buffer between consumer and producer, not very small so enough
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
        producerExecutor.execute(producer);


        ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();
        consumerExecutor.execute(consumer);

        producerExecutor.shutdown();
        consumerExecutor.shutdown();
    }
}
