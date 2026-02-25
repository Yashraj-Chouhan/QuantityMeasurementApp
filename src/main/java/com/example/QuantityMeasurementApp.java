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
	
	// ---------------UC9------------------------
	public static boolean demonstrateWeightEquality(QuantityWeight weight1, QuantityWeight weight2) {
		return weight1.equals(weight2);
	}
	
	public static boolean demonstrateWeightComparison(double value1,  WeightUnit unit1, double value2 ,  WeightUnit unit2) {
		QuantityWeight weight1 = new QuantityWeight(value1,unit1);
		QuantityWeight weight2 = new QuantityWeight(value2,unit2);
		System.out.println("Are lengths equals? " + demonstrateWeightEquality(weight1,weight2));
		return demonstrateWeightEquality(weight1,weight2);
	}
	
	public static QuantityWeight demonstrateWeightConversion(double value,  WeightUnit fromUnit,  WeightUnit toUnit) {
		QuantityWeight source  =  new QuantityWeight(value,fromUnit);
		QuantityWeight convert = source.convertTo(toUnit);
		System.out.println(source + " -> "+ convert);
		return convert;
	}
	
	public static QuantityWeight demonstrateWeightConversion(QuantityWeight weight,  WeightUnit targetUnit) {
		QuantityWeight convert = weight.convertTo(targetUnit);
		System.out.println(weight + " -> "+ convert);
		return convert;
	}
	
	public static QuantityWeight demonstrateWeightAddition (QuantityWeight weight1 , QuantityWeight weight2) {
	QuantityWeight sum = weight1.add(weight2);
	System.out.println(weight1 + " + " + weight2 + " = " + sum);
	return sum;
	}
	
	public static QuantityWeight demonstrateWeightAddition (QuantityWeight weight1, QuantityWeight weight2,  WeightUnit targetUnit) {
		QuantityWeight sum = weight1.add(weight2);
		return demonstrateWeightConversion(sum,targetUnit);
	}
	
	
    public static void main(String[] args) {
    	demonstrateWeightComparison(1.0, WeightUnit.KILOGRAM, 1000.0, WeightUnit.GRAM);
		demonstrateWeightComparison(2.204624, WeightUnit.POUND, 1.0, WeightUnit.KILOGRAM);
		demonstrateWeightComparison(453.592, WeightUnit.GRAM, 1.0, WeightUnit.POUND);
		demonstrateWeightComparison(1.0, WeightUnit.KILOGRAM, 1.0, WeightUnit.KILOGRAM);
		demonstrateWeightComparison(2.0, WeightUnit.POUND, 2.0, WeightUnit.POUND);
		demonstrateWeightComparison(500.0, WeightUnit.GRAM, 0.5, WeightUnit.KILOGRAM);
		
		demonstrateWeightConversion(1.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);
		demonstrateWeightConversion(2.0, WeightUnit.POUND, WeightUnit.KILOGRAM);
		demonstrateWeightConversion(500.0, WeightUnit.GRAM, WeightUnit.POUND);
		demonstrateWeightConversion(0.0, WeightUnit.KILOGRAM, WeightUnit.GRAM);
		
		demonstrateWeightConversion(
	        new QuantityWeight(-1.0, WeightUnit.KILOGRAM),
	        WeightUnit.GRAM
        );

		demonstrateWeightAddition(
	        new QuantityWeight(1.0, WeightUnit.KILOGRAM),
	        new QuantityWeight(2.0, WeightUnit.KILOGRAM)
        );
		
		demonstrateWeightAddition(
	        new QuantityWeight(1.0, WeightUnit.KILOGRAM),
	        new QuantityWeight(1000.0, WeightUnit.GRAM)
        );

		demonstrateWeightAddition(
	        new QuantityWeight(500.0, WeightUnit.GRAM),
	        new QuantityWeight(0.5, WeightUnit.KILOGRAM)
        );

		demonstrateWeightAddition(
	        new QuantityWeight(1.0, WeightUnit.KILOGRAM),
	        new QuantityWeight(1000.0, WeightUnit.GRAM),
	        WeightUnit.GRAM
        );

		demonstrateWeightAddition(
	        new QuantityWeight(1.0, WeightUnit.POUND),
	        new QuantityWeight(453.592, WeightUnit.GRAM),
	        WeightUnit.POUND
        );
		
		demonstrateWeightAddition(
	        new QuantityWeight(2.0, WeightUnit.KILOGRAM),
	        new QuantityWeight(4.0, WeightUnit.POUND),
	        WeightUnit.KILOGRAM
        );
		
		System.out.println("Weight vs Length equality: " +
		    new QuantityWeight(1.0, WeightUnit.KILOGRAM)
		    .equals(new QuantityLength(1.0, LengthUnit.FEET))
		);
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