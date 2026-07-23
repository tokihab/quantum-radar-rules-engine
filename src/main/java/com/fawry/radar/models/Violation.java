package com.fawry.radar.models;

// details about a specific broken rule
public class Violation {
    public String ruleName;
    public String description;
    public int fee;

    public Violation(String ruleName, String description, int fee) {
        this.ruleName = ruleName;
        this.description = description;
        this.fee = fee;
    }
}