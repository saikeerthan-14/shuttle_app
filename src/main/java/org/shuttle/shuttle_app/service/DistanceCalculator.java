package org.shuttle.shuttle_app.service;

import org.springframework.stereotype.Component;

/**
 * The DistanceCalculator class provides a method to calculate the distance
 * between two points on the Earth's surface given their latitude and longitude.
 *
 * The calculation is performed using the Haversine formula, which accounts for
 * the spherical shape of the Earth.
 */
@Component
public class DistanceCalculator {

    // Radius of the Earth in kilometers
    private static final double EARTH_RADIUS_KM = 6371.0;

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Apply the Haversine formula
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
