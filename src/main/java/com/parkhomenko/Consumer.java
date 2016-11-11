package com.parkhomenko;

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
    private ExecutorService executor = Executors.newFixedThreadPool(16); //I have 4 cores in my CPU so 2 threads per core is normal approach
    private Signal stopSignal;

    public Consumer(BlockingQueue<String> queue, Signal stopSignal) {
        this.queue = queue;
        this.stopSignal = stopSignal;
    }

    public void run() {

        boolean interrupted = false;

        try {

            while(!stopSignal.isWorkCompleted() || !queue.isEmpty()) {

                try {

                    String city = queue.poll(300, TimeUnit.MILLISECONDS);

                    if(Objects.nonNull(city)) {
                        executor.execute(new GoogleCityGeocodingTask(city));
                    }

                } catch (InterruptedException e) {
                    interrupted = true;
                }

            }
        } finally {
            executor.shutdown();

            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

                System.out.print("SUCCESS = " + GoogleCityGeocodingTask.getSuccessCount());
                System.out.println(" / TOTAL = " + GoogleCityGeocodingTask.getTotalCount());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
