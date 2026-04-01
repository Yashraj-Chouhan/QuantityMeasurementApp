package com.app.quantitymeasurement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthResponse
 *
 * Data Transfer Object returned to the client after a successful authentication
 * event — either a local email/password login or a Google OAuth2 sign-in.
 *
 * <p>The JWT {@code accessToken} contained in this response must be included by
 * the client in the {@code Authorization} header of all subsequent requests to
 * protected endpoints, using the {@code Bearer} scheme:</p>
 *
 * <pre>
 * Authorization: Bearer &lt;accessToken&gt;
 * </pre>
 *
 * <p><b>Example JSON response:</b></p>
 * <pre>
 * {
 *   "accessToken":  "eyJhbGciOiJIUzI1NiJ9...",
 *   "tokenType":    "Bearer",
 *   "email":        "user@example.com",
 *   "name":         "Jane Doe",
 *   "role":         "USER"
 * }
 * </pre>
 *
 * <p><b>Token lifetime:</b> configured via {@code app.jwt.expiration-ms} in
 * {@code application.properties}. After expiry, the client must re-authenticate
 * to obtain a new token (refresh tokens are outside the scope of UC-18).</p>
 *
 * @author Abhishek Puri Goswami
 * @version 18.0
 * @since 18.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    /**
     * The signed JWT access token. Must be sent as {@code Authorization: Bearer <token>}
     * on every protected API call.
     */
    private String accessToken;

    /**
     * Always {@code "Bearer"}. Included for RFC 6750 compliance so clients do not
     * need to hard-code the scheme.
     */
    @Builder.Default
    private String tokenType = "Bearer";

    /**
     * The authenticated user's email address. Convenient for the client to
     * display the current user's identity without decoding the JWT.
     */
    private String email;

    /**
     * The authenticated user's display name (may be {@code null} for local
     * accounts that did not supply one at registration).
     */
    private String name;

    /**
     * The authenticated user's role ({@code "USER"} or {@code "ADMIN"}).
     * Allows the client to conditionally show or hide admin-only UI elements
     * without decoding the JWT.
     */
    private String role;
}
