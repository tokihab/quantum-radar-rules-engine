package com.fawry.radar.rules;

import com.fawry.radar.models.CarType;
import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;

// enforces speed limits for trucks and private cars
public class SpeedRule implements Rule {

    @Override
    public String getRuleName() { 
        return "SpeedLimit"; 
    }

    @Override
    public Violation evaluate(Observation obs) {
        int limit = Integer.MAX_VALUE;
        
        // set limits based on the car type
        if (obs.type == CarType.TRUCK) {
            limit = 60;
        } else if (obs.type == CarType.PRIVATE) {
            limit = 80;
        }

        // check if the vehicle was speeding
        if (obs.speed > limit) {
            return new Violation(
                getRuleName(), 
                "speed of " + obs.speed + " exceeded max allowed " + limit, 
                300
            );
        }
        return null; 
    }
}