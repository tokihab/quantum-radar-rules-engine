package com.fawry.radar.system;

import com.fawry.radar.models.CarType;
import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;
import com.fawry.radar.rules.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RadarSystemTests {
    // we need to capture System.out to test the print statements
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        // always reset System.out so we don't break other tests
        System.setOut(standardOut);
    }

    @Test
    void testProcessObservationWithMultipleViolations() {
        // mock the rules so we are only testing the system's aggregation logic, not the rules themselves
        Rule mockRule1 = mock(Rule.class);
        when(mockRule1.evaluate(any(Observation.class)))
                .thenReturn(new Violation("Rule1", "First mock violation", 150));

        Rule mockRule2 = mock(Rule.class);
        when(mockRule2.evaluate(any(Observation.class)))
                .thenReturn(new Violation("Rule2", "Second mock violation", 250));

        RadarSystem radar = new RadarSystem(Arrays.asList(mockRule1, mockRule2));
        Observation obs = new Observation("TST-999", LocalDateTime.now(), CarType.PRIVATE, 80, true);

        // process and check output
        radar.processObservation(obs);
        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Traffic fine for car TST-999"));
        assertTrue(output.contains("Total amount: 400 EGP")); // should perfectly sum 150 + 250
        assertTrue(output.contains("First mock violation : 150 EGP"));
    }

    @Test
    void testProcessObservationWithNoViolations() {
        // mock a rule that returns null (meaning no violation occurred)
        Rule mockRule = mock(Rule.class);
        when(mockRule.evaluate(any(Observation.class))).thenReturn(null);

        RadarSystem radar = new RadarSystem(Collections.singletonList(mockRule));
        radar.processObservation(new Observation("PERFECT", LocalDateTime.now(), CarType.PRIVATE, 50, true));

        // shouldn't print anything if there are no fines
        assertTrue(outputStreamCaptor.toString().trim().isEmpty());
    }
}