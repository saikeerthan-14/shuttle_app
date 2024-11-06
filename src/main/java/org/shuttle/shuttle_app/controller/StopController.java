package org.shuttle.shuttle_app.controller;

import org.shuttle.shuttle_app.entity.Stop;
import org.shuttle.shuttle_app.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;

@RestController
public class StopController {

    private final StopService stopService;

    @Autowired
    public StopController(StopService stopService) {
        this.stopService = stopService;
    }

    @PostMapping("/addStop")
    public String addStop(@RequestBody Stop stop) throws ServiceUnavailableException {
        return stopService.addStop(stop);
    }

    @GetMapping("/getStop")
    public Stop getStop(@RequestParam String stopName) throws ServiceUnavailableException {
        return stopService.getStop(stopName);
    }
}