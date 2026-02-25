package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ArchitecturalTest {
	@Test
	public void testArchitecturalScalability_MultipleCategories() {
		assertDoesNotThrow(() -> LengthUnit.valueOf("FEET"));
		assertDoesNotThrow(() -> WeightUnit.valueOf("KILOGRAM"));
	}

	@Test
	public void testUnitImmutability() {
		assertTrue(LengthUnit.FEET instanceof Enum);
		assertTrue(LengthUnit.INCHES instanceof Enum);
		assertTrue(LengthUnit.YARDS instanceof Enum);
		assertTrue(LengthUnit.CENTIMETERS instanceof Enum);

		assertTrue(WeightUnit.KILOGRAM instanceof Enum);
		assertTrue(WeightUnit.GRAM instanceof Enum);
		assertTrue(WeightUnit.POUND instanceof Enum);
	}
}