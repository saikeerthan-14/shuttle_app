package org.shuttle.shuttle_app.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.shuttle.shuttle_app.entity.*;
import org.shuttle.shuttle_app.repository.ShuttleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;

@Service
public class ShuttleServiceImpl implements ShuttleService {

    private final ShuttleRepository shuttleRepository;
    private final PassengerServiceImpl passengerServiceImpl;
    private final StudentServiceImpl studentServiceImpl;
    private final GeoCodingService geoCodingService;
    private final StrategyService strategyService;
    private CacheService cacheService;
    private Shuttle singletonShuttle;
    private final Coordinates collegePlaceCoordinates;

    @Autowired
    public ShuttleServiceImpl(PassengerServiceImpl passengerServiceImpl, ShuttleRepository shuttleRepository, StopServiceImpl stopServiceImpl,
                              GeoCodingService geoCodingService, @Lazy StudentServiceImpl studentServiceImpl, StrategyService strategyService,
                              CacheService cacheService) {
        this.shuttleRepository = shuttleRepository;
        this.passengerServiceImpl = passengerServiceImpl;
        this.geoCodingService = geoCodingService;
        this.studentServiceImpl = studentServiceImpl;
        collegePlaceCoordinates = stopServiceImpl.getStop("CollegePlace").getStopCoordinates();
        this.strategyService = strategyService;
        this.cacheService = cacheService;
    }

    @PostConstruct
    private void init() throws ServiceUnavailableException {
        // Loading the singleton instance from the database on startup
        singletonShuttle = shuttleRepository.findTopByOrderByIdAsc();

        // If no instance exists, create a new one with default values
        if (singletonShuttle == null) {
            singletonShuttle = new Shuttle();
            singletonShuttle.setShuttleSpeed(30.0);
            // Default coordinates
            singletonShuttle.setCoordinates(collegePlaceCoordinates);
            singletonShuttle = shuttleRepository.save(singletonShuttle);
        }
    }

    public Shuttle getShuttle() {
        return singletonShuttle;
    }

    @Override
    public String pickPassenger(Long suid, String dropAddress) throws ServiceUnavailableException {
        Coordinates dropLocation = geoCodingService.getCoordinates(dropAddress);
        if(dropLocation == null) {
            return "Unable to locate address on map, please make sure it is spelled correctly";
        }
        double drop_lat = dropLocation.getLatitude();
        double drop_long = dropLocation.getLongitude();

        if (cacheService.waitList.isEmpty() && cacheService.passengerList.isEmpty()) {
            resetShuttleLocation();
        }

        Student student = studentServiceImpl.findBySuid(suid);
        if (student == null ) {
            return "Student not found";
        } else if(student.getStudentStatus() == Status.IDLE) {
            return "Only pick-up requested students are allowed. Please raise pickup request";
        } else if (student.getStudentStatus() == Status.WAITING) {
            student.setStudentStatus(Status.PICKED);
            Passenger passenger = passengerServiceImpl.getPassengerBySUIDAndPassengerStatus(suid, Status.WAITING);
            passenger.setPassengerStatus(Status.PICKED);
            passenger.setDropLatitude(drop_lat);
            passenger.setDropLongitude(drop_long);
            passenger.setDropAddress(dropAddress);
            passenger.setEta(studentServiceImpl.calculateETA(dropLocation));
            studentServiceImpl.saveToDB(student);
            CacheService.removePassengerFromCache(passenger, cacheService.waitList);
            passengerServiceImpl.saveToDB(passenger);
            return "You have been picked " + student.getName() + " Details: " + passenger;
        } else if (student.getStudentStatus() == Status.PICKED) {
            return "Cannot pick Passenger who is travelling";
        }

        return "Unable to pick passenger, please try again later";
    }


    @Override
    public String dropPassenger() {
        Passenger passengerToBeDropped = strategyService.getNextPassengerToBeDropped();
        Coordinates shuttleCoordinates = singletonShuttle.getCoordinates();
        if (passengerToBeDropped != null) {

            CacheService.removePassengerFromCache(passengerToBeDropped, cacheService.passengerList);
            passengerToBeDropped.setPassengerStatus(Status.DROPPED);

            Student student = studentServiceImpl.findBySuid(passengerToBeDropped.getSUID());
            student.setStudentStatus(Status.IDLE);

            updateShuttleLocation(passengerToBeDropped.getDropLatitude(), passengerToBeDropped.getDropLongitude());

            studentServiceImpl.saveToDB(student);
            passengerServiceImpl.saveToDB(passengerToBeDropped);

            return "Dropped passenger successfully " + passengerToBeDropped;

        } else {
            resetShuttleLocation();
        }
        return "No passengers to drop";
    }

    @Override
    public String getShuttleLocation() {
        return "Shuttle is at " + singletonShuttle.getCoordinates().toString();
    }

    public void savetoDB(Shuttle shuttle) {
        shuttleRepository.save(shuttle);
    }

    public void resetShuttleLocation() {
        updateShuttleLocation(collegePlaceCoordinates.getLatitude(), collegePlaceCoordinates.getLongitude());
        savetoDB(singletonShuttle);
    }

    public String updateShuttleLocation(double lat, double lng) {
        Coordinates coordinates = new Coordinates(lat, lng);
        singletonShuttle.setCoordinates(coordinates);
        savetoDB(singletonShuttle);
//        strategyService.updateETA(cacheService.passengerList);
//        strategyService.updateETA(cacheService.waitList);
        return "Update shuttle location successfully. Updated shuttle coordinates: " + coordinates;
    }

    @PreDestroy
    public void cleanUp() {
        singletonShuttle.setCoordinates(collegePlaceCoordinates);
        savetoDB(singletonShuttle);
    }

}
