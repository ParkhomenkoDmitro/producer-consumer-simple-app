package com.parkhomenko;

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
    private ExecutorService geocodingTaskExecutor = Executors.newFixedThreadPool(8); //I have 4 cores in my CPU so 2 threads per core is normal approach

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        String city;
        try {
            while(!Producer.EXIT_PILL.equals(city = queue.poll(10, TimeUnit.SECONDS))) { //10 seconds for reading data from disk I think enough
                Thread.sleep(200); //due to fact of Google Geocoding API Usage Limits I added delay here without it some part of parallel requests to Google Geocoding API service will be discarded
                geocodingTaskExecutor.execute(new GoogleCityGeocodingTask(city));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        geocodingTaskExecutor.shutdown();
    }
}
