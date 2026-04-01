package com.app.quantitymeasurement.unit;

/**
 * SupportsArithmetic
 *
 * Marker interface that tags a measurement unit as eligible for arithmetic operations
 * (addition, subtraction, division).
 *
 * <p>The service layer checks for this marker at runtime using {@code instanceof}
 * before allowing add, subtract, or divide operations. Units that do <em>not</em>
 * implement this interface — specifically {@code TemperatureUnit} — will cause those
 * operations to throw {@link UnsupportedOperationException}.</p>
 *
 * <p>Implementations: {@code LengthUnit}, {@code WeightUnit}, {@code VolumeUnit}.</p>
 *
 * @author Abhishek Puri Goswami
 * @version 17.0
 * @since 1.0
 */
public interface SupportsArithmetic {
}
