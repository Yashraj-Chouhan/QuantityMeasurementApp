package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BackwardCompatibilityTest {

	@Test
	public void testBackwardCompatibility_UC1EqualityTests() {
		assertEquals(new QuantityLength<>(3.0, LengthUnit.FEET), new QuantityLength<>(36.0, LengthUnit.INCHES));
	}

	@Test
	public void testBackwardCompatibility_UC5ConversionTests() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.YARDS).convertTo(LengthUnit.FEET);
		assertEquals(new QuantityLength<>(3.0, LengthUnit.FEET), result);
	}

	@Test
	public void testBackwardCompatibility_UC6AdditionTests() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(2.0, LengthUnit.FEET).add(new QuantityLength<>(24.0, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(4.0, LengthUnit.FEET), result);
	}

	@Test
	public void testBackwardCompatibility_UC7AdditionWithTargetUnitTests() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(2.0, LengthUnit.FEET).add(new QuantityLength<>(24.0, LengthUnit.INCHES),
				LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(48.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testQuantityLengthLengthRefactored_Equality() {
		assertTrue(new QuantityLength<>(1.0, LengthUnit.FEET).equals(new QuantityLength<>(12.0, LengthUnit.INCHES)));
	}

	@Test
	public void testQuantityLengthLengthRefactored_ConvertTo() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(12.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testQuantityLengthLengthRefactored_Add() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES),
				LengthUnit.FEET);
		assertEquals(new QuantityLength<>(2.0, LengthUnit.FEET), result);
	}

	@Test
	public void testQuantityLengthLengthRefactored_AddWithTargetUnit() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES),
				LengthUnit.YARDS);
		assertEquals(new QuantityLength<>(0.666667, LengthUnit.YARDS), result);
	}

	@Test
	public void testQuantityLengthLengthRefactored_NullUnitThrows() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(1.0, (LengthUnit) null));
	}

	@Test
	public void testQuantityLengthLengthRefactored_InvalidValueThrows() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.NaN, LengthUnit.FEET));
	}

	@Test
	public void testBackwardCompatibility_UC1EqualityTests_Alternate() {
		assertEquals(new QuantityLength<>(3.0, LengthUnit.FEET), new QuantityLength<>(36.0, LengthUnit.INCHES));
	}

	@Test
	public void testBackwardCompatibility_UC5ConversionTests_Alternate() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.YARDS).convertTo(LengthUnit.FEET);
		assertEquals(new QuantityLength<>(3.0, LengthUnit.FEET), result);
	}

	@Test
	public void testBackwardCompatibility_UC6AdditionTests_Alternate() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(2.0, LengthUnit.FEET).add(new QuantityLength<>(24.0, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(4.0, LengthUnit.FEET), result);
	}

	@Test
	public void testBackwardCompatibility_UC7AdditionWithTargetUnitTests_Alternate() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(2.0, LengthUnit.FEET).add(new QuantityLength<>(24.0, LengthUnit.INCHES),
				LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(48.0, LengthUnit.INCHES), result);
	}
}