package com.example;

public enum LengthUnit implements IMeasurable {
		FEET(12.0), // Conversion Factor: 1 foot = 12 inches
		INCHES(1.0),// Conversion Factor: 1 Inch = 1 Inch(base unit)
		YARDS(36.0), // Conversion Factor: 1 yard = 36 inches
		CENTIMETERS(1/2.54); // Conversion Factor 1 cm = 0.393701in
		
		private final double conversionFactor;
		
		LengthUnit(double conversionFactor){
			this.conversionFactor = conversionFactor;
		}
		
		@Override
		public double getConversionFactor() {
			return conversionFactor;
		}
		
		@Override
		public double convertToBaseUnit(double value) {
	        double result = value * conversionFactor;
	        return Math.round(result * 1_000_000.0) / 1_000_000.0;
	    }
		
		@Override
		public double convertFromBaseUnit(double baseValue) {
	        double result = baseValue / conversionFactor;
	        return Math.round(result * 1_000_000.0) / 1_000_000.0;
	    }
		
		@Override
		public String getUnitName() {
		    return name();
		}
	}