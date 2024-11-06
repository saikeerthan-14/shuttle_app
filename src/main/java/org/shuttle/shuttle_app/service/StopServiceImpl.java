package org.shuttle.shuttle_app.service;

import org.shuttle.shuttle_app.entity.Stop;
import org.shuttle.shuttle_app.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.util.List;

@Service
public class StopServiceImpl implements StopService {

    private final StopRepository stopRepository;

    @Autowired
    public StopServiceImpl(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    // Extract Constant
    private static final String STOP_NULL_ERROR = "Stop cannot be null";
    private static final String STOP_ADDED_SUCCESS = "Stop added successfully";

    @Override
    public String addStop(Stop stop) throws ServiceUnavailableException {
        if (stop != null) {
            stopRepository.save(stop);
            return STOP_ADDED_SUCCESS + " Details: " + stop;
        } else {
            throw new ServiceUnavailableException(STOP_NULL_ERROR);
        }
    }

    @Override
    public Stop getStop(String stopName) {
        List<Stop> stopList = stopRepository.findTop1ByStopName(stopName);
        if (stopList == null || stopList.isEmpty()) {
            return null;
        }
        return stopList.getFirst();
    }


}
