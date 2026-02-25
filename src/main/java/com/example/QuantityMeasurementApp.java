package com.example;

import java.util.Scanner;

import com.example.LengthUnit;

public class QuantityMeasurementApp {
	
	// static method to demostrate length equality
	public static boolean demonstrateLengthEquality(QuantityLength l1, QuantityLength l2) {
		return l1.equals(l2);
	}
		
	// Static method to demonstrate extended unit comparisons
	public static boolean demonstrateLengthComparison(double value1,  LengthUnit unit1, double value2 ,  LengthUnit unit2) {
		QuantityLength length1 = new QuantityLength(value1,unit1);
		QuantityLength length2 = new QuantityLength(value2,unit2);
		System.out.println("Are lengths equals? " + demonstrateLengthEquality(length1,length2));
		return demonstrateLengthEquality(length1,length2);
		
	}
	

	public static double convert(double value,  LengthUnit fromUnit,  LengthUnit toUnit) {
		
		if(fromUnit == null || toUnit == null) 
			throw new IllegalArgumentException("Units cannot be null");

		
		if (Double.isNaN(value) || Double.isInfinite(value))
				throw new IllegalArgumentException("Invalid numeric value");

		// convert to base(inches)
		double baseValue = value * fromUnit.getConversionFactor();
		// convert base to target
		return baseValue / toUnit.getConversionFactor();
	}
	
	//Overloaded conversion method (Using QuantityLength object)
	public static QuantityLength demonstrateLengthConversion(double value,  LengthUnit fromUnit,  LengthUnit toUnit) {
		QuantityLength source  =  new QuantityLength(value,fromUnit);
		QuantityLength convert = source.convertTo(toUnit);
		System.out.println(source + " -> "+ convert);
		return convert;
	}
	
	public static QuantityLength demonstrateLengthConversion(QuantityLength length,  LengthUnit targetUnit) {
		QuantityLength convert = length.convertTo(targetUnit);
		System.out.println(length + " -> "+ convert);
		return convert;
	}
	
	// static method to demonstrate addition of two Length objects
	public static QuantityLength demonstrateLengthAddition (QuantityLength length1 , QuantityLength length2) {
	QuantityLength sum = length1.add(length2);
	System.out.println(length1 + " + " + length2 + " = " + sum);
	return sum;
	}
	
	//UC-7
	public static QuantityLength demonstrateLengthAddition (QuantityLength length1, QuantityLength length2,  LengthUnit targetUnit) {
		QuantityLength sum = length1.add(length2);
		return demonstrateLengthConversion(sum,targetUnit);
	}
	
    public static void main(String[] args) {
    	demonstrateLengthComparison(1.0,  LengthUnit.FEET, 12.0,  LengthUnit.INCHES);
		demonstrateLengthComparison(1.0,  LengthUnit.YARDS, 36.0,  LengthUnit.INCHES);
		demonstrateLengthComparison(100.0,  LengthUnit.CENTIMETERS, 39.3701,  LengthUnit.INCHES);
		demonstrateLengthComparison(3.0,  LengthUnit.FEET, 1.0,  LengthUnit.YARDS);
		demonstrateLengthComparison(30.48,  LengthUnit.CENTIMETERS, 1.0,  LengthUnit.FEET);
		System.out.println();
		
		demonstrateLengthConversion(1.0,  LengthUnit.FEET,  LengthUnit.INCHES);
		demonstrateLengthConversion(3.0,  LengthUnit.YARDS,  LengthUnit.FEET);
		demonstrateLengthConversion(30.48,  LengthUnit.CENTIMETERS,  LengthUnit.FEET);
		demonstrateLengthConversion(36.0,  LengthUnit.INCHES,  LengthUnit.YARDS);
		System.out.println();
		
		demonstrateLengthConversion(new QuantityLength(-1.0, LengthUnit.FEET), LengthUnit.INCHES);
		
		demonstrateLengthAddition(
				new QuantityLength(1.0,LengthUnit.FEET),
				new QuantityLength(2.0,LengthUnit.FEET)
				);
		
		demonstrateLengthAddition(
				new QuantityLength(1.0,LengthUnit.FEET),
				new QuantityLength(12.0,LengthUnit.INCHES)
				);
		
		demonstrateLengthAddition(
				new QuantityLength(12.0,LengthUnit.INCHES),
				new QuantityLength(1.0,LengthUnit.FEET)
				);
		
		demonstrateLengthAddition(
				new QuantityLength(1.0,LengthUnit.YARDS),
				new QuantityLength(3.0,LengthUnit.FEET)
				);
		
		demonstrateLengthAddition(
				new QuantityLength(36.0,LengthUnit.INCHES),
				new QuantityLength(1.0,LengthUnit.YARDS)
				);
		
		demonstrateLengthAddition(
				new QuantityLength(2.54,LengthUnit.CENTIMETERS),
				new QuantityLength(1.0,LengthUnit.INCHES)
				);
		
		demonstrateLengthAddition(
				new QuantityLength(5.0,LengthUnit.FEET),
				new QuantityLength(0.0,LengthUnit.INCHES)
				);
		
		System.out.println();
		
		demonstrateLengthAddition(
				new QuantityLength(5.0,LengthUnit.FEET),
				new QuantityLength(-2.0,LengthUnit.FEET)
				);
		
		demonstrateLengthAddition(
				new QuantityLength(1.0,LengthUnit.FEET),
				new QuantityLength(12.0,LengthUnit.INCHES),
				LengthUnit.FEET
				);
		
		demonstrateLengthAddition(
				new QuantityLength(1.0,LengthUnit.FEET),
				new QuantityLength(12.0,LengthUnit.INCHES),
				LengthUnit.INCHES
				);
		
		demonstrateLengthAddition(
				new QuantityLength(3.0,LengthUnit.FEET),
				new QuantityLength(1.0,LengthUnit.YARDS),
				LengthUnit.YARDS
				);
		
		demonstrateLengthAddition(
				new QuantityLength(1.0,LengthUnit.FEET),
				new QuantityLength(12.0,LengthUnit.INCHES),
				LengthUnit.YARDS
				);
		
		demonstrateLengthAddition(
				new QuantityLength(36.0,LengthUnit.INCHES),
				new QuantityLength(1.0,LengthUnit.YARDS),
				LengthUnit.FEET
				);
		
		demonstrateLengthAddition(
				new QuantityLength(2.54,LengthUnit.CENTIMETERS),
				new QuantityLength(1.0,LengthUnit.INCHES),
				LengthUnit.CENTIMETERS
				);
		
		demonstrateLengthAddition(
				new QuantityLength(5.0,LengthUnit.FEET),
				new QuantityLength(0.0,LengthUnit.INCHES),
				LengthUnit.YARDS
				);
		
		demonstrateLengthAddition(
				new QuantityLength(5.0,LengthUnit.FEET),
				new QuantityLength(-2.0,LengthUnit.FEET),
				LengthUnit.INCHES
				);
		System.out.println();
    }
}