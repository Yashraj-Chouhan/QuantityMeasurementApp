package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeightQuantityTest {

	private static final double EPSILON = 1e-6;

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
	public void testAddition_WeightSameUnit() {
		QuantityLength<WeightUnit> sum = new QuantityLength<>(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityLength<>(2.0, WeightUnit.KILOGRAM));
		assertEquals(new QuantityLength<>(3.0, WeightUnit.KILOGRAM), sum);
	}

	@Test
	public void testAddition_WeightCrossUnit() {
		QuantityLength<WeightUnit> sum = new QuantityLength<>(1.0, WeightUnit.KILOGRAM).add(new QuantityLength<>(1000.0, WeightUnit.GRAM),
				WeightUnit.GRAM);
		assertEquals(new QuantityLength<>(2000.0, WeightUnit.GRAM), sum);
	}

	@Test
	public void testAddition_WeightNegativeValues() {
		QuantityLength<WeightUnit> sum = new QuantityLength<>(5.0, WeightUnit.KILOGRAM)
				.add(new QuantityLength<>(-2.0, WeightUnit.KILOGRAM));
		assertEquals(new QuantityLength<>(3.0, WeightUnit.KILOGRAM), sum);
	}

	@Test
	public void testEquality_KilogramToPound() {
		assertEquals(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(2.204624, WeightUnit.POUND));
	}

	@Test
	public void testEquality_GramToPound_EquivalentValue() {
		assertEquals(new QuantityLength<>(453.592370, WeightUnit.GRAM), new QuantityLength<>(1.0, WeightUnit.POUND));
	}

	@Test
	public void testEquality_ZeroValue() {
		assertEquals(new QuantityLength<>(0.0, WeightUnit.KILOGRAM), new QuantityLength<>(0.0, WeightUnit.GRAM));
	}

	@Test
	public void testEquality_NegativeWeight() {
		assertEquals(new QuantityLength<>(-1.0, WeightUnit.KILOGRAM), new QuantityLength<>(-1000.0, WeightUnit.GRAM));
	}

	@Test
	public void testEquality_LargeWeightValue() {
		assertEquals(new QuantityLength<>(1_000_000.0, WeightUnit.GRAM), new QuantityLength<>(1000.0, WeightUnit.KILOGRAM));
	}

	@Test
	public void testEquality_SmallWeightValue() {
		assertEquals(new QuantityLength<>(0.001, WeightUnit.KILOGRAM), new QuantityLength<>(1.0, WeightUnit.GRAM));
	}

	@Test
	public void testEquality_NullUnit_WeightThrows() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(1.0, (WeightUnit) null));
	}

	@Test
	public void testEquality_NullComparison_Weight() {
		assertFalse(new QuantityLength<>(1.0, WeightUnit.KILOGRAM).equals(null));
	}

	@Test
	public void testEquality_SameReference_Weight() {
		QuantityLength<WeightUnit> w = new QuantityLength<>(2.0, WeightUnit.KILOGRAM);
		assertEquals(w, w);
	}
}