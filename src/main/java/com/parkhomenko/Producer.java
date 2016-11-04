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
    public static final String EXIT_PILL = "EXIT_PILL_CODE";
    private static final String FILE_PATH = "city.txt";
    private BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        ClassLoader classLoader = this.getClass().getClassLoader();
        String fileName = classLoader.getResource(FILE_PATH).getFile();

        try {
            try (BufferedReader br =
                         new BufferedReader(new FileReader(fileName))) {
                String city;
                while((city = br.readLine()) != null) {
                    queue.put(city);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            queue.put(EXIT_PILL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
