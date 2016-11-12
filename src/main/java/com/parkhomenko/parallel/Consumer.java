package com.parkhomenko.parallel;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class Consumer implements Runnable {

    private BlockingQueue<String> queue;
    private ExecutorService subTaskExecutorService = Executors.newFixedThreadPool(16);
    private volatile boolean runFlag = true;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {

        try {

            while (runFlag || !queue.isEmpty()) {

                String city = queue.poll(100, TimeUnit.MILLISECONDS);

                if (Objects.nonNull(city)) {
                    subTaskExecutorService.execute(new GoogleGeocodingTask(city));
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            subTaskExecutorService.shutdown();
        }
    }

    public boolean isAllSubTaskTerminated() {
        return subTaskExecutorService.isTerminated();
    }

    public void stop() {
        runFlag = false;
    }
}
