package com.fawry.radar.rules;

import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;

// common interface for all radar rules to ensure the system is extensible
public interface Rule {
    String getRuleName();
    Violation evaluate(Observation obs);
}