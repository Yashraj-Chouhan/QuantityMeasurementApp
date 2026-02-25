package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class VolumeQuantityTest {

	private static final double EPSILON = 1e-6;

	@Test
	public void testEquality_LitreToLitre_SameValue() {
		assertEquals(new QuantityLength<>(1.0, VolumeUnit.LITRE), new QuantityLength<>(1.0, VolumeUnit.LITRE));
	}

	@Test
	public void testEquality_LitreToLitre_DifferentValue() {
		assertNotEquals(new QuantityLength<>(1.0, VolumeUnit.LITRE), new QuantityLength<>(2.0, VolumeUnit.LITRE));
	}

	@Test
	public void testEquality_MillilitreToMillilitre_SameValue() {
		assertEquals(new QuantityLength<>(500.0, VolumeUnit.MILLILITRE), new QuantityLength<>(500.0, VolumeUnit.MILLILITRE));
	}

	@Test
	public void testEquality_GallonToGallon_SameValue() {
		assertEquals(new QuantityLength<>(2.0, VolumeUnit.GALLON), new QuantityLength<>(2.0, VolumeUnit.GALLON));
	}

	@Test
	public void testEquality_LitreToMillilitre_EquivalentValue() {
		assertEquals(new QuantityLength<>(1.0, VolumeUnit.LITRE), new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE));
	}

	@Test
	public void testEquality_MillilitreToLitre_Symmetric() {
		assertTrue(new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE).equals(new QuantityLength<>(1.0, VolumeUnit.LITRE)));
	}

	@Test
	public void testEquality_LitreToGallon_WithinEpsilon() {
		QuantityLength<VolumeUnit> litres = new QuantityLength<>(3.785412, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> gallon = new QuantityLength<>(1.0, VolumeUnit.GALLON);
		assertTrue(litres.equals(gallon));
		assertTrue(gallon.equals(litres));
	}

	@Test
	public void testEquality_TransitiveProperty() {
		QuantityLength<VolumeUnit> a = new QuantityLength<>(3.785412, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> b = new QuantityLength<>(1.0, VolumeUnit.GALLON);
		QuantityLength<VolumeUnit> c = new QuantityLength<>(3785.412, VolumeUnit.MILLILITRE);
		assertTrue(a.equals(b));
		assertTrue(b.equals(c));
		assertTrue(a.equals(c));
	}

	@Test
	public void testConversion_LitreToMillilitre() {
		QuantityLength<VolumeUnit> converted = new QuantityLength<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
		assertEquals(1000.0, converted.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, converted.getUnit());
	}

	@Test
	public void testConversion_MillilitreToLitre() {
		QuantityLength<VolumeUnit> converted = new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
		assertEquals(1.0, converted.getValue(), EPSILON);
		assertEquals(VolumeUnit.LITRE, converted.getUnit());
	}

	@Test
	public void testConversion_GallonToLitre() {
		QuantityLength<VolumeUnit> converted = new QuantityLength<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);
		assertEquals(3.785412, converted.getValue(), EPSILON);
	}

	@Test
	public void testConversion_LitreToGallon() {
		QuantityLength<VolumeUnit> converted = new QuantityLength<>(3.785412, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);
		assertEquals(1.0, converted.getValue(), EPSILON);
	}

	@Test
	public void testConversion_RoundTrip_PreservesValue() {
		QuantityLength<VolumeUnit> original = new QuantityLength<>(1.5, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> roundTrip = original.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
		assertEquals(original, roundTrip);
	}

	@Test
	public void testConversion_SameUnit_NoChange() {
		QuantityLength<VolumeUnit> converted = new QuantityLength<>(5.0, VolumeUnit.LITRE).convertTo(VolumeUnit.LITRE);
		assertEquals(new QuantityLength<>(5.0, VolumeUnit.LITRE), converted);
	}

	@Test
	public void testConversion_ZeroAndNegativeValues() {
		assertEquals(
			new QuantityLength<>(0.0, VolumeUnit.MILLILITRE),
			new QuantityLength<>(0.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE)
		);
		assertEquals(
			new QuantityLength<>(-1000.0, VolumeUnit.MILLILITRE),
			new QuantityLength<>(-1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE)
		);
	}

	@Test
	public void testAddition_SameUnit_LitrePlusLitre() {
		QuantityLength<VolumeUnit> sum = new QuantityLength<>(1.0, VolumeUnit.LITRE).add(new QuantityLength<>(2.0, VolumeUnit.LITRE));
		assertEquals(new QuantityLength<>(3.0, VolumeUnit.LITRE), sum);
	}

	@Test
	public void testAddition_SameUnit_MillilitrePlusMillilitre() {
		QuantityLength<VolumeUnit> sum = new QuantityLength<>(500.0, VolumeUnit.MILLILITRE)
			.add(new QuantityLength<>(500.0, VolumeUnit.MILLILITRE));
		assertEquals(new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE), sum);
	}

	@Test
	public void testAddition_CrossUnit_LitrePlusMillilitre_Implicit() {
		QuantityLength<VolumeUnit> sum = new QuantityLength<>(1.0, VolumeUnit.LITRE)
			.add(new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE));
		assertEquals(new QuantityLength<>(2.0, VolumeUnit.LITRE), sum);
	}

	@Test
	public void testAddition_CrossUnit_MillilitrePlusLitre_Implicit() {
		QuantityLength<VolumeUnit> sum = new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE)
			.add(new QuantityLength<>(1.0, VolumeUnit.LITRE));
		assertEquals(new QuantityLength<>(2000.0, VolumeUnit.MILLILITRE), sum);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Litre() {
		QuantityLength<VolumeUnit> sum = new QuantityLength<>(1.0, VolumeUnit.LITRE)
			.add(new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);
		assertEquals(new QuantityLength<>(2.0, VolumeUnit.LITRE), sum);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Millilitre() {
		QuantityLength<VolumeUnit> sum = new QuantityLength<>(1.0, VolumeUnit.LITRE)
			.add(new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);
		assertEquals(new QuantityLength<>(2000.0, VolumeUnit.MILLILITRE), sum);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Gallon() {
		QuantityLength<VolumeUnit> sum = new QuantityLength<>(3.785412, VolumeUnit.LITRE)
			.add(new QuantityLength<>(3.785412, VolumeUnit.LITRE), VolumeUnit.GALLON);
		assertEquals(new QuantityLength<>(2.0, VolumeUnit.GALLON), sum);
	}

	@Test
	public void testAddition_Commutativity_WithTargetUnit() {
		QuantityLength<VolumeUnit> a = new QuantityLength<>(1.0, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> b = new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE);
		QuantityLength<VolumeUnit> r1 = a.add(b, VolumeUnit.GALLON);
		QuantityLength<VolumeUnit> r2 = b.add(a, VolumeUnit.GALLON);
		assertEquals(r1, r2);
	}

	@Test
	public void testAddition_WithZeroAndNegative() {
		QuantityLength<VolumeUnit> base = new QuantityLength<>(5.0, VolumeUnit.LITRE);
		assertEquals(base, base.add(new QuantityLength<>(0.0, VolumeUnit.MILLILITRE)));
		assertEquals(new QuantityLength<>(3.0, VolumeUnit.LITRE), base.add(new QuantityLength<>(-2000.0, VolumeUnit.MILLILITRE)));
	}

	@Test
	public void testAddition_LargeAndSmallValues() {
		QuantityLength<VolumeUnit> large = new QuantityLength<>(1e6, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> sum = large.add(new QuantityLength<>(1e6, VolumeUnit.LITRE));
		assertEquals(new QuantityLength<>(2e6, VolumeUnit.LITRE), sum);

		QuantityLength<VolumeUnit> small = new QuantityLength<>(0.001, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> smallSum = small.add(new QuantityLength<>(0.002, VolumeUnit.LITRE));
		assertEquals(new QuantityLength<>(0.003, VolumeUnit.LITRE), smallSum);
	}

	@Test
	public void testCrossCategoryPrevention_VolumeVsLength() {
		assertFalse(new QuantityLength<>(1.0, VolumeUnit.LITRE).equals(new QuantityLength<>(1.0, LengthUnit.FEET)));
	}

	@Test
	public void testCrossCategoryPrevention_VolumeVsWeight() {
		assertFalse(new QuantityLength<>(1.0, VolumeUnit.LITRE).equals(new QuantityLength<>(1.0, WeightUnit.KILOGRAM)));
	}

	@Test
	public void testNullUnitConstructor_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(1.0, (VolumeUnit) null));
	}

	@Test
	public void testEquals_NullComparison_ReturnsFalse() {
		assertFalse(new QuantityLength<>(1.0, VolumeUnit.LITRE).equals(null));
	}

	@Test
	public void testSameReferenceEquality() {
		QuantityLength<VolumeUnit> q = new QuantityLength<>(2.0, VolumeUnit.LITRE);
		assertEquals(q, q);
	}

	@Test
	public void testVolumeUnitEnum_ConstantsAndConversionFactors() {
		assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
		assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
		assertEquals(3.785412, VolumeUnit.GALLON.getConversionFactor(), EPSILON);
	}

	@Test
	public void testConvertToBaseUnitAndFromBaseUnit() {
		assertEquals(5.0, VolumeUnit.LITRE.convertToBaseUnit(5.0), EPSILON);
		assertEquals(1.0, VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0), EPSILON);
		assertEquals(3.785412, VolumeUnit.GALLON.convertToBaseUnit(1.0), EPSILON);

		assertEquals(2.0, VolumeUnit.LITRE.convertFromBaseUnit(2.0), EPSILON);
		assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0), EPSILON);
		assertEquals(1.0, VolumeUnit.GALLON.convertFromBaseUnit(3.785412), EPSILON);
	}

	@Test
	public void testHashCode_EqualObjectsHaveSameHashCode() {
		QuantityLength<VolumeUnit> a = new QuantityLength<>(1.0, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> b = new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());

		QuantityLength<VolumeUnit> c = new QuantityLength<>(3.785412, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> d = new QuantityLength<>(1.0, VolumeUnit.GALLON);
		assertEquals(c, d);
		assertEquals(c.hashCode(), d.hashCode());
	}

	@Test
	public void testTypeSafetyInCollections_HashSetBehavior() {
		Set<QuantityLength<VolumeUnit>> set = new HashSet<>();
		set.add(new QuantityLength<>(1.0, VolumeUnit.LITRE));
		set.add(new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE));
		assertEquals(1, set.size());
	}

	@Test
	public void testPrecision_SmallDifferencesWithinEpsilon() {
		QuantityLength<VolumeUnit> a = new QuantityLength<>(1.00000005, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> b = new QuantityLength<>(1.0, VolumeUnit.LITRE);
		assertEquals(a, b);
	}

	@Test
	public void testPrecision_SmallDifferencesOutsideEpsilon() {
		QuantityLength<VolumeUnit> a = new QuantityLength<>(1.00001, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> b = new QuantityLength<>(1.0, VolumeUnit.LITRE);
		assertNotEquals(a, b);
	}

	@Test
	public void testConstructor_InvalidValue_NaNOrInfinite_Throws() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.NaN, VolumeUnit.LITRE));
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.POSITIVE_INFINITY, VolumeUnit.MILLILITRE));
	}

	@Test
	public void testIMeasurableInterface_VolumeUnitImplementation() {
		IMeasurable unit = VolumeUnit.LITRE;
		assertEquals("LITRE", unit.getUnitName());
		assertEquals(1.0, unit.getConversionFactor(), EPSILON);
		assertEquals(1.0, unit.convertToBaseUnit(1.0), EPSILON);
		assertEquals(1.0, unit.convertFromBaseUnit(1.0), EPSILON);
	}

	@Test
	public void testGenericQuantityLength_VolumeOperations_EqualityAndConversion() {
		QuantityLength<VolumeUnit> v1 = new QuantityLength<>(1.0, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> v2 = new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE);
		assertTrue(v1.equals(v2));
		QuantityLength<VolumeUnit> converted = v1.convertTo(VolumeUnit.MILLILITRE);
		assertEquals(new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE), converted);
	}

	@Test
	public void testImmutability_GenericQuantityLength() {
		QuantityLength<VolumeUnit> q = new QuantityLength<>(1.0, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> added = q.add(new QuantityLength<>(1.0, VolumeUnit.LITRE));
		assertEquals(new QuantityLength<>(1.0, VolumeUnit.LITRE), q);
		assertEquals(new QuantityLength<>(2.0, VolumeUnit.LITRE), added);
	}

	@Test
	public void testScalability_NewCategoryIntegration() {
		QuantityLength<VolumeUnit> q = new QuantityLength<>(2.0, VolumeUnit.LITRE);
		assertEquals(new QuantityLength<>(2000.0, VolumeUnit.MILLILITRE), q.convertTo(VolumeUnit.MILLILITRE));
	}

	@Test
	public void testDemonstrationMethods_HandleVolume() {
		boolean eq = QuantityMeasurementApp.demonstrateEquality(
			new QuantityLength<>(1.0, VolumeUnit.LITRE),
			new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE)
		);
		assertTrue(eq);

		QuantityLength<VolumeUnit> converted = QuantityMeasurementApp.demonstrateConversion(
			new QuantityLength<>(1.0, VolumeUnit.GALLON),
			VolumeUnit.LITRE
		);
		assertEquals(new QuantityLength<>(3.785412, VolumeUnit.LITRE), converted);

		QuantityLength<VolumeUnit> sum = QuantityMeasurementApp.demonstrateAddition(
			new QuantityLength<>(1.0, VolumeUnit.LITRE),
			new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE)
		);
		assertEquals(new QuantityLength<>(2.0, VolumeUnit.LITRE), sum);
	}
}