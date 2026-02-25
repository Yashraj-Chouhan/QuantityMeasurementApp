package com.example;

import java.util.Scanner;

import com.example.LengthUnit;

public class QuantityMeasurementApp {
	
	public static <U extends IMeasurable> boolean demonstrateEquality(QuantityLength<U> q1, QuantityLength<U> q2) {
		boolean result = q1.equals(q2);
		System.out.println(q1 + " == " + q2 + " ? " + result);
		return result;
	}

	public static <U extends IMeasurable> QuantityLength<U> demonstrateConversion(QuantityLength<U> source, U targetUnit) {
		QuantityLength<U> converted = source.convertTo(targetUnit);
		System.out.println(source + " -> " + converted);
		return converted;
	}

	public static <U extends IMeasurable> QuantityLength<U> demonstrateAddition(QuantityLength<U> q1, QuantityLength<U> q2) {
		QuantityLength<U> sum = q1.add(q2);
		System.out.println(q1 + " + " + q2 + " = " + sum);
		return sum;
	}

	public static <U extends IMeasurable> QuantityLength<U> demonstrateAddition(QuantityLength<U> q1, QuantityLength<U> q2,
			U targetUnit) {
		QuantityLength<U> sum = q1.add(q2, targetUnit);
		System.out.println(q1 + " + " + q2 + " in " + targetUnit.getUnitName() + " = " + sum);
		return sum;
	}
	
    public static void main(String[] args) {
    	demonstrateEquality(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(1000.0, WeightUnit.GRAM));
		demonstrateEquality(new QuantityLength<>(2.204624, WeightUnit.POUND), new QuantityLength<>(1.0, WeightUnit.KILOGRAM));
		demonstrateEquality(new QuantityLength<>(453.592, WeightUnit.GRAM), new QuantityLength<>(1.0, WeightUnit.POUND));
		demonstrateEquality(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(1.0, WeightUnit.KILOGRAM));
		demonstrateEquality(new QuantityLength<>(2.0, WeightUnit.POUND), new QuantityLength<>(2.0, WeightUnit.POUND));
		demonstrateEquality(new QuantityLength<>(500.0, WeightUnit.GRAM), new QuantityLength<>(0.5, WeightUnit.KILOGRAM));

		demonstrateConversion(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
		demonstrateConversion(new QuantityLength<>(2.0, WeightUnit.POUND), WeightUnit.KILOGRAM);
		demonstrateConversion(new QuantityLength<>(500.0, WeightUnit.GRAM), WeightUnit.POUND);
		demonstrateConversion(new QuantityLength<>(0.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
		demonstrateConversion(new QuantityLength<>(-1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);

		demonstrateAddition(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(2.0, WeightUnit.KILOGRAM));
		demonstrateAddition(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(1000.0, WeightUnit.GRAM));
		demonstrateAddition(new QuantityLength<>(500.0, WeightUnit.GRAM), new QuantityLength<>(0.5, WeightUnit.KILOGRAM));
		demonstrateAddition(new QuantityLength<>(1.0, WeightUnit.KILOGRAM), new QuantityLength<>(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);
		demonstrateAddition(new QuantityLength<>(1.0, WeightUnit.POUND), new QuantityLength<>(453.592, WeightUnit.GRAM), WeightUnit.POUND);
		demonstrateAddition(new QuantityLength<>(2.0, WeightUnit.KILOGRAM), new QuantityLength<>(4.0, WeightUnit.POUND), WeightUnit.KILOGRAM);

		System.out.println("Weight vs Length equality: " + new QuantityLength<>(1.0, WeightUnit.KILOGRAM).equals(new QuantityLength<>(1.0, LengthUnit.FEET)));
		demonstrateEquality(new QuantityLength<>(1.0, LengthUnit.FEET), new QuantityLength<>(1.0, WeightUnit.KILOGRAM));

		demonstrateEquality(new QuantityLength<>(1.0, LengthUnit.FEET), new QuantityLength<>(12.0, LengthUnit.INCHES));
		demonstrateEquality(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(36.0, LengthUnit.INCHES));
		demonstrateEquality(new QuantityLength<>(100.0, LengthUnit.CENTIMETERS), new QuantityLength<>(39.370078, LengthUnit.INCHES));
		demonstrateEquality(new QuantityLength<>(3.0, LengthUnit.FEET), new QuantityLength<>(1.0, LengthUnit.YARDS));
		demonstrateEquality(new QuantityLength<>(30.48, LengthUnit.CENTIMETERS), new QuantityLength<>(1.0, LengthUnit.FEET));

		demonstrateConversion(new QuantityLength<>(1.0, LengthUnit.FEET), LengthUnit.INCHES);
		demonstrateConversion(new QuantityLength<>(3.0, LengthUnit.YARDS), LengthUnit.FEET);
		demonstrateConversion(new QuantityLength<>(36.0, LengthUnit.INCHES), LengthUnit.YARDS);
		demonstrateConversion(new QuantityLength<>(30.48, LengthUnit.CENTIMETERS), LengthUnit.FEET);
		demonstrateConversion(new QuantityLength<>(-1.0, LengthUnit.FEET), LengthUnit.INCHES);

		demonstrateAddition(new QuantityLength<>(1.0, LengthUnit.FEET), new QuantityLength<>(12.0, LengthUnit.INCHES));
		demonstrateAddition(new QuantityLength<>(12.0, LengthUnit.INCHES), new QuantityLength<>(1.0, LengthUnit.FEET));
		demonstrateAddition(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(3.0, LengthUnit.FEET));
		demonstrateAddition(new QuantityLength<>(2.54, LengthUnit.CENTIMETERS), new QuantityLength<>(1.0, LengthUnit.INCHES));
		demonstrateAddition(new QuantityLength<>(5.0, LengthUnit.FEET), new QuantityLength<>(0.0, LengthUnit.INCHES));
		demonstrateAddition(new QuantityLength<>(5.0, LengthUnit.FEET), new QuantityLength<>(-2.0, LengthUnit.FEET));
		
		demonstrateAddition(new QuantityLength<>(1.0, LengthUnit.FEET), new QuantityLength<>(12.0, LengthUnit.INCHES), LengthUnit.FEET);
		demonstrateAddition(new QuantityLength<>(1.0, LengthUnit.FEET), new QuantityLength<>(12.0, LengthUnit.INCHES), LengthUnit.INCHES);
		demonstrateAddition(new QuantityLength<>(1.0, LengthUnit.FEET), new QuantityLength<>(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
		demonstrateAddition(new QuantityLength<>(1.0, LengthUnit.YARDS), new QuantityLength<>(3.0, LengthUnit.FEET), LengthUnit.YARDS);
		demonstrateAddition(new QuantityLength<>(36.0, LengthUnit.INCHES), new QuantityLength<>(1.0, LengthUnit.YARDS), LengthUnit.FEET);
		demonstrateAddition(new QuantityLength<>(2.54, LengthUnit.CENTIMETERS), new QuantityLength<>(1.0, LengthUnit.INCHES), LengthUnit.CENTIMETERS);
		demonstrateAddition(new QuantityLength<>(5.0, LengthUnit.FEET), new QuantityLength<>(0.0, LengthUnit.INCHES), LengthUnit.YARDS);
		demonstrateAddition(new QuantityLength<>(5.0, LengthUnit.FEET), new QuantityLength<>(-2.0, LengthUnit.FEET), LengthUnit.INCHES);
    }
}