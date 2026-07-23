package com.fawry.radar.rules;

import com.fawry.radar.models.CarType;
import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;

// custom rule added to demonstrate system extensibility
// sets a strict speed limit for buses
public class BusSpeedRule implements Rule {

    @Override
    public String getRuleName() {
        return "BusSpeedLimit";
    }

    @Override
    public Violation evaluate(Observation obs) {
        // we only care about evaluating buses in this specific rule
        if (obs.type == CarType.BUS && obs.speed > 70) {
            return new Violation(
                getRuleName(),
                "speed of " + obs.speed + " exceeded max allowed 70 for buses",
                400 // custom fee for buses
            );
        }
        return null;
    }
}