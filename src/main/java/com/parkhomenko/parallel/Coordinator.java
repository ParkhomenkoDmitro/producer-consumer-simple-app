package com.parkhomenko.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Dmytro Parkhomenko
 *         Created on 12.11.16.
 */

public class Coordinator implements Runnable {

    private ExecutorService producerExecutor;
    private ExecutorService consumerExecutor;
    private Consumer consumer;
    private long startTime;

    public Coordinator(ExecutorService producerExecutor, ExecutorService consumerExecutor, Consumer consumer, long startTime) {
        this.producerExecutor = producerExecutor;
        this.consumerExecutor = consumerExecutor;
        this.consumer = consumer;
        this.startTime = startTime;
    }

    @Override
    public void run() {

        producerExecutor.shutdown();

        try {
            producerExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        consumerExecutor.shutdown();

        consumer.stop();

        try {

            while (!consumer.isAllSubTaskTerminated()) {
                Thread.sleep(300);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("-------------------------RESULT-----------------------");
        System.out.print("SUCCESS = " + GoogleGeocodingTask.getSuccessCount());
        System.out.println(" / TOTAL = " + GoogleGeocodingTask.getTotalCount());

        long endTime = System.currentTimeMillis();

        System.out.println("Time: " + (endTime - startTime));
    }
}
