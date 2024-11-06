package org.shuttle.shuttle_app.controller;

import lombok.SneakyThrows;
import org.shuttle.shuttle_app.entity.Passenger;
import org.shuttle.shuttle_app.entity.Status;
import org.shuttle.shuttle_app.service.PassengerServiceImpl;
import org.shuttle.shuttle_app.service.ShuttleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShuttleController {
    private final ShuttleServiceImpl shuttleService;
    private final PassengerServiceImpl passengerServiceImpl;

    @Autowired
    public ShuttleController(ShuttleServiceImpl shuttleService, PassengerServiceImpl passengerServiceImpl) {
        this.shuttleService = shuttleService;
        this.passengerServiceImpl = passengerServiceImpl;
    }

    @SneakyThrows
    @PostMapping("/addPassenger")
    public String pickPassenger(@RequestParam Long suid, @RequestParam  String dropAddress) {
        System.out.println("passanger controller " + suid + " " + dropAddress);
        return shuttleService.pickPassenger(suid, dropAddress);
    }

    @PutMapping("/dropPassenger")
    public String dropPassenger() {
        return shuttleService.dropPassenger();
    }

    @PostMapping("/shuttleLocation")
    public String shuttleLocation(@RequestParam double latitude, @RequestParam double longitude) {
        return shuttleService.updateShuttleLocation(latitude, longitude);
    }

    @GetMapping("/shuttleLocation")
    public String shuttleLocation() {
        return shuttleService.getShuttleLocation();
    }

    @GetMapping("/getAllPassengers")
    public List<Passenger> getPassengers() {
        return passengerServiceImpl.getPassengersByStatus(Status.PICKED);
    }
}
