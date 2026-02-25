package com.example;

public class QuantityLength<U extends IMeasurable> {
	private final double value;
	private final U unit;
	private static final double EPSILON = 1e-6;
	
	// Constructor to initialize length value and unit
	public QuantityLength(double value, U unit) {
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

	public U getUnit() {
		return unit;
	}
	
	@Override
	public boolean equals(Object obj) {
		// 1.same reference
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof QuantityLength<?> other)) {
			return false;
		}
		
		if (this.unit.getClass() != other.unit.getClass()) {
			return false;
		}

		double thisBase = unit.convertToBaseUnit(value);
		double otherBase = other.unit.convertToBaseUnit(other.value);
		return Math.abs(thisBase - otherBase) < EPSILON;
	}
	
	
	public QuantityLength<U> convertTo(U targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double baseValue = unit.convertToBaseUnit(value);
		double converted = targetUnit.convertFromBaseUnit(baseValue);

		return new QuantityLength<>(converted, targetUnit);
	}
	public QuantityLength<U> add(QuantityLength<U> thatLength) {
		if(thatLength == null) {
			throw new IllegalArgumentException("Second oprend null");
		}
		
		double base1 = unit.convertToBaseUnit(value);
		double base2 = thatLength.unit.convertToBaseUnit(thatLength.value);
		
		double sumBase = base1 + base2;
		
		double result = unit.convertFromBaseUnit(sumBase);
		return new QuantityLength(result,unit);
	}
	
	public QuantityLength<U> add(QuantityLength<U> thatLength, U targetUnit) {
		if(thatLength == null || targetUnit == null) {
			throw new IllegalArgumentException();
		}
		if(!Double.isFinite(this.value) || !Double.isFinite(thatLength.value)) {
	    	throw new IllegalArgumentException("Values must be a finite number");
	    }
		
		double base1 = unit.convertToBaseUnit(value);
		double base2 = thatLength.unit.convertToBaseUnit(thatLength.value);
		
		double sumBase = base1 + base2;
		
		double result = targetUnit.convertFromBaseUnit(sumBase);
		return new QuantityLength<>(result,targetUnit);
	}

	@Override
	public int hashCode() {
		Long normalized = Math.round(unit.convertToBaseUnit(value)  / EPSILON);
		return Long.hashCode(normalized);
	}
	
	@Override 
	public String toString() {
		return String.format("%s %s", Double.toString(value).replace("\\.0+$", ""), unit.getUnitName());
	}
}	