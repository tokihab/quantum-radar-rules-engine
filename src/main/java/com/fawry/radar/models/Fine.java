package com.fawry.radar.models;

import java.util.ArrayList;
import java.util.List;

// bundles all violations and calculates the total fee for a car
public class Fine {
    public String plateNumber;
    public List<Violation> violations = new ArrayList<>();

    public Fine(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    // sum up fees from all violations
    public int getTotalFee() {
        return violations.stream().mapToInt(v -> v.fee).sum();
    }
}