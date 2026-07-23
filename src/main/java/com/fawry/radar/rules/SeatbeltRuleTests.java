package com.fawry.radar.rules;

import com.fawry.radar.models.CarType;
import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SeatbeltRuleTests {
    private final SeatbeltRule rule = new SeatbeltRule();

    @Test
    void testSeatbeltNotFastened() {
        Observation obs = new Observation("TEST", LocalDateTime.now(), CarType.PRIVATE, 50, false);
        Violation result = rule.evaluate(obs);
        
        assertNotNull(result);
        assertEquals("Seatbelt", result.ruleName);
    }

    @Test
    void testSeatbeltFastened() {
        Observation obs = new Observation("TEST", LocalDateTime.now(), CarType.PRIVATE, 50, true);
        assertNull(rule.evaluate(obs));
    }
}