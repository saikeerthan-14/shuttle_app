package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Coordinates;
import org.shuttle.shuttle_app.entity.Passenger;
import org.shuttle.shuttle_app.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CacheService {

//    private final StopServiceImpl stopServiceImpl;
//    private final PassengerServiceImpl passengerServiceImpl;
    List<Passenger> passengerList = new ArrayList<>();
    List<Passenger> waitList = new ArrayList<>();
//    private final ETACalculator calculator;
//    private final Coordinates collegePlaceCoordinates;

    @Autowired
    public CacheService(PassengerServiceImpl passengerService) {
        passengerList = passengerService.getPassengersByStatus(Status.PICKED);
        waitList = passengerService.getPassengersByStatus(Status.WAITING);
//        this.calculator = calculator;
//        this.stopServiceImpl = stopServiceImpl;
//        this.passengerServiceImpl = passengerServiceImpl;
//        collegePlaceCoordinates = stopServiceImpl.getStop("CollegePlace").getStopCoordinates();
    }

    public static void removePassengerFromCache(Passenger passenger, List<Passenger> modifiableList) {
        Iterator<Passenger> iterator = modifiableList.iterator();
        while (iterator.hasNext()) {
            Passenger p = iterator.next();
            if (passenger.getSUID().equals(p.getSUID())) {
                iterator.remove();
                break;
            }
        }
    }

//    public void updateETA(List<Passenger> passengers, boolean isPassenger) {
//        Iterator<Passenger> iterator = passengers.iterator();
//        Coordinates location = collegePlaceCoordinates;
//        while (iterator.hasNext()) {
//            Passenger p = iterator.next();
//            if (isPassenger){
//                location.setLatitude(p.getDropLatitude());
//                location.setLongitude(p.getDropLongitude());
//            }
//            p.setEta(calculator.calculateETA(location));
//            passengerServiceImpl.saveToDB(p);
//        }
//    }

}
