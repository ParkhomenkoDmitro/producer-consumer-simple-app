package com.parkhomenko.geolocation.google;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkhomenko.GoogleResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class GeoLocationUtil {

    private static final String GEOCODE_URL = "http://maps.googleapis.com/maps/api/geocode/json";

    public static Location convertToLatLong(String city) throws IOException {
        URL url = new URL(GEOCODE_URL + "?address=" + URLEncoder.encode(city, "UTF-8") + "&sensor=false");

        URLConnection conn = url.openConnection();

        InputStream in = conn.getInputStream() ;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GoogleResponse response = mapper.readValue(in,GoogleResponse.class);
        in.close();


        Result[] results = response.getResults();

        if(results.length > 0) {
            return results[0].getGeometry().getLocation();
        }

        return null;
    }
}
