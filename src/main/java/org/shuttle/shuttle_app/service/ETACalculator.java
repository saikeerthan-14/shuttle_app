package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Coordinates;
import org.shuttle.shuttle_app.entity.Shuttle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ETACalculator {

    private final Shuttle shuttle;

    @Autowired
    public ETACalculator(ShuttleServiceImpl shuttleService) {
        shuttle = shuttleService.getShuttle();
    }


//  ETA with respect to shuttle
    public double calculateETA(Coordinates location) {
        double shuttleLat = shuttle.getCoordinates().getLatitude();
        double shuttleLong = shuttle.getCoordinates().getLongitude();

        double distance = DistanceCalculator.calculateDistance(shuttleLat, shuttleLong, location.getLatitude(), location.getLongitude());

        double speed = shuttle.getShuttleSpeed();

        return (distance/speed)*60;
    }

}
