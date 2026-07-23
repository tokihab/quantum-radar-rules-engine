package com.fawry.radar.models;

import java.time.LocalDateTime;

// holds the data captured by the physical radar
public class Observation {
    public String plateNumber;
    public LocalDateTime date;
    public CarType type;
    public int speed;
    public boolean isSeatbeltFastened;

    public Observation(String plateNumber, LocalDateTime date, CarType type, int speed, boolean isSeatbeltFastened) {
        this.plateNumber = plateNumber;
        this.date = date;
        this.type = type;
        this.speed = speed;
        this.isSeatbeltFastened = isSeatbeltFastened;
    }
}