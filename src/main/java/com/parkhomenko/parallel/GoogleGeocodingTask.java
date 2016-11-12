package com.parkhomenko.parallel;

import com.google.maps.model.LatLng;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import static com.parkhomenko.common.GoogleGeocodingUtil.convertToLatLongWithApiKey;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class GoogleGeocodingTask implements Runnable {

    private String city;
    private static AtomicLong successCount = new AtomicLong();
    private static AtomicLong totalCount = new AtomicLong();

    public GoogleGeocodingTask(String city) {
        this.city = city;
    }

    public void run() {

        LatLng latLng = convertToLatLongWithApiKey(city);

        if (Objects.nonNull(latLng)) {
            System.out.format("City: %s, Location: [Lat = %s, Lng = %s]%n", city, latLng.lat, latLng.lng);
            successCount.incrementAndGet();
        } else {
            System.out.println("FAIL !!!");
        }

        totalCount.incrementAndGet();
    }

    public static long getSuccessCount() {
        return successCount.get();
    }

    public static long getTotalCount() {
        return totalCount.get();
    }
}