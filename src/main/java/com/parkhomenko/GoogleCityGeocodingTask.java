package com.parkhomenko;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class GoogleCityGeocodingTask implements Runnable {
    private String city;
    private static AtomicLong successCount = new AtomicLong();
    private static AtomicLong totalCount = new AtomicLong();

    public GoogleCityGeocodingTask(String city) {
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

    private static LatLng convertToLatLongWithApiKey(String city) {
        GeoApiContext context = new GeoApiContext()
                .setApiKey("AIzaSyBk0gOCk5-FiLKdTrUTNtg1fcgh_0OizCs")
                .setQueryRateLimit(50);

        GeocodingResult[] results;

        try {
            results = GeocodingApi.geocode(context, city).await();
        } catch (Exception e) {
            return null;
        }

        if(results.length > 0) {
            return results[0].geometry.location;
        }

        return null;
    }
}