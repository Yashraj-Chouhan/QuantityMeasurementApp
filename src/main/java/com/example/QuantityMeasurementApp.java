package com.example;

import java.util.Scanner;

import com.example.QuantityLength.LengthUnit;

public class QuantityMeasurementApp {
	
	// static method to demostrate length equality
	public static boolean demonstrateLengthEquality(QuantityLength l1, QuantityLength l2) {
		return l1.equals(l2);
	}
		
	// Static method to demonstrate extended unit comparisons
	public static boolean demonstrateLengthComparison(double value1, QuantityLength.LengthUnit unit1, double value2 , QuantityLength.LengthUnit unit2) {
		QuantityLength length1 = new QuantityLength(value1,unit1);
		QuantityLength length2 = new QuantityLength(value2,unit2);
		
		boolean result = length1.equals(length2);
		System.out.println("Are lengths equals? " + result);
		return result;
		
	}
    public static void main(String[] args) {
    	demonstrateLengthComparison(1.0, QuantityLength.LengthUnit.FEET, 12.0, QuantityLength.LengthUnit.INCHES);
		demonstrateLengthComparison(1.0, QuantityLength.LengthUnit.YARDS, 36.0, QuantityLength.LengthUnit.INCHES);
		demonstrateLengthComparison(100.0, QuantityLength.LengthUnit.CENTIMETERS, 39.3701, QuantityLength.LengthUnit.INCHES);
		demonstrateLengthComparison(3.0, QuantityLength.LengthUnit.FEET, 1.0, QuantityLength.LengthUnit.YARDS);
		demonstrateLengthComparison(30.48, QuantityLength.LengthUnit.CENTIMETERS, 1.0, QuantityLength.LengthUnit.FEET);
    }
}