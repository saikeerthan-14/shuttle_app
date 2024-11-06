package org.shuttle.shuttle_app.service;

import lombok.Data;
import org.shuttle.shuttle_app.entity.Passenger;
import org.shuttle.shuttle_app.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Data
public class StrategyService {
    private final PassengerServiceImpl passengerService;
    private CacheService cacheService;

    @Autowired
    public StrategyService(PassengerServiceImpl passengerService, CacheService cacheService) {
        this.passengerService = passengerService;
        this.cacheService = cacheService;
    }

    public Passenger getNextPassengerToBeDropped() {

        if (cacheService.passengerList.isEmpty()) {
            return null;
        }

        // Sorting the list of passengers by their ETA in ascending order
        cacheService.passengerList.sort(Comparator.comparingDouble(Passenger::getEta));

        return cacheService.passengerList.getFirst();

    }

//    public void updateETA(List<Passenger> passengerList) {
//
//    }
}
