package org.shuttle.shuttle_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Shuttle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double shuttleSpeed;

    private Coordinates coordinates;

/*
    CST lat, long
    private double campusLatitude = 43.037619224685415;
    private double campusLongitude = -76.13065883527489;
*/

}
