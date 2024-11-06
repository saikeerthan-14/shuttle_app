package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Coordinates;

public interface ShuttleLocationObserver {
    void updateLocation(Coordinates newLocation);
}


