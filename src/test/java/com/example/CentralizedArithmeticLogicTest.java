package com.example;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class CentralizedArithmeticLogicTest {

	private static final double EPSILON = 1e-6;

	@Test
	public void testRefactoring_Add_DelegatesViaHelper() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);
		QuantityLength<LengthUnit> sum = a.add(b);
		assertEquals(new QuantityLength<>(2.0, LengthUnit.FEET), sum);
	}

	@Test
	public void testRefactoring_Subtract_DelegatesViaHelper() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(10.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(6.0, LengthUnit.INCHES);
		QuantityLength<LengthUnit> diff = a.subtract(b);
		assertEquals(new QuantityLength<>(9.5, LengthUnit.FEET), diff);
	}

	@Test
	public void testRefactoring_Divide_DelegatesViaHelper() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(24.0, LengthUnit.INCHES);
		QuantityLength<LengthUnit> b = new QuantityLength<>(2.0, LengthUnit.FEET);
		double ratio = a.divide(b);
		assertEquals(1.0, ratio, EPSILON);
	}

	@Test
	public void testValidation_NullOperand_ConsistentAcrossOperations() {
		QuantityLength<LengthUnit> q = new QuantityLength<>(1.0, LengthUnit.FEET);

		IllegalArgumentException exAdd = assertThrows(IllegalArgumentException.class, () -> q.add(null));
		IllegalArgumentException exSub = assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
		IllegalArgumentException exDiv = assertThrows(IllegalArgumentException.class, () -> q.divide(null));

		assertEquals(exAdd.getMessage(), exSub.getMessage());
		assertEquals(exAdd.getMessage(), exDiv.getMessage());
	}

	@Test
	public void testValidation_CrossCategory_ConsistentAcrossOperations() {

	    QuantityLength<LengthUnit> length =
	            new QuantityLength<>(10.0, LengthUnit.FEET);

	    QuantityLength<WeightUnit> weight =
	            new QuantityLength<>(5.0, WeightUnit.KILOGRAM);

	    assertThrows(IllegalArgumentException.class,
	            () -> length.add((QuantityLength) weight));

	    assertThrows(IllegalArgumentException.class,
	            () -> length.subtract((QuantityLength) weight));

	    assertThrows(IllegalArgumentException.class,
	            () -> length.divide((QuantityLength) weight));
	}

	@Test
	public void testValidation_FiniteValue_ConsistentAcrossOperations() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.NaN, LengthUnit.FEET));
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength<>(Double.POSITIVE_INFINITY, LengthUnit.FEET));
	}

	@Test
	public void testValidation_NullTargetUnit_AddSubtractReject() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(5.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);

		assertThrows(IllegalArgumentException.class, () -> a.add(b, null));
		assertThrows(IllegalArgumentException.class, () -> a.subtract(b, null));
	}

	@Test
	public void testArithmeticOperation_Add_EnumComputation() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		assertNotNull(enumClass);
		Object add = Enum.valueOf((Class<Enum>) enumClass, "ADD");
		Method compute = enumClass.getMethod("compute", double.class, double.class);
		double out = (double) compute.invoke(add, 10.0, 5.0);
		assertEquals(15.0, out, EPSILON);
	}

	@Test
	public void testArithmeticOperation_Subtract_EnumComputation() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		Object sub = Enum.valueOf((Class<Enum>) enumClass, "SUBTRACT");
		Method compute = enumClass.getMethod("compute", double.class, double.class);
		double out = (double) compute.invoke(sub, 10.0, 5.0);
		assertEquals(5.0, out, EPSILON);
	}

	@Test
	public void testArithmeticOperation_Divide_EnumComputation() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		Object div = Enum.valueOf((Class<Enum>) enumClass, "DIVIDE");
		Method compute = enumClass.getMethod("compute", double.class, double.class);
		double out = (double) compute.invoke(div, 10.0, 5.0);
		assertEquals(2.0, out, EPSILON);
	}

	@Test
	public void testArithmeticOperation_DivideByZero_EnumThrows() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		Object div = Enum.valueOf((Class<Enum>) enumClass, "DIVIDE");
		Method compute = enumClass.getMethod("compute", double.class, double.class);

		InvocationTargetException thrown = assertThrows(InvocationTargetException.class,
				() -> compute.invoke(div, 10.0, 0.0));
		assertTrue(thrown.getCause() instanceof ArithmeticException);
	}

	@Test
	public void testPerformBaseArithmetic_ConversionAndOperation() throws Exception {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);

		Method perform = QuantityLength.class.getDeclaredMethod("performArithmetic", QuantityLength.class,
				findInnerEnum(QuantityLength.class, "ArithmeticOperation"));
		perform.setAccessible(true);

		Object addConst = Enum.valueOf((Class<Enum>) findInnerEnum(QuantityLength.class, "ArithmeticOperation"), "ADD");
		double baseResult = (double) perform.invoke(a, b, addConst);

		double baseA = a.getUnit().convertToBaseUnit(a.getValue());
		double baseB = b.getUnit().convertToBaseUnit(b.getValue());
		assertEquals(baseA + baseB, baseResult, EPSILON);
	}

	@Test
	public void testAdd_UC12_BehaviorPreserved() {
		assertEquals(new QuantityLength<>(2.0, LengthUnit.FEET),
				new QuantityLength<>(1.0, LengthUnit.FEET).add(new QuantityLength<>(12.0, LengthUnit.INCHES)));

		assertEquals(new QuantityLength<>(15000.0, WeightUnit.GRAM), new QuantityLength<>(10.0, WeightUnit.KILOGRAM)
				.add(new QuantityLength<>(5000.0, WeightUnit.GRAM), WeightUnit.GRAM));
	}

	@Test
	public void testSubtract_UC12_BehaviorPreserved() {
		assertEquals(new QuantityLength<>(9.5, LengthUnit.FEET),
				new QuantityLength<>(10.0, LengthUnit.FEET).subtract(new QuantityLength<>(6.0, LengthUnit.INCHES)));

		assertEquals(new QuantityLength<>(0.0, LengthUnit.FEET),
				new QuantityLength<>(10.0, LengthUnit.FEET).subtract(new QuantityLength<>(120.0, LengthUnit.INCHES)));
	}

	@Test
	public void testDivide_UC12_BehaviorPreserved() {
		assertEquals(5.0, new QuantityLength<>(10.0, LengthUnit.FEET).divide(new QuantityLength<>(2.0, LengthUnit.FEET)), EPSILON);
		assertEquals(1.0, new QuantityLength<>(24.0, LengthUnit.INCHES).divide(new QuantityLength<>(2.0, LengthUnit.FEET)),
				EPSILON);
	}

	@Test
	public void testRounding_AddSubtract_TwoDecimalPlaces() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(0.333333, LengthUnit.YARDS);
		QuantityLength<LengthUnit> res = a.subtract(b);
		double rounded = Math.round(res.getValue() * 1_000_000.0) / 1_000_000.0;
		assertEquals(rounded, res.getValue(), EPSILON);
	}

	@Test
	public void testRounding_Divide_NoRounding() {
		double ratio = new QuantityLength<>(10.0, LengthUnit.FEET).divide(new QuantityLength<>(3.0, LengthUnit.FEET));
		assertEquals(10.0 / 3.0, ratio, 1e-12);
	}

	@Test
	public void testImplicitTargetUnit_AddSubtract() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(2.0, LengthUnit.FEET), a.add(b));
		assertEquals(new QuantityLength<>(0.0, LengthUnit.FEET), a.subtract(b));
	}

	@Test
	public void testExplicitTargetUnit_AddSubtract_Overrides() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);
		assertEquals(new QuantityLength<>(24.0, LengthUnit.INCHES), a.add(b, LengthUnit.INCHES));
		assertEquals(new QuantityLength<>(0.0, LengthUnit.INCHES), a.subtract(b, LengthUnit.INCHES));
	}

	@Test
	public void testImmutability_AfterAdd_ViaCentralizedHelper() {
		QuantityLength<VolumeUnit> original = new QuantityLength<>(5.0, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> other = new QuantityLength<>(500.0, VolumeUnit.MILLILITRE);
		QuantityLength<VolumeUnit> sum = original.add(other);
		assertEquals(new QuantityLength<>(5.0, VolumeUnit.LITRE), original);
		assertNotSame(original, sum);
	}

	@Test
	public void testImmutability_AfterSubtract_ViaCentralizedHelper() {
		QuantityLength<VolumeUnit> original = new QuantityLength<>(5.0, VolumeUnit.LITRE);
		QuantityLength<VolumeUnit> diff = original.subtract(new QuantityLength<>(500.0, VolumeUnit.MILLILITRE));
		assertEquals(new QuantityLength<>(5.0, VolumeUnit.LITRE), original);
		assertEquals(new QuantityLength<>(4.5, VolumeUnit.LITRE), diff);
	}

	@Test
	public void testImmutability_AfterDivide_ViaCentralizedHelper() {
		QuantityLength<WeightUnit> original = new QuantityLength<>(10.0, WeightUnit.KILOGRAM);
		double ratio = original.divide(new QuantityLength<>(5.0, WeightUnit.KILOGRAM));
		assertEquals(new QuantityLength<>(10.0, WeightUnit.KILOGRAM), original);
		assertEquals(2.0, ratio, EPSILON);
	}

	@Test
	public void testAllOperations_AcrossAllCategories() {
		assertEquals(new QuantityLength<>(9.5, LengthUnit.FEET),
				new QuantityLength<>(10.0, LengthUnit.FEET).subtract(new QuantityLength<>(6.0, LengthUnit.INCHES)));

		assertEquals(new QuantityLength<>(5.0, WeightUnit.KILOGRAM),
				new QuantityLength<>(10.0, WeightUnit.KILOGRAM).subtract(new QuantityLength<>(5000.0, WeightUnit.GRAM)));

		assertEquals(new QuantityLength<>(4.5, VolumeUnit.LITRE),
				new QuantityLength<>(5.0, VolumeUnit.LITRE).subtract(new QuantityLength<>(500.0, VolumeUnit.MILLILITRE)));
	}

	@Test
	public void testCodeDuplication_ValidationLogic_Eliminated() throws Exception {
		Method validate = QuantityLength.class.getDeclaredMethod("validateArithmeticOperands", QuantityLength.class,
				IMeasurable.class, boolean.class);
		assertTrue(Modifier.isPrivate(validate.getModifiers()));
	}

	@Test
	public void testCodeDuplication_ConversionLogic_Eliminated() throws Exception {
		Method perform = QuantityLength.class.getDeclaredMethod("performArithmetic", QuantityLength.class,
				findInnerEnum(QuantityLength.class, "ArithmeticOperation"));
		assertTrue(Modifier.isPrivate(perform.getModifiers()));
	}

	@Test
	public void testEnumDispatch_AllOperations_CorrectlyDispatched() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		assertNotNull(Enum.valueOf((Class<Enum>) enumClass, "ADD"));
		assertNotNull(Enum.valueOf((Class<Enum>) enumClass, "SUBTRACT"));
		assertNotNull(Enum.valueOf((Class<Enum>) enumClass, "DIVIDE"));
	}

	@Test
	public void testFutureOperation_MultiplicationPattern() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		Method compute = enumClass.getMethod("compute", double.class, double.class);
		assertNotNull(compute);
	}

	@Test
	public void testErrorMessage_Consistency_Across_Operations() {
		QuantityLength<LengthUnit> q = new QuantityLength<>(1.0, LengthUnit.FEET);
		String msgAdd = assertThrows(IllegalArgumentException.class, () -> q.add(null)).getMessage();
		String msgSub = assertThrows(IllegalArgumentException.class, () -> q.subtract(null)).getMessage();
		String msgDiv = assertThrows(IllegalArgumentException.class, () -> q.divide(null)).getMessage();
		assertEquals(msgAdd, msgSub);
		assertEquals(msgAdd, msgDiv);
	}

	@Test
	public void testHelper_PrivateVisibility() throws Exception {
		Method perform = QuantityLength.class.getDeclaredMethod("performArithmetic", QuantityLength.class,
				findInnerEnum(QuantityLength.class, "ArithmeticOperation"));
		assertTrue(Modifier.isPrivate(perform.getModifiers()));
	}

	@Test
	public void testValidation_Helper_PrivateVisibility() throws Exception {
		Method validate = QuantityLength.class.getDeclaredMethod("validateArithmeticOperands", QuantityLength.class,
				IMeasurable.class, boolean.class);
		assertTrue(Modifier.isPrivate(validate.getModifiers()));
	}

	@Test
	public void testRounding_Helper_Accuracy() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.234567, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(0.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> res = a.add(b);
		double rounded = Math.round(res.getValue() * 1_000_000.0) / 1_000_000.0;
		assertEquals(rounded, res.getValue(), 1e-6);
	}

	@Test
	public void testArithmetic_Chain_Operations() {
		QuantityLength<LengthUnit> q1 = new QuantityLength<>(10.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> q2 = new QuantityLength<>(2.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> q3 = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> q4 = new QuantityLength<>(7.0, LengthUnit.FEET);
		double result = q1.add(q2).subtract(q3).divide(q4);
		assertEquals(11.0 / 7.0, result, EPSILON);
	}

	@Test
	public void testRefactoring_NoBehaviorChange_LargeDataset() {
		QuantityLength<LengthUnit> base = new QuantityLength<>(0.0, LengthUnit.INCHES);
		for (int i = 0; i < 1000; i++) {
			base = base.add(new QuantityLength<>(1.0, LengthUnit.INCHES));
		}
		assertEquals(new QuantityLength<>(1000.0, LengthUnit.INCHES), base);
	}

	@Test
	public void testRefactoring_Performance_ComparableToUC12() {
		QuantityLength<LengthUnit> q = new QuantityLength<>(0.0, LengthUnit.INCHES);
		for (int i = 0; i < 10000; i++) {
			q = q.add(new QuantityLength<>(1.0, LengthUnit.INCHES));
		}
		assertEquals(10000.0, q.getValue(), EPSILON);
	}

	@Test
	public void testEnumConstant_ADD_CorrectlyAdds() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		Object add = Enum.valueOf((Class<Enum>) enumClass, "ADD");
		Method compute = enumClass.getMethod("compute", double.class, double.class);
		assertEquals(10.0, (double) compute.invoke(add, 7.0, 3.0), EPSILON);
	}

	@Test
	public void testEnumConstant_SUBTRACT_CorrectlySubtracts() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		Object sub = Enum.valueOf((Class<Enum>) enumClass, "SUBTRACT");
		Method compute = enumClass.getMethod("compute", double.class, double.class);
		assertEquals(4.0, (double) compute.invoke(sub, 7.0, 3.0), EPSILON);
	}

	@Test
	public void testEnumConstant_DIVIDE_CorrectlyDivides() throws Exception {
		Class<?> enumClass = findInnerEnum(QuantityLength.class, "ArithmeticOperation");
		Object div = Enum.valueOf((Class<Enum>) enumClass, "DIVIDE");
		Method compute = enumClass.getMethod("compute", double.class, double.class);
		assertEquals(3.5, (double) compute.invoke(div, 7.0, 2.0), EPSILON);
	}

	@Test
	public void testHelper_BaseUnitConversion_Correct() throws Exception {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);
		Method perform = QuantityLength.class.getDeclaredMethod("performArithmetic", QuantityLength.class,
				findInnerEnum(QuantityLength.class, "ArithmeticOperation"));
		perform.setAccessible(true);
		Object addConst = Enum.valueOf((Class<Enum>) findInnerEnum(QuantityLength.class, "ArithmeticOperation"), "ADD");
		double baseResult = (double) perform.invoke(a, b, addConst);
		double baseA = a.getUnit().convertToBaseUnit(a.getValue());
		double baseB = b.getUnit().convertToBaseUnit(b.getValue());
		assertEquals(baseA + baseB, baseResult, EPSILON);
	}

	@Test
	public void testHelper_ResultConversion_Correct() {
		QuantityLength<LengthUnit> a = new QuantityLength<>(1.0, LengthUnit.FEET);
		QuantityLength<LengthUnit> b = new QuantityLength<>(12.0, LengthUnit.INCHES);

		QuantityLength<LengthUnit> sumFeet = a.add(b);
		QuantityLength<LengthUnit> sumInches = a.add(b, LengthUnit.INCHES);

		QuantityLength<LengthUnit> converted = sumFeet.convertTo(LengthUnit.INCHES);
		assertEquals(sumInches, converted);
	}

	@Test
	public void testRefactoring_Validation_UnifiedBehavior() {

	    QuantityLength<LengthUnit> q =
	            new QuantityLength<>(1.0, LengthUnit.FEET);

	    String msgAdd =
	            assertThrows(IllegalArgumentException.class,
	                    () -> q.add(null)).getMessage();

	    String msgSub =
	            assertThrows(IllegalArgumentException.class,
	                    () -> q.subtract(null)).getMessage();

	    String msgDiv =
	            assertThrows(IllegalArgumentException.class,
	                    () -> q.divide(null)).getMessage();

	    assertEquals(msgAdd, msgSub);
	    assertEquals(msgAdd, msgDiv);
	}

	// Helper method
	private static Class<?> findInnerEnum(Class<?> outer, String enumName) {
		for (Class<?> c : outer.getDeclaredClasses()) {
			if (c.isEnum() && c.getSimpleName().equals(enumName)) {
				return c;
			}
		}
		return null;
	}

}