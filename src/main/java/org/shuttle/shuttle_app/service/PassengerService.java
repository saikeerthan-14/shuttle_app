package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Passenger;
import org.shuttle.shuttle_app.entity.Status;

import java.util.List;

public interface PassengerService {

    void saveToDB(Passenger passenger);

    List<Passenger> getPassengersByStatus(Status status);

}
