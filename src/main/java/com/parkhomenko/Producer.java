package com.parkhomenko;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class Producer implements Runnable {

    private static final String FILE_PATH = "city.txt";
    private BlockingQueue<String> queue;
    private Signal stopSignal;

    public Producer(BlockingQueue<String> queue, Signal stopSignal) {
        this.queue = queue;
        this.stopSignal = stopSignal;
    }

    @Override
    public void run() {

        try {
            readAll();
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            stopSignal.setWorkCompleted(true);
        }
    }

    private void readAll() throws IOException, InterruptedException {

        ClassLoader classLoader = this.getClass().getClassLoader();
        String fileName = classLoader.getResource(FILE_PATH).getFile();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String city;
            while((city = br.readLine()) != null) {
                queue.put(city);
            }
        }
    }
}
