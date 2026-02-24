package com.example;

public class QuantityLength {
	private final double value;
	private final LengthUnit unit;
	
	private static final double EPSILON = 0.0001;
	
	// Enum to represent supported units of length
	public enum LengthUnit {
		FEET(12.0), // Conversion Factor: 1 foot = 12 inches
		INCHES(1.0),// Conversion Factor: 1 Inch = 1 Inch(base unit)
		YARDS(36.0), // Conversion Factor: 1 yard = 36 inches
		CENTIMETERS(0.393701); // Conversion Factor 1 cm = 0.393701in
		
		private final double conversionFactor;
		
		LengthUnit(double conversionFactor){
			this.conversionFactor = conversionFactor;
		}
		
		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	
	// Constructor to initialize length value and unit
	public QuantityLength(double value, LengthUnit unit) {
		if(unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if(!Double.isFinite(value)) {
			throw new IllegalArgumentException("value must be a finite number");
		}
		this.value = value;
		this.unit = unit;
	}
	
	//Convert the length value to the base unit(inches)
	private double convertToBaseUnit(){
		return value * unit.getConversionFactor();
	}
	

	public boolean compare(QuantityLength obj) {
		return Math.abs(this.convertToBaseUnit() - obj.convertToBaseUnit()) < EPSILON;
	}
	
	@Override
	public boolean equals(Object obj) {
		// 1.same reference
		if(this == obj) {
			return true;
		}
		
		//2. obj is null
		if(obj == null) {
			return false;
		}
		
		//3. Type Check
		if(getClass()!= obj.getClass()) {
			return false;
		}
		
		//4. Cast safely
		QuantityLength other = (QuantityLength) obj;
		
		//5. compare double values safely
		return compare(other);
	}
	
	@Override
	public int hashCode() {
	 return Double.hashCode(convertToBaseUnit());
	}
	
	@Override 
	public String toString() {
		return String.format("%.2f %s",value, unit);
	}
	
	// convert this length to the specified target unit
	public QuantityLength convertTo(LengthUnit targetUnit) {
		if(targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		double baseValue = this.convertToBaseUnit();
		double convertedValue = baseValue / targetUnit.getConversionFactor();
	
		convertedValue = Math.round(convertedValue * 100.0) /100.0 ;
		return new QuantityLength(convertedValue,targetUnit);
	}
	
	// main method for standalone testing
	public static void main(String []args) {
		QuantityLength length1 = new QuantityLength(1.0,LengthUnit.FEET);
		QuantityLength length2 = new QuantityLength(12.0,LengthUnit.INCHES);
		System.out.println("Are lengths equal? "+ length1.equals(length2));
		
		QuantityLength length3 = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength length4 = new QuantityLength(36.0,LengthUnit.INCHES);
		System.out.println("Are lengths equal? "+ length3.equals(length4));
		
		QuantityLength length5 = new QuantityLength(100.0,LengthUnit.CENTIMETERS);
		QuantityLength length6 = new QuantityLength(39.3701,LengthUnit.INCHES);
		System.out.println("Are lengths equal? "+ length5.equals(length6));
		
		System.out.println("Convert 3 Feet to Inches: " + length1.convertTo(LengthUnit.INCHES));
		System.out.println("Convert 2 Yards to Inches: " + length3.convertTo(LengthUnit.INCHES));
		System.out.println("Convert 30.48 cm to Feet: " + new QuantityLength(30.48, LengthUnit.CENTIMETERS).convertTo(LengthUnit.FEET));
		System.out.println("Convert 72 Inches to Yards: " + new QuantityLength(72.0, LengthUnit.INCHES).convertTo(LengthUnit.YARDS));
		System.out.println("Convert 0 Feet to Inches: " + new QuantityLength(0.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES));
		System.out.println("Convert -1 Foot to Inches: " + new QuantityLength(-1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES));

	}
	
}