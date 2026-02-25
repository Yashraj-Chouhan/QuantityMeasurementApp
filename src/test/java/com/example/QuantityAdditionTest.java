package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityAdditionTest {

	@Test
	public void testAddition_SameUnit_FeetPlusFeet() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(2.0, LengthUnit.FEET));
		assertEquals(new QuantityLength<>(3.0, LengthUnit.FEET), result);
	}

	@Test
	public void testAddition_SameUnit_InchPlusInch() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(6.0, LengthUnit.INCHES)
				.add(new QuantityLength<>(6.0, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(12.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testAddition_CrossUnit_FeetPlusInches() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(2.0, LengthUnit.FEET), result);
	}

	@Test
	public void testAddition_CrossUnit_InchPlusFeet() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(12.0, LengthUnit.INCHES).add(new QuantityLength<>(1.0, LengthUnit.FEET));
		assertEquals(new QuantityLength<>(24.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testAddition_CrossUnit_YardPlusFeet() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.YARDS).add(new QuantityLength<>(3.0, LengthUnit.FEET));
		assertEquals(new QuantityLength<>(2.0, LengthUnit.YARDS), result);
	}

	@Test
	public void testAddition_CrossUnit_CentimeterPlusInch() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(2.54, LengthUnit.CENTIMETERS)
				.add(new QuantityLength<>(1.0, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(5.08, LengthUnit.CENTIMETERS), result);
	}

	@Test
	public void testAddition_Commutativity() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);
		assertEquals(a.add(b), b.add(a).convertTo(LengthUnit.FEET));
	}

	@Test
	public void testAddition_WithZero() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(5.0, LengthUnit.FEET).add(new QuantityLength<>(0.0, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(5.0, LengthUnit.FEET), result);
	}

	@Test
	public void testAddition_NegativeValues() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(5.0, LengthUnit.FEET).add(new QuantityLength<>(-2.0, LengthUnit.FEET));
		assertEquals(new QuantityLength<>(3.0, LengthUnit.FEET), result);
	}

	@Test
	public void testAddition_NullSecondOperandThrows() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(1.0, LengthUnit.FEET).add(null));
	}

	@Test
	public void testAddition_LargeValues() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1e6, LengthUnit.FEET).add(new QuantityLength<>(1e6, LengthUnit.FEET));
		assertEquals(new QuantityLength<>(2e6, LengthUnit.FEET), result);
	}

	@Test
	public void testAddition_SmallValues() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(0.001, LengthUnit.FEET)
				.add(new QuantityLength<>(0.002, LengthUnit.FEET));
		assertEquals(new QuantityLength<>(0.003, LengthUnit.FEET), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Feet() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES),
				LengthUnit.FEET);
		assertEquals(new QuantityLength<>(2.0, LengthUnit.FEET), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Inches() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES),
				LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(24.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Yards() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES),
				LengthUnit.YARDS);
		assertEquals(new QuantityLength<>(0.666667, LengthUnit.YARDS), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Centimeters() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1.0, LengthUnit.INCHES).add(new QuantityLength<>(1.0, LengthUnit.INCHES),
				LengthUnit.CENTIMETERS);
		assertEquals(new QuantityLength<>(5.079998, LengthUnit.CENTIMETERS), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(2.0, LengthUnit.YARDS).add(new QuantityLength<>(3.0, LengthUnit.FEET),
				LengthUnit.YARDS);
		assertEquals(new QuantityLength<>(3.0, LengthUnit.YARDS), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(2.0, LengthUnit.YARDS).add(new QuantityLength<>(3.0, LengthUnit.FEET),
				LengthUnit.FEET);
		assertEquals(new QuantityLength<>(9.0, LengthUnit.FEET), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Commutativity() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);
		QuantityLength<LengthUnit> result1 = a.add(b, LengthUnit.YARDS);
		QuantityLength<LengthUnit> result2 = b.add(a, LengthUnit.YARDS);
		assertEquals(result1, result2);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_WithZero() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(5.0, LengthUnit.FEET).add(new QuantityLength<>(0.0, LengthUnit.INCHES),
				LengthUnit.YARDS);
		assertEquals(new QuantityLength<>(1.666667, LengthUnit.YARDS), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_NegativeValues() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(5.0, LengthUnit.FEET).add(new QuantityLength<>(-2.0, LengthUnit.FEET),
				LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(36.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_NullTargetUnitThrows() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES), null));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(1000.0, LengthUnit.FEET)
				.add(new QuantityLength<>(500.0, LengthUnit.FEET), LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(18000.0, LengthUnit.INCHES), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(12.0, LengthUnit.INCHES)
				.add(new QuantityLength<>(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
		assertEquals(new QuantityLength<>(0.666667, LengthUnit.YARDS), result);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
		QuantityLength<LengthUnit> result1 = new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(1.0, LengthUnit.YARDS),
				LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(48.0, LengthUnit.INCHES), result1);

		QuantityLength<LengthUnit> result2 = new QuantityLength<>(2.54, LengthUnit.CENTIMETERS)
				.add(new QuantityLength<>(1.0, LengthUnit.INCHES), LengthUnit.FEET);
		assertEquals(new QuantityLength<>(0.166667, LengthUnit.FEET), result2);
	}

	@Test
	public void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
		QuantityLength<LengthUnit> result = new QuantityLength<>(0.1, LengthUnit.FEET).add(new QuantityLength<>(0.2, LengthUnit.FEET),
				LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(3.6, LengthUnit.INCHES), result);
	}
}