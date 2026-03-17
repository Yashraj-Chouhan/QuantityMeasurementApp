package com.example.entity;
import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String operation;
    private double operand1;
    private double operand2;
    private double result;
    private String error;

    public QuantityMeasurementEntity(String operation, double operand1, double operand2, double result) {
        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
    }

    public QuantityMeasurementEntity(String error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }

    @Override
    public String toString() {
        if (hasError())
            return "Error: " + error;

        return operation + " Result = " + result;
    }
}