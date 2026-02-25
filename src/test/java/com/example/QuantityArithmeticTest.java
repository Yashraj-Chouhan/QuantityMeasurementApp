package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class QuantityArithmeticTest {

    private static final double EPSILON = 1e-6;

    @Test
    public void testSubtraction_SameUnit_FeetMinusFeet() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(10.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(5.0, LengthUnit.FEET));
        assertEquals(new QuantityLength<>(5.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_SameUnit_LitreMinusLitre() {
        QuantityLength<VolumeUnit> result = new QuantityLength<>(10.0, VolumeUnit.LITRE)
            .subtract(new QuantityLength<>(3.0, VolumeUnit.LITRE));
        assertEquals(new QuantityLength<>(7.0, VolumeUnit.LITRE), result);
    }

    @Test
    public void testSubtraction_CrossUnit_FeetMinusInches() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(10.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(6.0, LengthUnit.INCHES));
        assertEquals(new QuantityLength<>(9.5, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_CrossUnit_InchesMinusFeet() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(120.0, LengthUnit.INCHES)
            .subtract(new QuantityLength<>(5.0, LengthUnit.FEET));
        assertEquals(new QuantityLength<>(60.0, LengthUnit.INCHES), result);
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Feet() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(10.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(6.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertEquals(new QuantityLength<>(9.5, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Inches() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(10.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
        assertEquals(new QuantityLength<>(114.0, LengthUnit.INCHES), result);
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Millilitre() {
        QuantityLength<VolumeUnit> result = new QuantityLength<>(5.0, VolumeUnit.LITRE)
            .subtract(new QuantityLength<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertEquals(new QuantityLength<>(3000.0, VolumeUnit.MILLILITRE), result);
    }

    @Test
    public void testSubtraction_ResultingInNegative() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(5.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(10.0, LengthUnit.FEET));
        assertEquals(new QuantityLength<>(-5.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_ResultingInZero() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(10.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(120.0, LengthUnit.INCHES));
        assertEquals(new QuantityLength<>(0.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_WithZeroOperand() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(5.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(0.0, LengthUnit.INCHES));
        assertEquals(new QuantityLength<>(5.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_WithNegativeOperand() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(5.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(-2.0, LengthUnit.FEET));
        assertEquals(new QuantityLength<>(7.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_NonCommutative() {
        QuantityLength<LengthUnit> a = new QuantityLength<>(10.0, LengthUnit.FEET);
        QuantityLength<LengthUnit> b = new QuantityLength<>(5.0, LengthUnit.FEET);
        assertEquals(new QuantityLength<>(5.0, LengthUnit.FEET), a.subtract(b));
        assertEquals(new QuantityLength<>(-5.0, LengthUnit.FEET), b.subtract(a));
    }

    @Test
    public void testSubtraction_ChainedOperations() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(10.0, LengthUnit.FEET)
            .subtract(new QuantityLength<>(2.0, LengthUnit.FEET))
            .subtract(new QuantityLength<>(1.0, LengthUnit.FEET));
        assertEquals(new QuantityLength<>(7.0, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_WithLargeValues() {
        QuantityLength<WeightUnit> result = new QuantityLength<>(1e6, WeightUnit.KILOGRAM)
            .subtract(new QuantityLength<>(5e5, WeightUnit.KILOGRAM));
        assertEquals(new QuantityLength<>(5e5, WeightUnit.KILOGRAM), result);
    }

    @Test
    public void testSubtraction_WithSmallValues() {
        QuantityLength<LengthUnit> result = new QuantityLength<>(0.001, LengthUnit.FEET)
            .subtract(new QuantityLength<>(0.0005, LengthUnit.FEET));
        assertEquals(new QuantityLength<>(0.0005, LengthUnit.FEET), result);
    }

    @Test
    public void testSubtraction_PrecisionAndRounding() {
        QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
        QuantityLength<LengthUnit> b = new QuantityLength<>(0.333333, LengthUnit.YARDS);
        QuantityLength<LengthUnit> result = a.subtract(b);
        assertEquals(Math.round(result.getValue() * 1e6) / 1e6, result.getValue(), EPSILON);
    }

    @Test
    public void testSubtraction_NullOperand_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
            new QuantityLength<>(10.0, LengthUnit.FEET).subtract(null));
    }

    @Test
    public void testSubtraction_NullTargetUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
            new QuantityLength<>(10.0, LengthUnit.FEET).subtract(new QuantityLength<>(5.0, LengthUnit.FEET), null));
    }

    @Test
    public void testDivision_SameUnit_FeetDividedByFeet() {
        double ratio = new QuantityLength<>(10.0, LengthUnit.FEET)
            .divide(new QuantityLength<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, ratio, EPSILON);
    }

    @Test
    public void testDivision_SameUnit_LitreDividedByLitre() {
        double ratio = new QuantityLength<>(10.0, VolumeUnit.LITRE)
            .divide(new QuantityLength<>(5.0, VolumeUnit.LITRE));
        assertEquals(2.0, ratio, EPSILON);
    }

    @Test
    public void testDivision_CrossUnit_FeetDividedByInches() {
        double ratio = new QuantityLength<>(24.0, LengthUnit.INCHES)
            .divide(new QuantityLength<>(2.0, LengthUnit.FEET));
        assertEquals(1.0, ratio, EPSILON);
    }

    @Test
    public void testDivision_CrossUnit_KilogramDividedByGram() {
        double ratio = new QuantityLength<>(2.0, WeightUnit.KILOGRAM)
            .divide(new QuantityLength<>(2000.0, WeightUnit.GRAM));
        assertEquals(1.0, ratio, EPSILON);
    }

    @Test
    public void testDivision_RatioGreaterThanOne() {
        double ratio = new QuantityLength<>(10.0, LengthUnit.FEET)
            .divide(new QuantityLength<>(2.0, LengthUnit.FEET));
        assertTrue(ratio > 1.0);
    }

    @Test
    public void testDivision_RatioLessThanOne() {
        double ratio = new QuantityLength<>(5.0, LengthUnit.FEET)
            .divide(new QuantityLength<>(10.0, LengthUnit.FEET));
        assertEquals(0.5, ratio, EPSILON);
    }

    @Test
    public void testDivision_RatioEqualToOne() {
        double ratio = new QuantityLength<>(10.0, LengthUnit.FEET)
            .divide(new QuantityLength<>(10.0, LengthUnit.FEET));
        assertEquals(1.0, ratio, EPSILON);
    }

    @Test
    public void testDivision_ByZero_Throws() {
        assertThrows(ArithmeticException.class, () ->
            new QuantityLength<>(10.0, LengthUnit.FEET).divide(new QuantityLength<>(0.0, LengthUnit.FEET)));
    }

    @Test
    public void testDivision_NullOperand_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
            new QuantityLength<>(10.0, LengthUnit.FEET).divide(null));
    }

    @Test
    public void testDivision_WithLargeRatio() {
        double ratio = new QuantityLength<>(1e6, WeightUnit.KILOGRAM)
            .divide(new QuantityLength<>(1.0, WeightUnit.KILOGRAM));
        assertEquals(1e6, ratio, EPSILON);
    }

    @Test
    public void testDivision_WithSmallRatio() {
        double ratio = new QuantityLength<>(1.0, WeightUnit.KILOGRAM)
            .divide(new QuantityLength<>(1e6, WeightUnit.KILOGRAM));
        assertEquals(1e-6, ratio, 1e-12);
    }

    @Test
    public void testImmutability_AfterSubtraction() {
        QuantityLength<VolumeUnit> original = new QuantityLength<>(5.0, VolumeUnit.LITRE);
        QuantityLength<VolumeUnit> diff = original.subtract(new QuantityLength<>(500.0, VolumeUnit.MILLILITRE));
        assertEquals(new QuantityLength<>(5.0, VolumeUnit.LITRE), original);
        assertEquals(new QuantityLength<>(4.5, VolumeUnit.LITRE), diff);
    }

    @Test
    public void testImmutability_AfterDivision() {
        QuantityLength<WeightUnit> original = new QuantityLength<>(10.0, WeightUnit.KILOGRAM);
        double ratio = original.divide(new QuantityLength<>(5.0, WeightUnit.KILOGRAM));
        assertEquals(new QuantityLength<>(10.0, WeightUnit.KILOGRAM), original);
        assertEquals(2.0, ratio, EPSILON);
    }

    @Test
    public void testSubtractionAddition_Inverse() {
        QuantityLength<LengthUnit> a = new QuantityLength<>(5.0, LengthUnit.FEET);
        QuantityLength<LengthUnit> b = new QuantityLength<>(2.0, LengthUnit.FEET);
        QuantityLength<LengthUnit> result = a.add(b).subtract(b);
        assertEquals(a, result);
    }

    @Test
    public void testDivision_Associativity_NonAssociative() {
        QuantityLength<LengthUnit> A = new QuantityLength<>(8.0, LengthUnit.FEET);
        QuantityLength<LengthUnit> B = new QuantityLength<>(2.0, LengthUnit.FEET);
        QuantityLength<LengthUnit> C = new QuantityLength<>(2.0, LengthUnit.FEET);

        double left = (A.divide(B)) / (C.divide(new QuantityLength<>(1.0, LengthUnit.FEET)));

        double bDivC = B.divide(C);
        QuantityLength<LengthUnit> scalarAsQuantityLength = new QuantityLength<>(bDivC, LengthUnit.FEET);
        double right = A.divide(scalarAsQuantityLength);

        assertNotEquals(left, right, EPSILON);
    }

    @Test
    public void testTypeSafetyInCollections_HashSetBehavior() {
        Set<QuantityLength<VolumeUnit>> set = new HashSet<>();
        set.add(new QuantityLength<>(1.0, VolumeUnit.LITRE));
        set.add(new QuantityLength<>(1000.0, VolumeUnit.MILLILITRE));
        assertEquals(1, set.size());
    }

    @Test
    public void testSubtraction_AllMeasurementCategories() {
        assertEquals(new QuantityLength<>(9.5, LengthUnit.FEET),
            new QuantityLength<>(10.0, LengthUnit.FEET).subtract(new QuantityLength<>(6.0, LengthUnit.INCHES)));
        assertEquals(new QuantityLength<>(5.0, WeightUnit.KILOGRAM),
            new QuantityLength<>(10.0, WeightUnit.KILOGRAM).subtract(new QuantityLength<>(5000.0, WeightUnit.GRAM)));
        assertEquals(new QuantityLength<>(4.5, VolumeUnit.LITRE),
            new QuantityLength<>(5.0, VolumeUnit.LITRE).subtract(new QuantityLength<>(500.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testDivision_AllMeasurementCategories() {
        assertEquals(5.0, new QuantityLength<>(10.0, LengthUnit.FEET).divide(new QuantityLength<>(2.0, LengthUnit.FEET)), EPSILON);
        assertEquals(2.0, new QuantityLength<>(10.0, WeightUnit.KILOGRAM).divide(new QuantityLength<>(5.0, WeightUnit.KILOGRAM)), EPSILON);
        assertEquals(0.5, new QuantityLength<>(5.0, VolumeUnit.LITRE).divide(new QuantityLength<>(10.0, VolumeUnit.LITRE)), EPSILON);
    }
}