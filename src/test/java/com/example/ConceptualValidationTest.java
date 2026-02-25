package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConceptualValidationTest {

	private static final double EPSILON = 1e-6;

	// IMeasurable Interface Implementation
	@Test
	public void testIMeasurableInterface_LengthUnitImplementation() {
		IMeasurable unit = LengthUnit.FEET;
		assertEquals("FEET", unit.getUnitName());
		assertEquals(12.0, unit.getConversionFactor(), EPSILON);
		assertEquals(12.0, unit.convertToBaseUnit(1.0), EPSILON);
		assertEquals(1.0, unit.convertFromBaseUnit(12.0), EPSILON);
	}

	@Test
	public void testIMeasurableInterface_WeightUnitImplementation() {
		IMeasurable unit = WeightUnit.KILOGRAM;
		assertEquals("KILOGRAM", unit.getUnitName());
		assertEquals(1.0, unit.getConversionFactor(), EPSILON);
		assertEquals(1.0, unit.convertToBaseUnit(1.0), EPSILON);
		assertEquals(1.0, unit.convertFromBaseUnit(1.0), EPSILON);
	}

	@Test
	public void testIMeasurableInterface_ConsistentBehavior() {
		IMeasurable length = LengthUnit.INCHES;
		IMeasurable weight = WeightUnit.GRAM;
		assertNotNull(length.getUnitName());
		assertNotNull(weight.getUnitName());
	}

	// Generic QuantityLength Class Functionality
	@Test
	public void testGenericQuantityLength_LengthOperations_Equality() {
		assertTrue(new QuantityLength<>(1.0, LengthUnit.FEET).equals(new QuantityLength<>(12.0, LengthUnit.INCHES)));
	}

	@Test
	public void testGenericQuantityLengthLength_WeightOperations_Equality() {
		assertTrue(new QuantityLength<>(1.0, WeightUnit.KILOGRAM).equals(new QuantityLength<>(1000.0, WeightUnit.GRAM)));
	}

	@Test
	public void testGenericQuantityLengthLength_LengthOperations_Conversion() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(12.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testGenericQuantityLengthLength_WeightOperations_Conversion() {
		QuantityLength<WeightUnit> result = new QuantityLength<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
		assertEquals(new QuantityLength<>(1000.0, WeightUnit.GRAM), result);
	}

	@Test
	public void testGenericQuantityLength_LengthOperations_Addition() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES),
				LengthUnit.FEET);
		assertEquals(new QuantityLength<>(2.0, LengthUnit.FEET), result);
	}

	@Test
	public void testGenericQuantityLength_WeightOperations_Addition() {
		QuantityLength<WeightUnit> result = new QuantityLength<>(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityLength<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);
		assertEquals(new QuantityLength<>(2.0, WeightUnit.KILOGRAM), result);
	}

	// Cross-Category Comparison Prevention
	@Test
	public void testCrossCategoryPrevention_LengthVsWeight() {
		assertFalse(new QuantityLength<>(1.0, LengthUnit.FEET).equals(new QuantityLength<>(1.0, WeightUnit.KILOGRAM)));
	}

	// Constructor Validation
	@Test
	public void testGenericQuantityLength_ConstructorValidation_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(1.0, null));
	}

	@Test
	public void testGenericQuantityLength_ConstructorValidation_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.NaN, LengthUnit.FEET));
	}

	// HashCode & Equals Contract
	@Test
	public void testHashCode_GenericQuantityLength_Consistency() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(3.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(36.0, LengthUnit.INCHES);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void testEquals_GenericQuantityLength_ContractPreservation() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);
		QuantityLength<LengthUnit> c = new QuantityLength<>(1.0 / 3.0, LengthUnit.YARDS);
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
		assertTrue(a.equals(c) && b.equals(c));
	}

	@Test
	public void testImmutability_GenericQuantityLength() {
		QuantityLength<LengthUnit> q = new QuantityLength<>(1.0, LengthUnit.FEET);
		assertEquals(1.0, q.getValue(), EPSILON);
		assertEquals(LengthUnit.FEET, q.getUnit());
	}
}