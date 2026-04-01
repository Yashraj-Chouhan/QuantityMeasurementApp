package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.enums.AuthProvider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 *
 * Spring Data JPA repository for the {@link User} entity. Provides standard CRUD
 * operations inherited from {@link JpaRepository}, plus application-specific
 * finder methods used by the authentication and user-management flows.
 *
 * <p><b>Finder methods and their use cases:</b></p>
 * <ul>
 *   <li>{@link #findByEmail(String)}               — used by
 *       {@code CustomUserDetailsService} to load a principal for local login,
 *       and by the JWT filter to resolve the subject claim back to a full User.</li>
 *   <li>{@link #existsByEmail(String)}              — used during registration to
 *       reject duplicate email addresses before attempting an INSERT.</li>
 *   <li>{@link #findByProviderAndProviderId}        — used by
 *       {@code CustomOAuth2UserService} to look up an existing account when the
 *       same OAuth2 user (Google or GitHub) signs in again, without relying on
 *       an email address that the user may have changed on the provider's side.</li>
 * </ul>
 *
 * <p>All method names follow Spring Data's query-derivation naming convention,
 * so no explicit JPQL / SQL is required.</p>
 *
 * @author Abhishek Puri Goswami
 * @version 18.0
 * @since 18.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Looks up a user by their email address.
     *
     * <p>The email address is the canonical identity key for local, Google, and
     * GitHub-authenticated accounts. Spring Data generates:
     * {@code SELECT * FROM app_user WHERE email = ?1}</p>
     *
     * @param email the email address to search for
     * @return an {@link Optional} containing the matching user, or empty if none found
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether a user with the given email address already exists.
     *
     * <p>Used during local registration to prevent duplicate accounts.
     * More efficient than {@link #findByEmail(String)} when only a boolean
     * result is needed, because Spring Data can translate it to a
     * {@code SELECT COUNT(*) > 0} query.</p>
     *
     * @param email the email address to check
     * @return {@code true} if a user with this email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Looks up a user by their OAuth2 provider and the provider-assigned subject ID.
     *
     * <p>Used by {@code CustomOAuth2UserService} for both Google and GitHub logins.
     * Each provider issues a stable, opaque identifier that does not change even if
     * the user changes their email address on the provider's platform:</p>
     * <ul>
     *   <li><b>Google</b> — the {@code sub} claim from the OpenID Connect ID token.</li>
     *   <li><b>GitHub</b> — the numeric {@code id} field from the UserInfo response,
     *       stored as a String.</li>
     * </ul>
     *
     * @param provider   the authentication provider (e.g., {@link AuthProvider#GOOGLE},
     *                   {@link AuthProvider#GITHUB})
     * @param providerId the provider-assigned subject identifier
     * @return an {@link Optional} containing the matching user, or empty if none found
     */
    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);
}
