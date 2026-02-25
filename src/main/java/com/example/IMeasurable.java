package com.example;

public interface IMeasurable {
	public double getConversionFactor();

	public double convertToBaseUnit(double value);

	public double convertFromBaseUnit(double baseValue);

	public String getUnitName();
	
	// ===== NEW UC14 ADDITIONS =====

    // default lambda → all units support arithmetic by default
    SupportsArithmetic supportsArithmetic = () -> true;

    // default method → existing units inherit TRUE
    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    /*
     * Default validation method.
     * Units that do NOT support arithmetic (Temperature) will override this.
     */
    default void validateOperationSupport(String operation) {
        // do nothing by default
    }
}