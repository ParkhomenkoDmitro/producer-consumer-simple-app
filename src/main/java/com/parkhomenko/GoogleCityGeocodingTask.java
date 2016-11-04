package com.parkhomenko;

import com.parkhomenko.geolocation.google.GeoLocationUtil;
import com.parkhomenko.geolocation.google.Location;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class GoogleCityGeocodingTask implements Runnable {
    private String city;

    public GoogleCityGeocodingTask(String city) {
        this.city = city;
    }

    public void run() {
        try {
            Location location = GeoLocationUtil.convertToLatLong(city);
            if(Objects.nonNull(location)) {
                System.out.format("City: %s, Location: [Lat = %s, Lng = %s]%n", city, location.getLat(), location.getLng());
            } else {
                System.out.format("City: %s, Location: Not found ---------------------------------------------%n", city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}