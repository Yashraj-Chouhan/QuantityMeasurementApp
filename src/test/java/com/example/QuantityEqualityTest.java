package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityEqualityTest {

	@Test
	public void testEquality_YardToYard_SameValue() {
		assertEquals(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(1.0, LengthUnit.YARDS));
	}

	@Test
	public void testEquality_YardToYard_DifferentValue() {
		assertNotEquals(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(2.0, LengthUnit.YARDS));
	}

	@Test
	public void testEquality_YardToFeet_EquivalentValue() {
		assertEquals(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(3.0, LengthUnit.FEET));
	}

	@Test
	public void testEquality_FeetToYard_EquivalentValue() {
		assertEquals(new QuantityLength<>(3.0, LengthUnit.FEET), new QuantityLength<>(1.0, LengthUnit.YARDS));
	}

	@Test
	public void testEquality_YardToInches_EquivalentValue() {
		assertEquals(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(36.0, LengthUnit.INCHES));
	}

	@Test
	public void testEquality_InchesToYard_EquivalentValue() {
		assertEquals(new QuantityLength<>(36.0, LengthUnit.INCHES), new QuantityLength<>(1.0, LengthUnit.YARDS));
	}

	@Test
	public void testEquality_YardToFeet_NonEquivalentValue() {
		assertNotEquals(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(2.0, LengthUnit.FEET));
	}

	@Test
	public void testEquality_CentimetersToInches_EquivalentValue() {
		assertEquals(new QuantityLength<>(1.0, LengthUnit.CENTIMETERS), new QuantityLength<>(0.393701, LengthUnit.INCHES));
	}

	@Test
	public void testEquality_CentimetersToFeet_NonEquivalentValue() {
		assertNotEquals(new QuantityLength<>(1.0, LengthUnit.CENTIMETERS), new QuantityLength<>(1.0, LengthUnit.FEET));
	}

	@Test
	public void testEquality_MultiUnit_TransitiveProperty() {
		assertEquals(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(3.0, LengthUnit.FEET));
		assertEquals(new QuantityLength<>(3.0, LengthUnit.FEET), new QuantityLength<>(36.0, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(36.0, LengthUnit.INCHES));
	}

	@Test
	public void testEquality_NullUnitThrows() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(1.0, null));
	}

	@Test
	public void testEquality_SameReference() {
		QuantityLength<LengthUnit> yard = new QuantityLength<>(2.0, LengthUnit.YARDS);
		assertEquals(yard, yard);
	}

	@Test
	public void testEquality_NullComparison() {
		QuantityLength<LengthUnit> yard = new QuantityLength<>(2.0, LengthUnit.YARDS);
		assertNotEquals(yard, null);
	}

	@Test
	public void testEquality_AllUnits_ComplexScenario() {
		assertEquals(new QuantityLength<>(2.0, LengthUnit.YARDS), new QuantityLength<>(6.0, LengthUnit.FEET));
		assertEquals(new QuantityLength<>(6.0, LengthUnit.FEET), new QuantityLength<>(72.0, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(2.0, LengthUnit.YARDS), new QuantityLength<>(72.0, LengthUnit.INCHES));
	}

	@Test
	public void testEquality_CentimetersToCentimeters_SameValue() {
		assertEquals(new QuantityLength<>(2.0, LengthUnit.CENTIMETERS), new QuantityLength<>(2.0, LengthUnit.CENTIMETERS));
	}

	@Test
	public void testEquality_CentimetersToCentimeters_DifferentValue() {
		assertNotEquals(new QuantityLength<>(2.0, LengthUnit.CENTIMETERS), new QuantityLength<>(3.0, LengthUnit.CENTIMETERS));
	}

	@Test
	public void testEquality_InchesToCentimeters_EquivalentValue() {
		assertEquals(new QuantityLength<>(0.393701, LengthUnit.INCHES), new QuantityLength<>(1.0, LengthUnit.CENTIMETERS));
	}

	@Test
	public void testEquality_DifferentClass() {
		QuantityLength<LengthUnit> yard = new QuantityLength<>(2.0, LengthUnit.YARDS);
		assertFalse(yard.equals("2.0"));
	}

	@Test
	public void testEquality_NaNThrows() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.NaN, LengthUnit.FEET));
	}

	@Test
	public void testEquality_InfinityThrows() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityLength<>(Double.POSITIVE_INFINITY, LengthUnit.CENTIMETERS));
	}

	@Test
	public void testEquality_VerySmallValues_WithinEpsilon() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1e-9, LengthUnit.INCHES);
		QuantityLength<LengthUnit> b = new QuantityLength<>(1.0000005e-9, LengthUnit.INCHES);
		assertEquals(a, b);

		QuantityLength<LengthUnit> c = new QuantityLength<>(1e-8, LengthUnit.FEET);
		QuantityLength<LengthUnit> d = new QuantityLength<>(1.2e-7, LengthUnit.INCHES);
		assertEquals(c, d);

		QuantityLength<LengthUnit> e = new QuantityLength<>(1e-9, LengthUnit.INCHES);
		QuantityLength<LengthUnit> f = new QuantityLength<>(1e-9 + 2e-6, LengthUnit.INCHES);
		assertNotEquals(e, f);
	}

	@Test
	public void testEquality_SmallDifference_OutsideTolerance() {
		assertNotEquals(new QuantityLength<>(1.0000003, LengthUnit.FEET), new QuantityLength<>(1.0, LengthUnit.FEET));
	}

	@Test
	public void testEquality_SmallDifference_InsideTolerance() {
		assertEquals(new QuantityLength<>(1.00000008, LengthUnit.FEET), new QuantityLength<>(1.0, LengthUnit.FEET));
	}

	@Test
	public void testHashCode_EqualObjectsHaveSameHashCode() {
		QuantityLength<LengthUnit> a1 = new QuantityLength<>(5.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> a2 = new QuantityLength<>(5.0, LengthUnit.FEET);
		assertEquals(a1.hashCode(), a2.hashCode());

		QuantityLength<LengthUnit> b1 = new QuantityLength<>(1.0, LengthUnit.YARDS);
		QuantityLength<LengthUnit> b2 = new QuantityLength<>(36.0, LengthUnit.INCHES);
		assertEquals(b1, b2);
		assertEquals(b1.hashCode(), b2.hashCode());

		QuantityLength<LengthUnit> c1 = new QuantityLength<>(2.54, LengthUnit.CENTIMETERS);
		QuantityLength<LengthUnit> c2 = new QuantityLength<>(1.0, LengthUnit.INCHES);
		assertEquals(c1, c2);
		assertEquals(c1.hashCode(), c2.hashCode());

		QuantityLength<LengthUnit> d1 = new QuantityLength<>(1.0000003, LengthUnit.FEET);
		QuantityLength<LengthUnit> d2 = new QuantityLength<>(1.0, LengthUnit.FEET);
		assertNotEquals(d1, d2);
		assertNotEquals(d1.hashCode(), d2.hashCode());
	}

	// Weight equality related
	@Test
	public void testEquality_KilogramToKilogram_SameValue() {
		assertEquals(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(1.0, WeightUnit.KILOGRAM));
	}

	@Test
	public void testEquality_KilogramToKilogram_DifferentValue() {
		assertNotEquals(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(2.0, WeightUnit.KILOGRAM));
	}

	@Test
	public void testEquality_GramToGram_SameValue() {
		assertEquals(new QuantityLength<>(500.0, WeightUnit.GRAM), new QuantityLength<>(500.0, WeightUnit.GRAM));
	}

	@Test
	public void testEquality_PoundToPound_SameValue() {
		assertEquals(new QuantityLength<>(2.0, WeightUnit.POUND), new QuantityLength<>(2.0, WeightUnit.POUND));
	}

	@Test
	public void testEquality_KilogramToGram_EquivalentValue() {
		assertEquals(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(1000.0, WeightUnit.GRAM));
	}

	@Test
	public void testEquality_GramToKilogram_EquivalentValue() {
		assertEquals(new QuantityLength<>(1000.0, WeightUnit.GRAM), new QuantityLength<>(1.0, WeightUnit.KILOGRAM));
	}

	@Test
	public void testEquality_KilogramToPound_EquivalentValue() {
		assertEquals(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(2.204624, WeightUnit.POUND));
	}

	@Test
	public void testEquality_GramToPound_EquivalentValue() {
		assertEquals(new QuantityLength<>(453.592370, WeightUnit.GRAM), new QuantityLength<>(1.0, WeightUnit.POUND));
	}

	@Test
	public void testEquality_Symmetry_Weight() {
		QuantityLength<WeightUnit> a = new QuantityLength<>(1.0, WeightUnit.KILOGRAM);
		QuantityLength<WeightUnit> b = new QuantityLength<>(1000.0, WeightUnit.GRAM);
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
	}

	@Test
	public void testEquality_Transitive_Weight() {
		QuantityLength<WeightUnit> a = new QuantityLength<>(1.0, WeightUnit.KILOGRAM);
		QuantityLength<WeightUnit> b = new QuantityLength<>(1000.0, WeightUnit.GRAM);
		QuantityLength<WeightUnit> c = new QuantityLength<>(2.204624, WeightUnit.POUND);
		assertTrue(a.equals(b));
		assertTrue(b.equals(c));
		assertTrue(a.equals(c));
	}

	@Test
	public void testEquality_WeightVsLength_Incompatible() {
		assertFalse(new QuantityLength<>(1.0, WeightUnit.KILOGRAM).equals(new QuantityLength<>(1.0, LengthUnit.FEET)));
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

	@Test
	public void testEquality_NullUnit_WeightThrows() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(1.0, (WeightUnit) null));
	}

	@Test
	public void testEquality_ZeroValue_Weight() {
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
}