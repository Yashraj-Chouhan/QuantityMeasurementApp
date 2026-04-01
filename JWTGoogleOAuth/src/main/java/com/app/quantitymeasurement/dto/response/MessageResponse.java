package com.app.quantitymeasurement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MessageResponse
 *
 * A lightweight DTO that wraps a single {@code message} string.
 * Used by endpoints that return a human-readable status message
 * rather than a full resource (e.g. forgotPassword, resetPassword).
 *
 * <p><b>Example JSON:</b></p>
 * <pre>
 * { "message": "Password has been changed successfully!" }
 * </pre>
 *
 * @author UC19
 * @version 19.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
