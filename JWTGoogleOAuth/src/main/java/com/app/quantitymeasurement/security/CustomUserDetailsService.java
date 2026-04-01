package com.app.quantitymeasurement.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.jwt.JwtAuthenticationFilter;

/**
 * CustomUserDetailsService
 *
 * Spring Security {@link UserDetailsService} implementation that loads a user
 * record from the database by email address and wraps it in a
 * {@link UserPrincipal} for use by the authentication infrastructure.
 *
 * <p><b>Integration points:</b></p>
 * <ul>
 *   <li><b>Local login</b> — {@code DaoAuthenticationProvider} calls
 *       {@link #loadUserByUsername(String)} after the client POSTs to
 *       {@code /api/v1/auth/login}. The returned {@link UserDetails} is used
 *       to verify the supplied password against the stored BCrypt hash.</li>
 *   <li><b>JWT filter</b> — {@link JwtAuthenticationFilter} calls
 *       {@link #loadUserByUsername(String)} on every request that carries a
 *       valid JWT, to confirm the user still exists in the database and to
 *       re-read the current role and enabled status.</li>
 * </ul>
 *
 * <p><b>Transaction:</b> the {@code @Transactional(readOnly = true)} annotation
 * ensures that the JPA session used by {@link UserRepository} is properly bounded
 * and that the query is executed in a read-only transaction, allowing the
 * connection pool to route it to a read replica if one is configured.</p>
 *
 * @author Abhishek Puri Goswami
 * @version 18.0
 * @since 18.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
     * Loads a user by their email address.
     *
     * <p>Spring Security calls this method with the value that was passed as the
     * "username" field — in this application, that is always an email address.
     * The method wraps the found entity in a {@link UserPrincipal} which
     * implements both {@link UserDetails} and
     * {@link org.springframework.security.oauth2.core.user.OAuth2User}.</p>
     *
     * @param email the email address to look up; must not be {@code null}
     * @return a fully populated {@link UserPrincipal} ready for authentication
     * @throws UsernameNotFoundException if no user with the given email exists
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*
         * Query the database for a User with the matching email.
         * If none is found, throw UsernameNotFoundException — Spring Security
         * catches this internally and converts it to an AuthenticationException,
         * which ultimately produces a 401 Unauthorized response.
         */
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email));

        /*
         * Wrap the entity in UserPrincipal (local auth path — no OAuth2 attributes).
         */
        return UserPrincipal.create(user);
    }

    /**
     * Loads a user by their database primary key.
     *
     * <p>This overload is used internally (e.g., by service methods that have
     * already resolved the user ID from a JWT claim or a related entity) to avoid
     * a second email-based query when the ID is already known.</p>
     *
     * @param id the user's primary key
     * @return a fully populated {@link UserPrincipal}
     * @throws UsernameNotFoundException if no user with the given ID exists
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with id: " + id));

        return UserPrincipal.create(user);
    }
}
