package com.app.quantitymeasurement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest
 *
 * Data Transfer Object for the local user registration request body.
 * Clients POST this payload to {@code /api/v1/auth/register}.
 *
 * <p>On success, {@code /api/v1/auth/register} returns a signed JWT
 * immediately — the user does not need a separate login step.</p>
 *
 * <p><b>Validation rules applied by Bean Validation ({@code @Valid}):</b></p>
 * <ul>
 *   <li>{@code email}    — non-blank, syntactically valid email address.</li>
 *   <li>{@code password} — must satisfy both a length constraint and a strength
 *       pattern:
 *       <ul>
 *         <li>Between 8 and 100 characters ({@code @Size}).</li>
 *         <li>At least one uppercase letter, one special character
 *             ({@code @#$%^&*()-+=}), and one digit ({@code @Pattern}).</li>
 *       </ul>
 *       BCrypt imposes a practical 72-byte limit on raw input, so 100
 *       characters is a safe upper bound for ASCII-heavy passwords.</li>
 *   <li>{@code name}     — optional display name; max 100 characters.</li>
 * </ul>
 *
 * <p>Constraint violations are caught by {@code GlobalExceptionHandler} and
 * returned as a {@code 400 Bad Request} response with a descriptive message.</p>
 *
 * <p><b>Example JSON payload:</b></p>
 * <pre>
 * {
 *   "email":    "newuser@example.com",
 *   "password": "Strong@123",
 *   "name":     "Jane Doe"
 * }
 * </pre>
 *
 * @author UC19
 * @version 19.0
 * @since 18.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    /**
     * The email address for the new account.
     * Must be unique across all existing users.
     * Used as the principal identifier for subsequent logins.
     */
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid address")
    private String email;

    /**
     * The plain-text password chosen by the user.
     * BCrypt-hashed before storage; the raw value is never persisted or logged.
     *
     * <p>Two constraints are applied in combination:</p>
     * <ul>
     *   <li>{@code @Size} — enforces the 8–100 character window.</li>
     *   <li>{@code @Pattern} — enforces at least one uppercase letter,
     *       one special character, and one digit.</li>
     * </ul>
     */
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(
        regexp  = "^(?=.*[A-Z])(?=.*[@#$%^&*()+\\-=])(?=.*[0-9]).{8,}$",
        message = "Password must contain at least 1 uppercase letter, " +
                  "1 special character (@#$%^&*()-+=), and 1 number"
    )
    private String password;

    /**
     * Optional display name for the user profile.
     * If not provided, it is stored as {@code null} in the database.
     */
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
}
