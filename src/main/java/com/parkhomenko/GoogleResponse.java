package com.parkhomenko;

import com.parkhomenko.geolocation.google.Result;

/**
 * @author Dmytro Parkhomenko
 *         Created on 04.11.16.
 */

public class GoogleResponse {

    private Result[] results ;

    public Result[] getResults() {
        return results;
    }
    public void setResults(Result[] results) {
        this.results = results;
    }
}
