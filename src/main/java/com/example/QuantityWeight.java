package com.example;

public class QuantityWeight {
	private final double value;
	private final WeightUnit unit;
	
	private static final double EPSILON = 1e-6;
	
	// Constructor to initialize length value and unit
	public QuantityWeight(double value, WeightUnit unit) {
		if(unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if(!Double.isFinite(value)) {
			throw new IllegalArgumentException("value must be a finite number");
		}
		this.value = value;
		this.unit = unit;
	}
	
	public double getValue() {
		return value;
	}
	
	public WeightUnit getUnit() {
		return unit;
	}
	
	//Convert the length value to the base unit(inches)
	private double convertToBaseUnit(){
		return unit.convertToBaseUnit(value);
	}
	
	private double convertFromBaseToTargetUnit(double lengthInInches, WeightUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		return targetUnit.convertFromBaseUnit(lengthInInches);
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
		QuantityWeight other = (QuantityWeight) obj;
		
		//5. compare double values safely
		double base1 = this.convertToBaseUnit();
		double base2 = other.convertToBaseUnit();
		return Double.compare(base1, base2) == 0;
	}
	
	@Override
	public int hashCode() {
		double normalized = Math.round(convertToBaseUnit() * 10000.0) / 10000.0;
		return Double.hashCode(normalized);
	}
	
	public QuantityWeight convertTo(WeightUnit targetUnit) {
		if(targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		double baseValue = this.convertToBaseUnit();
		double convertedValue = convertFromBaseToTargetUnit(baseValue,targetUnit);
		return new QuantityWeight(convertedValue,targetUnit);
	}
	
	public QuantityWeight add(QuantityWeight thatLength) {
		if(thatLength == null) {
			throw new IllegalArgumentException("Second oprend null");
		}
		
		double base1 = this.convertToBaseUnit();
		double base2 = thatLength.convertToBaseUnit();
		
		double sumBase = base1 + base2;
		
		double resultThisUnit = convertFromBaseToTargetUnit(sumBase, this.unit);
		return new QuantityWeight(resultThisUnit,this.unit);
	}
	
	public QuantityWeight add(QuantityWeight thatLength, WeightUnit targetUnit) {
		if(thatLength == null || targetUnit == null) {
			throw new IllegalArgumentException();
		}
		if(!Double.isFinite(this.value) || !Double.isFinite(thatLength.value)) {
	    	throw new IllegalArgumentException("Values must be a finite number");
	    }
		
		double base1 = this.convertToBaseUnit();
		double base2 = thatLength.convertToBaseUnit();
		
		double sumBase = base1 + base2;
		
		double resultThisUnit = convertFromBaseToTargetUnit(sumBase, targetUnit);
		return new QuantityWeight(resultThisUnit,targetUnit);
	}
	
	@Override 
	public String toString() {
		return String.format("%.2f %s",value, unit);
	}
}	