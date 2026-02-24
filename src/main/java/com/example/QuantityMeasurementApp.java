package com.example;

import java.util.Scanner;

import com.example.QuantityLength.LengthUnit;

public class QuantityMeasurementApp {
	
	// static method to demostrate length equality
	public static boolean demonstrateLengthEquality(QuantityLength l1, QuantityLength l2) {
		return l1.equals(l2);
	}
		
	public static void demonstrateFeetEquality() {
	       QuantityLength feet1 = new QuantityLength(1.0,LengthUnit.FEET);
	       QuantityLength feet2 = new QuantityLength(1.0,LengthUnit.FEET);    
	       System.out.println("Feet Equal ("+feet1.equals(feet2)+")");
	}
	
	public static void demonstrateInchesEquality() {
		QuantityLength inch1 = new QuantityLength(1.0,LengthUnit.INCHES);
		QuantityLength inch2 = new QuantityLength(1.0,LengthUnit.INCHES);
		System.out.println("Inches Equal ("+ inch1.equals(inch2)+")");
	}
	
	// Static method to demonstrate Feet and Inches comparison
			public static void demonstrateFeetInchesComparison() {
				QuantityLength feet1 = new QuantityLength(1.0, LengthUnit.FEET);
				QuantityLength inch12 = new QuantityLength(12.0, LengthUnit.INCHES);
				System.out.println("Feet vs Inches equality: " + demonstrateLengthEquality(feet1, inch12));
			}
	
    public static void main(String[] args) {
    	demonstrateFeetEquality();
    	demonstrateInchesEquality();
    	demonstrateFeetInchesComparison();
    }
}