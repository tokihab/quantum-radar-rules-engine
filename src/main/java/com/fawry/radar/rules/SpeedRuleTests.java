package com.fawry.radar.rules;

import com.fawry.radar.models.CarType;
import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SpeedRuleTests {
    private final SpeedRule rule = new SpeedRule();

    // easily test multiple scenarios without writing repetitive code
    @ParameterizedTest
    @CsvSource({
            "TRUCK, 61, true",
            "TRUCK, 60, false",
            "PRIVATE, 81, true",
            "PRIVATE, 80, false"
    })
    void testSpeedLimits(CarType type, int speed, boolean shouldViolate) {
        Observation obs = new Observation("TEST", LocalDateTime.now(), type, speed, true);
        Violation result = rule.evaluate(obs);

        if (shouldViolate) {
            assertNotNull(result);
            assertEquals("SpeedLimit", result.ruleName);
            assertEquals(300, result.fee);
        } else {
            assertNull(result); // no violation if under the limit
        }
    }
}