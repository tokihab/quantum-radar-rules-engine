package com.fawry.radar.rules;

import com.fawry.radar.models.CarType;
import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BusSpeedRuleTests {
    private final BusSpeedRule rule = new BusSpeedRule();

    @Test
    void testBusSpeeding() {
        Observation obs = new Observation("BUS1", LocalDateTime.now(), CarType.BUS, 75, true);
        Violation result = rule.evaluate(obs);
        
        assertNotNull(result);
        assertEquals(400, result.fee);
    }
    
    @Test
    void testBusUnderLimit() {
        Observation obs = new Observation("BUS1", LocalDateTime.now(), CarType.BUS, 65, true);
        assertNull(rule.evaluate(obs));
    }
}