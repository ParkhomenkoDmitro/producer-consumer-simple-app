package com.parkhomenko.linear;

import com.google.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static com.parkhomenko.common.GoogleGeocodingUtil.convertToLatLongWithApiKey;

/**
 * @author Dmytro Parkhomenko
 *         Created on 12.11.16.
 */

public class Main {
    private static final String FILE_PATH = "city.txt";

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        Main main = new Main();

        try {
            main.doJob();

            long endTime = System.currentTimeMillis();

            System.out.println("Time: " + (endTime - startTime));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doJob() throws IOException {

        long successCount = 0;
        long totalCount = 0;

        ClassLoader classLoader = this.getClass().getClassLoader();
        String fileName = classLoader.getResource(FILE_PATH).getFile();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String city;
            while((city = br.readLine()) != null) {

                LatLng latLng = convertToLatLongWithApiKey(city);

                if (Objects.nonNull(latLng)) {
                    System.out.format("City: %s, Location: [Lat = %s, Lng = %s]%n", city, latLng.lat, latLng.lng);
                    successCount++;
                } else {
                    System.out.println("FAIL !!!");
                }

                totalCount++;
            }
        }

        System.out.println("-------------------------RESULT-----------------------");
        System.out.print("SUCCESS = " + successCount);
        System.out.println(" / TOTAL = " + totalCount);
    }
}
