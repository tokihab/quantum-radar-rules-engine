package com.fawry.radar.system;

import com.fawry.radar.models.Fine;
import com.fawry.radar.models.Observation;
import com.fawry.radar.models.Violation;
import com.fawry.radar.rules.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// the core engine that processes observations without needing to know specific rule details
public class RadarSystem {
    private final List<Rule> rules;
    
    // keeps track of all fines and rule violation counts globally
    private final Map<String, Integer> finesLedger = new HashMap<>();
    private final Map<String, Integer> violationCounts = new HashMap<>();

    // dependency injection: pass the active rules in when creating the system
    public RadarSystem(List<Rule> rules) {
        this.rules = rules;
    }

    public void processObservation(Observation obs) {
        Fine fine = new Fine(obs.plateNumber);

        // run the observation through every active rule
        for (Rule rule : rules) {
            Violation violation = rule.evaluate(obs);
            if (violation != null) {
                fine.violations.add(violation);
                
                // update our global count for this specific rule
                violationCounts.put(violation.ruleName, violationCounts.getOrDefault(violation.ruleName, 0) + 1);
            }
        }

        // if they broke any rules, record the fine and print it
        if (!fine.violations.isEmpty()) {
            finesLedger.put(obs.plateNumber, finesLedger.getOrDefault(obs.plateNumber, 0) + fine.getTotalFee());
            printFine(fine);
        }
    }

    private void printFine(Fine fine) {
        System.out.println("Traffic fine for car " + fine.plateNumber);
        // keeping the output text exactly as the PDF requested
        System.out.println("Total amount: " + fine.getTotalFee() + " EGP");
        System.out.println("Violations:");
        for (Violation v : fine.violations) {
            System.out.println(v.description + " : " + v.fee + " EGP");
        }
        System.out.println();
    }

    // fulfill the "get all fines" requirement
    public void getAllFines() {
        System.out.println("--- All Fines ---");
        for (Map.Entry<String, Integer> entry : finesLedger.entrySet()) {
            System.out.println(entry.getKey() + " with total amount " + entry.getValue());
        }
        System.out.println();
    }

    // fulfill the "get all violated rules with count" requirement
    public void getAllViolatedRules() {
        System.out.println("--- Violated Rules Count ---");
        for (Map.Entry<String, Integer> entry : violationCounts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();
    }
}