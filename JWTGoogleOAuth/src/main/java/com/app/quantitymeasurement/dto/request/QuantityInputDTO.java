package com.app.quantitymeasurement.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import com.app.quantitymeasurement.dto.response.QuantityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuantityInputDTO
 *
 * Request body DTO that wraps the operands for a quantity measurement operation.
 *
 * <p>All five POST endpoints in {@code QuantityMeasurementController} accept this
 * class as their {@code @RequestBody}. It carries:</p>
 * <ul>
 *   <li>{@code thisQuantityDTO} — the first (or only) operand, required.</li>
 *   <li>{@code thatQuantityDTO} — the second operand, required.</li>
 *   <li>{@code targetUnitDTO}   — optional target unit; when present, the result
 *       is expressed in this unit instead of the first operand's unit.</li>
 * </ul>
 *
 * <p>Example JSON (add with explicit target unit):</p>
 * <pre>
 * {
 *   "thisQuantityDTO": { "value": 2.0, "unit": "FEET",   "measurementType": "LengthUnit" },
 *   "thatQuantityDTO": { "value": 24.0, "unit": "INCHES", "measurementType": "LengthUnit" },
 *   "targetUnitDTO":  { "value": 0.0, "unit": "YARDS",  "measurementType": "LengthUnit" }
 * }
 * </pre>
 *
 * <p>{@code @Valid} on each nested DTO ensures their own Bean Validation constraints
 * are evaluated before the controller delegates to the service.</p>
 *
 * @author Abhishek Puri Goswami
 * @version 17.0
 * @since 17.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityInputDTO {

    /** First operand. Required and fully validated. */
    @NotNull(message = "thisQuantityDTO must not be null")
    @Valid
    private QuantityDTO thisQuantityDTO;

    /** Second operand. Required and fully validated. */
    @NotNull(message = "thatQuantityDTO must not be null")
    @Valid
    private QuantityDTO thatQuantityDTO;

    /**
     * Optional target unit for the operation result.
     * When omitted, the result uses the unit of {@code thisQuantityDTO}.
     */
    @Valid
    private QuantityDTO targetUnitDTO;
}
