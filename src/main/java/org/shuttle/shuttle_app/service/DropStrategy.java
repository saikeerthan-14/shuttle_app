package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Passenger;

public interface DropStrategy {
    Passenger getNextPassengerToBeDropped();
}
