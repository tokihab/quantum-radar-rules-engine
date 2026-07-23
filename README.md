# Radar System Rules Engine

A traffic radar simulation built around an extensible **Rules Engine** using the **Strategy Pattern**. The system evaluates vehicle observations (speed, seatbelt status, etc.) against a set of pluggable rules and generates fines — without ever needing to modify the core `RadarSystem` class when new rules are added.

Implementations are provided in **Java**, along with a matching unit test suite for each.

---

## Core Requirement

> The system must be **extensible**, allowing flexibility for adding rules without modifying the Radar.

This is achieved via dependency injection: `RadarSystem` depends only on an `IRule` / `Rule` interface, never on concrete rule classes. New rules (e.g. bus speed limits, registration checks) can be added by simply authoring a new class that implements the interface — the `RadarSystem` class itself remains untouched (Open/Closed Principle).

---

## Architecture

### 1. Domain Models
| Class | Purpose |
|---|---|
| `Observation` | A single radar reading: plate number, timestamp, car type, speed, seatbelt status |
| `Violation` | A single rule breach: rule name, description, fee |
| `Fine` | Aggregates all violations for one observation, exposes `TotalAmount` |

### 2. Rules Engine (Strategy Pattern)
| Interface/Class | Purpose |
|---|---|
| `IRule` / `Rule` | Common interface: `Evaluate(Observation) -> Violation?` |
| `SpeedRule` | Truck max 60, Private max 80. Fee: 300 EGP |
| `SeatbeltRule` | Seatbelt must be fastened. Fee: 100 EGP |

### 3. `RadarSystem`
- Accepts a collection of `IRule` implementations via constructor injection.
- `ProcessObservation(obs)` — runs all active rules against an observation, aggregates violations into a `Fine`, updates a fines ledger and a per-rule violation counter, and prints the result.
- `PrintAllFines()` — prints total fines per plate.
- `PrintViolatedRulesCount()` — prints how many times each rule was violated.

### 4. `Main` / `Program`
Demonstrates the system end-to-end: constructs the active rule set, feeds in a sample observation, and prints the resulting fine and aggregate reports.

---

## Design Principles Applied

- **Open/Closed Principle** — `RadarSystem` is closed for modification, open for extension via new `IRule` implementations.
- **Single Responsibility Principle** — each rule class has exactly one reason to change.
- **Dependency Injection** — rules are supplied to `RadarSystem`, not hard-coded.

---

## Adding a New Rule

1. Create a class implementing `IRule` (C#) or `Rule` (Java).
2. Implement `Evaluate`/`evaluate` to inspect the `Observation` and return a `Violation` (or `null`/`None` if compliant).
3. Add an instance of the new class to the list passed into `RadarSystem`'s constructor.

No existing code needs to change.

---

## Testing

Both implementations ship with a full test suite that mocks the rule stream so the engine's aggregation logic is tested independently of concrete rule behavior:

| Language | Framework | Mocking | Console Capture |
|---|---|---|---|
| C# | xUnit (`[Theory]`, `[Fact]`) | Moq | `StringWriter` redirect of `Console.Out` |
| Java | JUnit 5 (`@ParameterizedTest`) | Mockito | `ByteArrayOutputStream` redirect of `System.out` |

### Coverage includes
- `SpeedRule` — parameterized cases for truck/private limits, boundary values (at-limit vs. over-limit).
- `SeatbeltRule` — fastened vs. unfastened cases.
- `RadarSystem` — multi-violation fee aggregation, running violation counts across multiple observations, and the no-violation (silent) case.

---

## Project Structure

```
/csharp
  RadarSystem.cs        # Domain models, IRule, SpeedRule, SeatbeltRule, RadarSystem, Program
  RadarSystemTests.cs   # xUnit + Moq test suite

/java
  Main.java              # Domain models, Rule, SpeedRule, SeatbeltRule, RadarSystem, Main
  RadarSystemTests.java  # JUnit 5 + Mockito test suite
```

## Source

based on the requirements in `nsquare - quantum Radar - slope12.pdf`.
