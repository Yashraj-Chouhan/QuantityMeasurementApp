# UC5 – Unit-to-Unit Conversion (Same Measurement Type)

## Description
Extends UC4 by adding explicit unit-to-unit conversion support.

Provides a public API:
static double convert(double value, LengthUnit source, LengthUnit target)

Supports conversion across:
- FEET
- INCHES
- YARDS
- CENTIMETERS

## Objective
- Convert between any supported length units
- Normalize to a base unit before conversion
- Preserve mathematical accuracy within floating-point precision

## Conversion Formula
result = value × (source.factor / target.factor)

All conversions are normalized through a common base unit.

## Validation Rules
- value must be finite (no NaN / Infinity)
- source and target must be non-null
- Unsupported units throw IllegalArgumentException

## Example

convert(1.0, FEET, INCHES) → 12.0  
convert(3.0, YARDS, FEET) → 9.0  
convert(36.0, INCHES, YARDS) → 1.0  
convert(1.0, CENTIMETERS, INCHES) → 0.393701  
convert(0.0, FEET, INCHES) → 0.0  

## Features
- Base unit normalization
- Bidirectional conversion
- Round-trip accuracy within epsilon
- Zero and negative value handling
- Precision-safe floating point comparison
- Immutable enum-based conversion factors

## Concepts Covered
- Enum with conversion factors
- Immutability
- Value object semantics
- Method overriding (equals, toString)
- Method overloading
- Exception handling
- API design principles
- DRY principle continuation

## Benefits
- Clean conversion API
- Centralized conversion logic
- Scalable for adding new units
- Backward compatible (UC1–UC4 preserved)
- Precision-aware implementation
