package org.shuttle.shuttle_app.controller;

import lombok.SneakyThrows;
import org.shuttle.shuttle_app.service.ShuttleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShuttleController {
    private final ShuttleServiceImpl shuttleService;

    @Autowired
    public ShuttleController(ShuttleServiceImpl shuttleService) {
        this.shuttleService = shuttleService;
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
    public String shuttleLocation(@RequestParam double lat, @RequestParam double lon) {
        return shuttleService.updateShuttleLocation(lat, lon);
    }

    @GetMapping("/shuttleLocation")
    public String shuttleLocation() {
        return shuttleService.getShuttleLocation();
    }
}
