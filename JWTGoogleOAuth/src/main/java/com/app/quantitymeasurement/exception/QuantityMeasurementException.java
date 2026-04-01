package com.app.quantitymeasurement.exception;

/**
 * QuantityMeasurementException
 *
 * Custom runtime exception for errors that occur during quantity measurement
 * operations such as comparison, conversion, or arithmetic.
 *
 * Extends {@link RuntimeException} so it propagates unchecked through the call
 * stack and can be handled centrally by {@code GlobalExceptionHandler}, which
 * maps it to a structured {@code 400 Bad Request} response.
 *
 * Typical causes:
 * <ul>
 *   <li>Invalid unit name provided by the caller.</li>
 *   <li>Arithmetic attempted on incompatible measurement categories.</li>
 *   <li>Arithmetic attempted on temperature units (not physically meaningful).</li>
 *   <li>Division by zero.</li>
 * </ul>
 *
 * @author Abhishek Puri Goswami
 * @version 17.0
 * @since 1.0
 */
public class QuantityMeasurementException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the given error message.
     *
     * @param message description of the error
     */
    public QuantityMeasurementException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the given message and root cause.
     * Use this constructor when wrapping a lower-level exception so that
     * the original stack trace is preserved.
     *
     * @param message description of the error
     * @param cause   underlying exception
     */
    public QuantityMeasurementException(String message, Throwable cause) {
        super(message, cause);
    }
}
