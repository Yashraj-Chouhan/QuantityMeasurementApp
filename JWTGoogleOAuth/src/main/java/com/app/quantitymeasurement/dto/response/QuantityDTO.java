package com.app.quantitymeasurement.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuantityDTO
 *
 * Data Transfer Object that carries a single quantity — a numeric value paired with
 * its measurement unit — between the API layer and the service layer.
 *
 * <p>Supported measurement categories and their units:</p>
 * <ul>
 *   <li><b>LengthUnit</b>      — FEET, INCHES, YARDS, CENTIMETERS</li>
 *   <li><b>VolumeUnit</b>      — LITRE, MILLILITRE, GALLON</li>
 *   <li><b>WeightUnit</b>      — KILOGRAM, GRAM, POUND</li>
 *   <li><b>TemperatureUnit</b> — CELSIUS, FAHRENHEIT, KELVIN</li>
 * </ul>
 *
 * <p>Bean Validation constraints ensure that the JSON payload received from an API
 * client is complete and internally consistent before the service processes it.
 * The custom {@link #isUnitValidForMeasurementType()} method provides cross-field
 * validation to confirm that the {@code unit} value is a valid constant for the
 * declared {@code measurementType}.</p>
 *
 * <p>Lombok {@code @Data} generates getters, setters, {@code toString}, {@code equals},
 * and {@code hashCode}. {@code @NoArgsConstructor} provides the no-arg constructor
 * required by Jackson for JSON deserialisation.</p>
 *
 * @author Abhishek Puri Goswami
 * @version 17.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class QuantityDTO {

    /**
     * Common contract for the unit enumerations nested inside this DTO.
     * Each enum must supply its name and its measurement category.
     */
    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    /**
     * Supported length units.
     */
    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS;

        @Override public String getUnitName()        { return this.name(); }
        @Override public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    /**
     * Supported volume units.
     */
    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;

        @Override public String getUnitName()        { return this.name(); }
        @Override public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    /**
     * Supported weight units.
     */
    public enum WeightUnit implements IMeasurableUnit {
        KILOGRAM, GRAM, POUND;

        @Override public String getUnitName()        { return this.name(); }
        @Override public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    /**
     * Supported temperature units.
     */
    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;

        @Override public String getUnitName()        { return this.name(); }
        @Override public String getMeasurementType() { return this.getClass().getSimpleName(); }
    }

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    /** Numeric value of the quantity. Must not be null. */
    @NotNull(message = "Value must not be null")
    private Double value;

    /** Name of the unit (e.g., {@code FEET}, {@code KILOGRAM}). Must not be empty. */
    @NotEmpty(message = "Unit must not be empty")
    private String unit;

    /**
     * Measurement category of the unit.
     * Must be one of {@code LengthUnit}, {@code VolumeUnit}, {@code WeightUnit},
     * or {@code TemperatureUnit}.
     */
    @NotEmpty(message = "Measurement type must not be empty")
    @Pattern(
        regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit",
        message = "Measurement type must be one of: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit"
    )
    private String measurementType;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Creates a QuantityDTO from a typed unit enum constant.
     *
     * @param value numeric quantity value
     * @param unit  unit enum constant (e.g., {@code LengthUnit.FEET})
     */
    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value           = value;
        this.unit            = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    /**
     * Creates a QuantityDTO from raw string values.
     *
     * @param value           numeric quantity value
     * @param unit            unit name string
     * @param measurementType measurement category string
     */
    public QuantityDTO(double value, String unit, String measurementType) {
        this.value           = value;
        this.unit            = unit;
        this.measurementType = measurementType;
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    /**
     * Returns the quantity value as a primitive {@code double}.
     * Returns {@code 0.0} when the backing {@code Double} field is {@code null}.
     *
     * @return quantity value
     */
    public double getValue() {
        return value == null ? 0.0 : value;
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    /**
     * Cross-field validation: verifies that {@code unit} is a valid constant for
     * the declared {@code measurementType}.
     *
     * <p>Returns {@code true} when either field is {@code null} so that the
     * {@code @NotEmpty} constraints handle the null case with their own messages.</p>
     *
     * @return {@code true} if the unit is valid for the measurement type
     */
    @jakarta.validation.constraints.AssertTrue(
        message = "Unit must be valid for the specified measurement type")
    public boolean isUnitValidForMeasurementType() {
        if (unit == null || measurementType == null) return true;
        try {
            switch (measurementType) {
                case "LengthUnit":      LengthUnit.valueOf(unit);      break;
                case "VolumeUnit":      VolumeUnit.valueOf(unit);      break;
                case "WeightUnit":      WeightUnit.valueOf(unit);      break;
                case "TemperatureUnit": TemperatureUnit.valueOf(unit); break;
                default: return false;
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // Object overrides
    // -------------------------------------------------------------------------

    /**
     * Returns a human-readable representation such as {@code "2 FEET"}.
     *
     * @return formatted quantity string
     */
    @Override
    public String toString() {
        return String.format("%s %s",
            Double.toString(value == null ? 0.0 : value).replaceAll("\\.0+$", ""),
            unit);
    }
}
