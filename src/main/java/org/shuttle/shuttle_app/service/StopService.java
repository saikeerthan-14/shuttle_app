package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Stop;

import javax.naming.ServiceUnavailableException;

public interface StopService {
    String addStop(Stop stop) throws ServiceUnavailableException;
    Stop getStop(String stopName) throws ServiceUnavailableException;
}

