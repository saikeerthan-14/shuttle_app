package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Coordinates;

import javax.naming.ServiceUnavailableException;

public interface ShuttleService {

    String pickPassenger(Long suid, String dropAddress) throws ServiceUnavailableException;

    String dropPassenger();

    String getShuttleLocation();
}
