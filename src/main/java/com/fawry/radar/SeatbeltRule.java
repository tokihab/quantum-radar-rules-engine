package com.fawry.radar.rules;

import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;

// checks if the driver is wearing a seatbelt
public class SeatbeltRule implements Rule {

    @Override
    public String getRuleName() { 
        return "Seatbelt"; 
    }

    @Override
    public Violation evaluate(Observation obs) {
        // generate a violation if seatbelt isn't fastened
        if (!obs.isSeatbeltFastened) {
            return new Violation(
                getRuleName(), 
                "Seatbelt not fastned", // keeping the typo from the PDF exactly as requested
                100
            );
        }
        return null; // return null if they followed the rule
    }
}