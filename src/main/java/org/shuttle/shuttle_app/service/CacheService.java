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
    List<Passenger> passengerList = new ArrayList<>();
    List<Passenger> waitList = new ArrayList<>();
    @Autowired
    public CacheService(PassengerServiceImpl passengerService) {
        passengerList = passengerService.getPassengersByStatus(Status.PICKED);
        waitList = passengerService.getPassengersByStatus(Status.WAITING);
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
    

}
