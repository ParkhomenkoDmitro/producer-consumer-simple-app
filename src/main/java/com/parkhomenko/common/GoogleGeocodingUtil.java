package com.parkhomenko.common;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import java.util.concurrent.TimeUnit;

/**
 * @author Dmytro Parkhomenko
 *         Created on 12.11.16.
 */

public class GoogleGeocodingUtil {

    public static LatLng convertToLatLongWithApiKey(String city) {
        GeoApiContext context = new GeoApiContext()
                .setApiKey("AIzaSyBk0gOCk5-FiLKdTrUTNtg1fcgh_0OizCs")
                .setQueryRateLimit(50)
                .disableRetries()
                .setConnectTimeout(500, TimeUnit.MILLISECONDS);

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
