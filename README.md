# UC-14: Temperature Measurement with Selective Arithmetic Support

## Overview
UC-14 extends the Quantity Measurement Application by adding temperature measurement support (Celsius, Fahrenheit, Kelvin).  
Unlike length, weight, and volume, temperature does not support arithmetic operations on absolute values.

## Key Changes
- Added `TemperatureUnit` enum.
- Refactored `IMeasurable` to support optional arithmetic using default methods.
- Introduced `SupportsArithmetic` functional interface.
- Added validation for unsupported operations.
- Maintained backward compatibility with UC1–UC13.

## Supported Features
- Temperature conversion between all units.
- Equality comparison across temperature units.
- Cross-category comparison prevention.
- Type safety using generics.

## Restricted Operations
- Addition ❌
- Subtraction ❌
- Division ❌

Unsupported operations throw `UnsupportedOperationException`.

## Design Principles Applied
- Interface Segregation Principle (ISP)
- Open/Closed Principle
- Capability-Based Design
- Functional Interfaces & Lambda Expressions
- Default Methods for Interface Evolution

## Postconditions
- All previous use cases remain functional.
- Temperature supports conversion and equality only.
- Clear error handling for invalid operations.
- System remains scalable for future measurement categories.
