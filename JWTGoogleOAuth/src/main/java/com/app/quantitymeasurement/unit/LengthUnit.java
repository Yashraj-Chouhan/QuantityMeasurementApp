package com.app.quantitymeasurement.unit;

/**
 * LengthUnit
 *
 * Enumeration of supported length measurement units.
 *
 * Implements {@link IMeasurable} and {@link SupportsArithmetic}, enabling full
 * arithmetic support (addition, subtraction, division) in the service layer.
 *
 * <p><b>Base unit:</b> INCHES. All conversions are performed by first converting to
 * inches and then to the target unit. Results are rounded to 6 decimal places.</p>
 *
 * <table border="1">
 *   <caption>Conversion factors to INCHES</caption>
 *   <tr><th>Unit</th><th>Factor</th></tr>
 *   <tr><td>FEET</td><td>12.0</td></tr>
 *   <tr><td>INCHES</td><td>1.0</td></tr>
 *   <tr><td>YARDS</td><td>36.0</td></tr>
 *   <tr><td>CENTIMETERS</td><td>1 / 2.54 ≈ 0.393701</td></tr>
 * </table>
 *
 * @author Abhishek Puri Goswami
 * @version 17.0
 * @since 1.0
 */
public enum LengthUnit implements IMeasurable, SupportsArithmetic {

    FEET(12.0),
    INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(1 / 2.54);

    /** Multiplier used to convert a value in this unit to the base unit (INCHES). */
    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    /**
     * Converts {@code value} from this unit to INCHES.
     * Result is rounded to 6 decimal places.
     *
     * @param value value in this unit
     * @return equivalent value in INCHES
     */
    @Override
    public double convertToBaseUnit(double value) {
        return Math.round(value * conversionFactor * 1_000_000.0) / 1_000_000.0;
    }

    /**
     * Converts {@code baseValue} from INCHES to this unit.
     * Result is rounded to 6 decimal places.
     *
     * @param baseValue value in INCHES
     * @return equivalent value in this unit
     */
    @Override
    public double convertFromBaseUnit(double baseValue) {
        return Math.round(baseValue / conversionFactor * 1_000_000.0) / 1_000_000.0;
    }

    /**
     * Returns the enum constant name as the unit identifier.
     *
     * @return unit name
     */
    @Override
    public String getUnitName() {
        return name();
    }

    /**
     * Returns {@code "LengthUnit"} to identify the measurement category.
     *
     * @return measurement type name
     */
    @Override
    public String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    /**
     * Returns the {@code LengthUnit} constant whose name matches {@code unitName}
     * (case-insensitive).
     *
     * @param unitName name of the unit to look up
     * @return matching {@code LengthUnit} constant
     * @throws IllegalArgumentException if no match is found
     */
    @Override
    public IMeasurable getUnitInstance(String unitName) {
        for (LengthUnit unit : LengthUnit.values()) {
            if (unit.getUnitName().equalsIgnoreCase(unitName)) return unit;
        }
        throw new IllegalArgumentException("Invalid length unit: " + unitName);
    }
}
