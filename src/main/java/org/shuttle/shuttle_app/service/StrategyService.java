package org.shuttle.shuttle_app.service;

import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.shuttle.shuttle_app.entity.Passenger;
import org.shuttle.shuttle_app.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class StrategyService {
    private CacheService cacheService;
    private DropStrategy dropStrategy;
    @Autowired
    public StrategyService(CacheService cacheService, ApplicationContext applicationContext) {
        this.cacheService = cacheService;
        this.dropStrategy = applicationContext.getBean("dropByETA", DropStrategy.class);
        System.out.println("ssCache plist: " + cacheService.passengerList);
        System.out.println("ssCache wlist: " + cacheService.waitList);
    }

    public Passenger getNextPassengerToBeDropped() {

        System.out.println("getNextPassengerToBeDropped function called\n");
        System.out.println(cacheService.passengerList);
        return dropStrategy.getNextPassengerToBeDropped();

    }

//    public void updateETA(List<Passenger> passengerList) {
//
//    }
}
