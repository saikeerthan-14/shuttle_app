package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Coordinates;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShuttleLocationService{

    private Coordinates currentLocation;
    private final List<ShuttleLocationObserver> observers = new ArrayList<>();

    // Method to register observers
    public void addObserver(ShuttleLocationObserver observer) {
        observers.add(observer);
    }

    // Method to unregister observers
    public void removeObserver(ShuttleLocationObserver observer) {
        observers.remove(observer);
    }

    // Method to notify all observers of the location update
    private void notifyObservers() {
        for (ShuttleLocationObserver observer : observers) {
            observer.updateLocation(currentLocation);
        }
    }

    // Update shuttle location and notify observers
    public void updateShuttleLocation(Coordinates newLocation) {
        this.currentLocation = newLocation;
        notifyObservers();
    }

    public Coordinates getCurrentLocation() {
        return currentLocation;
    }

}
