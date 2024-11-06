package org.shuttle.shuttle_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Long SUID;

    @Column(nullable = false)
    private double eta;

    @Column(nullable = false)
    private Status passengerStatus;

    private String dropAddress;

    private double dropLatitude;

    private double dropLongitude;


    public Passenger(Long suID, double eta, Status status) {
        this.SUID = suID;
        this.eta = eta;
        this.passengerStatus = status;
    }
}
