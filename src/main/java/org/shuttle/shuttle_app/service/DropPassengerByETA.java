package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service("dropByETA")
public class DropPassengerByETA implements DropStrategy {

    private final CacheService cacheService;

    @Autowired
    public DropPassengerByETA(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public Passenger getNextPassengerToBeDropped() {
        if(cacheService.passengerList.isEmpty()) {
            return null;
        }
        return cacheService.passengerList.stream().min(Comparator.comparingDouble(Passenger::getEta)).orElse(null);
    }
}
