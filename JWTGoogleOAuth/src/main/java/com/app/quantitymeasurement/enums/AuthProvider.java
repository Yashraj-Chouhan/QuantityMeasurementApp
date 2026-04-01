package com.app.quantitymeasurement.enums;

/**
 * AuthProvider
 *
 * Identifies the authentication provider that was used to create or last
 * authenticate a user account.
 *
 * <p>This information is persisted alongside the {@code User} entity so the
 * application can:</p>
 * <ol>
 *   <li>Prevent a provider-registered user from attempting a local password
 *       login (and vice versa), giving a meaningful error rather than a
 *       generic "bad credentials" response.</li>
 *   <li>Link future OAuth2 logins back to the correct existing account
 *       using the stored {@code providerId}, which is a stable identifier
 *       issued by each provider.</li>
 * </ol>
 *
 * <p>Provider values:</p>
 * <ul>
 *   <li>{@code LOCAL}  — the user registered with email + password through
 *       the application's own registration endpoint. A BCrypt-hashed password
 *       is stored in the {@code password} column.</li>
 *   <li>{@code GOOGLE} — the user authenticated via "Sign in with Google"
 *       (OAuth2 Authorization Code flow). No password is stored; the
 *       {@code providerId} column holds the Google subject ({@code sub}) claim.</li>
 *   <li>{@code GITHUB} — the user authenticated via "Sign in with GitHub"
 *       (OAuth2 Authorization Code flow). No password is stored; the
 *       {@code providerId} column holds the GitHub numeric user ID.</li>
 * </ul>
 *
 * @author Abhishek Puri Goswami
 * @version 18.0
 * @since 18.0
 */
public enum AuthProvider {

    /**
     * Local authentication — email + BCrypt-hashed password stored in the database.
     */
    LOCAL,

    /**
     * Google OAuth2 authentication — identity verified by Google's OpenID Connect layer.
     * The {@code sub} claim is used as the stable {@code providerId}.
     */
    GOOGLE,

    /**
     * GitHub OAuth2 authentication — identity verified by GitHub's OAuth2 flow.
     * The GitHub numeric user ID is used as the stable {@code providerId}.
     * Email may be {@code null} if the user's GitHub email is set to private;
     * in that case the login is rejected with a descriptive error.
     */
    GITHUB
}
