package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.QuantityLength;
import com.example.QuantityLength.LengthUnit;

public class QuantityMeasurementAppTest {

	@Test
	void testEquality_FeetToFeet_SameValue() {
		QuantityLength feet1 = new QuantityLength(1.0,LengthUnit.FEET);
		QuantityLength feet2 = new QuantityLength(1.0,LengthUnit.FEET);
		assertTrue(feet1.equals(feet2));
		
	}
	
	@Test
	void testEquality_InchToInch_SameValue() {
		QuantityLength inch1 = new QuantityLength(1.0,LengthUnit.INCHES);
		QuantityLength inch2 = new QuantityLength(1.0,LengthUnit.INCHES);
		assertTrue(inch1.equals(inch2));
	}

	@Test
	public void testEquality_InchToFeet_EquivalentValue() {
		QuantityLength inch1 = new QuantityLength(12.0,LengthUnit.INCHES);
		QuantityLength feet2 = new QuantityLength(1.0,LengthUnit.FEET);
		assertEquals(inch1,feet2);
		
	}

	@Test
	void testEquality_FeetToFeet_DifferentValue() {
		QuantityLength feet1 = new QuantityLength(1.0,LengthUnit.FEET);
		QuantityLength feet2 = new QuantityLength(2.0,LengthUnit.FEET);
		assertFalse(feet1.equals(feet2));
	}
	
	@Test
	void ttestEquality_InchToInch_DifferentValue() {
		QuantityLength inches1 = new QuantityLength(1.0,LengthUnit.INCHES);
		QuantityLength inches2 = new QuantityLength(2.0,LengthUnit.INCHES);
		assertFalse(inches1.equals(inches2));
	}
	
	@Test
	void testEquality_NullComparison(){
		QuantityLength inches1 = new QuantityLength(1.0,LengthUnit.FEET);
		assertNotEquals(inches1,(null));
	}
	

	@Test
	void testEquality_SameReference() {
		QuantityLength inches1 = new QuantityLength(1.0,LengthUnit.INCHES);
		assertTrue(inches1.equals(inches1));
	}
	
	@Test
	void testEquality_InvalidUnit() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new QuantityLength(1.0,null);
		});
	}
	
	@Test
	void testEquality_NullUnit() {
		QuantityLength feet1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength invalid = null;
		assertNotEquals(feet1, invalid);
	}
	
	@Test
	public void testEquality_DifferentClass() {
		QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertFalse(l1.equals("1.0"));
	}
	
	@Test
	public void testEquality_NaN() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, QuantityLength.LengthUnit.FEET));
	}

	@Test
	public void testEquality_Infinity() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityLength(Double.POSITIVE_INFINITY, QuantityLength.LengthUnit.INCHES));
	}
	
}