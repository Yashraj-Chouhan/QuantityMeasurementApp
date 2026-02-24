package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.QuantityMeasurementApp.Feet;

public class QuantityMeasurementAppTest {

	@Test
	public void testFeetEquality_SameValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);
		assertTrue(feet1.equals(feet2), "Feet objects with same value should be equal");
	}

	@Test
	public void testFeetEquality_DifferentValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(2.0);
		assertFalse(feet1.equals(feet2), "Feet objects with different values should not be equal");
	}

	@Test
	public void testFeetEquality_NullComparison() {
		Feet feet1 = new Feet(1.0);
		assertFalse(feet1.equals(null), "Feet object should not be equal to null");
	}

	@Test
	public void testFeetEquality_DifferentClass() {
		Feet feet1 = new Feet(1.0);
		String notFeet = "1.0";
		assertFalse(feet1.equals(notFeet), "Feet object should not be equal to a different class");
	}

	@Test
	public void testFeetEquality_SameReference() {
		Feet feet1 = new Feet(1.0);
		assertTrue(feet1.equals(feet1), "Feet object should be equal to itself (reflexive property)");
	}
}