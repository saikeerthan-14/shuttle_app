package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Passenger;
import org.shuttle.shuttle_app.entity.Status;
import org.shuttle.shuttle_app.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService{

    private final PassengerRepository passengerRepository;
    List<Passenger> passengerList;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger getPassengerBySUIDAndPassengerStatus(long suid, Status status) {
        List<Passenger> passengers = passengerRepository.findBySUIDEqualsAndPassengerStatus(suid, status);
        if (passengers.isEmpty()) {
            return null;
        }
        return passengers.getLast();
    }

    @Override
    public void saveToDB(Passenger passenger) {
        passengerRepository.save(passenger);
    }

    public String updatePassengerStatus() {
        return "";
    }

    @Override
    public List<Passenger> getPassengersByStatus(Status status) {
        return passengerRepository.findByPassengerStatus(status);
    }

}
