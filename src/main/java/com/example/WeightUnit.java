package com.example;

public enum WeightUnit {
	KILOGRAM(1.0),  // conversion factor 1kg = 1kg
	GRAM(0.001),	// conversion factor 1g = 0.001kg
	POUND(0.453592);// conversion factor 1lb = 0.453592kg
	
	private final double conversionFactor;
	
	// constructor
	WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor; 
	}

	// conversion factor relative to kilograms
	public double getConversionFactor() {
		return conversionFactor;
	}
	
	// convert given value -> base unit(Kilogram)
	public double convertToBaseUnit(double value) {
		double result =  value * conversionFactor;
		return Math.round(result * 1_000_000.0) / 1_000_000.0;
	}
	
	//convert from base unit -> target unit
	public double convertFromBaseUnit(double baseValue) {
		double result =  baseValue / conversionFactor;
		return Math.round(result * 1_000_000.0) / 1_000_000.0;
	}
}