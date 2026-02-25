package com.example;

import java.util.function.DoubleBinaryOperator;

public class QuantityLength<U extends IMeasurable> {
	private final double value;
	private final U unit;
	private static final double EPSILON = 1e-6;
	private static final double ROUND_SCALE = 1e6;
	
	private enum ArithmeticOperation {

		ADD((a, b) -> a + b), SUBTRACT((a, b) -> a - b), DIVIDE((a, b) -> {
			if (b == 0.0)
				throw new ArithmeticException("Division by zero");
			return a / b;
		});

		private final DoubleBinaryOperator op;

		ArithmeticOperation(DoubleBinaryOperator op) {
			this.op = op;
		}

		public double compute(double a, double b) {
			return op.applyAsDouble(a, b);
		}
	}

	
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
	
	private void validateArithmeticOperands(QuantityLength<? extends IMeasurable> other, IMeasurable targetUnit,boolean targetUnitRequired) {
		if (other == null) {
			throw new IllegalArgumentException("Other quantity must not be null");
		}
		if (this.unit == null || other.getUnit() == null) {
			throw new IllegalArgumentException("Unit must not be null");
		}
		if (!Double.isFinite(this.value) || !Double.isFinite(other.getValue())) {
			throw new IllegalArgumentException("Values must be finite numbers");
		}
		if (this.unit.getClass() != other.getUnit().getClass()) {
			throw new IllegalArgumentException("Cannot operate across different measurement categories");
		}
		if (targetUnitRequired && targetUnit == null) {
			throw new IllegalArgumentException("Target unit must not be null");
		}
		if (targetUnit != null && targetUnit.getClass() != this.unit.getClass()) {
			throw new IllegalArgumentException("Target unit must belong to same measurement category");
		}
	}

	private double performArithmetic(QuantityLength<? extends IMeasurable> other, ArithmeticOperation operation) {
		 // Validate operation support on both units before any conversion
        this.unit.validateOperationSupport(operation.name());
        other.getUnit().validateOperationSupport(operation.name());

        // For division, require both units to explicitly support arithmetic
        if (operation == ArithmeticOperation.DIVIDE) {
            if (!this.unit.supportsArithmetic() || !other.getUnit().supportsArithmetic()) {
                throw new UnsupportedOperationException(
                        "Division not supported for unit type: " + this.unit.getClass().getSimpleName());
            }
        }

        double baseThis = this.unit.convertToBaseUnit(this.value);
        double baseOther = other.getUnit().convertToBaseUnit(other.getValue());
        return operation.compute(baseThis, baseOther);
	}
	
	public QuantityLength<U> add(QuantityLength<? extends IMeasurable> thatLength) {
		validateArithmeticOperands(thatLength, this.unit, false);
		double baseResult = performArithmetic(thatLength, ArithmeticOperation.ADD);
		double resultInThisUnit = this.unit.convertFromBaseUnit(baseResult);
		double rounded = Math.round(resultInThisUnit * ROUND_SCALE) / ROUND_SCALE;
		return new QuantityLength<>(rounded, this.unit);
	}
	
	public QuantityLength<U> add(QuantityLength<? extends IMeasurable> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);
        // validate target unit supports the operation
        targetUnit.validateOperationSupport(ArithmeticOperation.ADD.name());

        double baseResult = performArithmetic(other, ArithmeticOperation.ADD);
        double resultInTarget = targetUnit.convertFromBaseUnit(baseResult);
        double rounded = Math.round(resultInTarget * ROUND_SCALE) / ROUND_SCALE;
        return new QuantityLength<>(rounded, targetUnit);
	}

	public QuantityLength<U> subtract(QuantityLength<? extends IMeasurable> other) {
		validateArithmeticOperands(other, this.unit, false);
		double baseResult = performArithmetic(other, ArithmeticOperation.SUBTRACT);
		double resultInThisUnit = this.unit.convertFromBaseUnit(baseResult);
		double rounded = Math.round(resultInThisUnit * ROUND_SCALE) / ROUND_SCALE;
		return new QuantityLength<>(rounded, this.unit);
	}

	public QuantityLength<U> subtract(QuantityLength<? extends IMeasurable> other, U targetUnit) {
		 validateArithmeticOperands(other, targetUnit, true);
	        targetUnit.validateOperationSupport(ArithmeticOperation.SUBTRACT.name());

	        double baseResult = performArithmetic(other, ArithmeticOperation.SUBTRACT);
	        double resultInTarget = targetUnit.convertFromBaseUnit(baseResult);
	        double rounded = Math.round(resultInTarget * ROUND_SCALE) / ROUND_SCALE;
	        return new QuantityLength<>(rounded, targetUnit);
	}

	public double divide(QuantityLength<? extends IMeasurable> other) {
		validateArithmeticOperands(other, null, false);
		return performArithmetic(other, ArithmeticOperation.DIVIDE);
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