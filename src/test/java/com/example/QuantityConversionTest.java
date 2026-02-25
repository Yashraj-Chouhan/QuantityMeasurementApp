package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityConversionTest {

	private static final double EPSILON = 1e-6;

	@Test
	public void testConversion_FeetToInches() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
		assertEquals(12.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	public void testConversion_InchesToFeet() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(24.0, LengthUnit.INCHES).convertTo(LengthUnit.FEET);
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	public void testConversion_YardsToInches() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.YARDS).convertTo(LengthUnit.INCHES);
		assertEquals(36.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_InchesToYards() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(72.0, LengthUnit.INCHES).convertTo(LengthUnit.YARDS);
		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_CentimetersToInches() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(2.54, LengthUnit.CENTIMETERS).convertTo(LengthUnit.INCHES);
		assertEquals(1.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_FeetToYards() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(6.0, LengthUnit.FEET).convertTo(LengthUnit.YARDS);
		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_RoundTrip_PreservesValue() {
		QuantityLength<LengthUnit> original = new QuantityLength<>(3.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> converted = original.convertTo(LengthUnit.INCHES).convertTo(LengthUnit.FEET);
		assertEquals(original, converted);
	}

	@Test
	public void testConversion_ZeroValue() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(0.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(0.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testConversion_NegativeValue() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(-1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(-12.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(1.0, LengthUnit.FEET).convertTo(null));
	}

	@Test
	public void testConversion_NaNOrInfinite_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.NaN, LengthUnit.FEET));
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.POSITIVE_INFINITY, LengthUnit.INCHES));
	}

	@Test
	public void testConversion_SameUnit() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(5.0, LengthUnit.FEET).convertTo(LengthUnit.FEET);
		assertEquals(new QuantityLength<>(5.0, LengthUnit.FEET), result);
	}

	// Enum constant and base conversion checks
	@Test
	public void testLengthUnitEnum_FeetConstant() {
		assertEquals(12.0, LengthUnit.FEET.getConversionFactor(), EPSILON);
	}

	@Test
	public void testLengthUnitEnum_InchesConstant() {
		assertEquals(1.0, LengthUnit.INCHES.getConversionFactor(), EPSILON);
	}

	@Test
	public void testLengthUnitEnum_YardsConstant() {
		assertEquals(36.0, LengthUnit.YARDS.getConversionFactor(), EPSILON);
	}

	@Test
	public void testLengthUnitEnum_CentimetersConstant() {
		assertEquals(1.0 / 2.54, LengthUnit.CENTIMETERS.getConversionFactor(), EPSILON);
	}

	@Test
	public void testConvertToBaseUnit_FeetToInches() {
		assertEquals(60.0, LengthUnit.FEET.convertToBaseUnit(5.0), EPSILON);
	}

	@Test
	public void testConvertToBaseUnit_InchesToInches() {
		assertEquals(12.0, LengthUnit.INCHES.convertToBaseUnit(12.0), EPSILON);
	}

	@Test
	public void testConvertToBaseUnit_YardsToInches() {
		assertEquals(36.0, LengthUnit.YARDS.convertToBaseUnit(1.0), EPSILON);
	}

	@Test
	public void testConvertToBaseUnit_CentimetersToInches() {
		assertEquals(12.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), EPSILON);
	}

	@Test
	public void testConvertFromBaseUnit_InchesToFeet() {
		assertEquals(1.0, LengthUnit.FEET.convertFromBaseUnit(12.0), EPSILON);
	}

	@Test
	public void testConvertFromBaseUnit_InchesToInches() {
		assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(12.0), EPSILON);
	}

	@Test
	public void testConvertFromBaseUnit_InchesToYards() {
		assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(36.0), EPSILON);
	}

	@Test
	public void testConvertFromBaseUnit_InchesToCentimeters() {
		assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(12.0), EPSILON);
	}

	// Weight conversions
	@Test
	public void testConversion_PoundToKilogram() {
		QuantityLength<WeightUnit> converted = new QuantityLength<>(2.204624, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM);
		assertEquals(1.0, converted.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, converted.getUnit());
	}

	@Test
	public void testConversion_KilogramToPound() {
		QuantityLength<WeightUnit> converted = new QuantityLength<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND);
		assertEquals(2.204624, converted.getValue(), EPSILON);
		assertEquals(WeightUnit.POUND, converted.getUnit());
	}

	@Test
	public void testConversion_SameUnit_Weight() {
		QuantityLength<WeightUnit> converted = new QuantityLength<>(5.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.KILOGRAM);
		assertEquals(new QuantityLength<>(5.0, WeightUnit.KILOGRAM), converted);
	}

	@Test
	public void testRoundTripConversion_RefactoredDesign() {
		QuantityLength<LengthUnit> original = new QuantityLength<>(5.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> converted = original.convertTo(LengthUnit.CENTIMETERS).convertTo(LengthUnit.FEET);
		assertEquals(original, converted);
	}
}