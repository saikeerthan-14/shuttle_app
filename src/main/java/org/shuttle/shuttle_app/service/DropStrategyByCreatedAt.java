package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service("dropByCreatedAt")
public class DropStrategyByCreatedAt implements DropStrategy {

    private final CacheService cacheService;

    @Autowired
    public DropStrategyByCreatedAt(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public Passenger getNextPassengerToBeDropped() {
        if(cacheService.passengerList.isEmpty())
            return null;
        return cacheService.passengerList.stream().min(Comparator.comparing(Passenger::getCreatedAt)).orElse(null);
    }
}
