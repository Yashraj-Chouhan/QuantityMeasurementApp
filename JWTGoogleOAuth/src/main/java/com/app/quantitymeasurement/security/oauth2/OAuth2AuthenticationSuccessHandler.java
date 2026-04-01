package com.app.quantitymeasurement.security.oauth2;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.security.UserPrincipal;
import com.app.quantitymeasurement.security.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * OAuth2AuthenticationSuccessHandler
 *
 * Invoked by Spring Security immediately after a successful Google OAuth2
 * login. Its sole responsibility is to:
 * <ol>
 *   <li>Extract the authenticated {@link UserPrincipal} from the
 *       {@link Authentication} object.</li>
 *   <li>Generate a short-lived JWT via {@link JwtTokenProvider}.</li>
 *   <li>Redirect the browser to the configured frontend URL with the JWT
 *       appended as a query parameter so the client-side application can
 *       store it and attach it to subsequent API calls.</li>
 * </ol>
 *
 * <p><b>Redirect target:</b></p>
 * <pre>
 * ${app.oauth2.redirect-uri}?token=&lt;jwt&gt;
 * </pre>
 *
 * <p>For example, if the frontend runs at {@code http://localhost:3000} and the
 * property is {@code app.oauth2.redirect-uri=http://localhost:3000/oauth2/callback},
 * the redirect will be:</p>
 * <pre>
 * http://localhost:3000/oauth2/callback?token=eyJhbGciOiJIUzI1NiJ9...
 * </pre>
 *
 * <p><b>Security consideration:</b> passing the JWT as a query parameter is
 * convenient for SPAs but means the token may appear in server logs and browser
 * history. For higher-security deployments, consider using a short-lived
 * one-time code and exchanging it for a token via a second POST, or switching
 * to HTTP-only cookies. UC-18 uses the query-parameter approach for simplicity.</p>
 *
 * <p>Extends {@link SimpleUrlAuthenticationSuccessHandler} to inherit the default
 * redirect logic and override only the parts specific to JWT issuance.</p>
 *
 * @author Abhishek Puri Goswami
 * @version 18.0
 * @since 18.0
 */
@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /*
     * -------------------------------------------------------------------------
     * Dependencies and configuration
     * -------------------------------------------------------------------------
     */

	private final JwtTokenProvider jwtTokenProvider;
	private final String redirectUri;

    /**
     * The frontend URL to redirect to after a successful OAuth2 login.
     * Configured via {@code app.oauth2.redirect-uri} in application.properties.
     * Defaults to {@code http://localhost:8080/swagger-ui.html} for local
     * development (so Swagger can be tested without a separate frontend).
     */
	
	public OAuth2AuthenticationSuccessHandler(
	        JwtTokenProvider jwtTokenProvider,
	        @Value("${app.oauth2.redirect-uri:http://localhost:3000/oauth2/callback}")
	        String redirectUri) {

	    this.jwtTokenProvider = jwtTokenProvider;
	    this.redirectUri = redirectUri;
	}
	

    /*
     * -------------------------------------------------------------------------
     * Handler logic
     * -------------------------------------------------------------------------
     */

    /**
     * Processes a successful OAuth2 authentication event.
     *
     * <p>Steps performed:</p>
     * <ol>
     *   <li>Cast the principal to {@link UserPrincipal}.</li>
     *   <li>Build a JWT from the user's email and role using the
     *       {@code generateTokenFromEmail} overload (no Authentication object
     *       is available at this point — Spring Security's internal flow passes
     *       the principal directly).</li>
     *   <li>Build the target redirect URL with the JWT as a query parameter.</li>
     *   <li>Issue an HTTP 302 redirect response.</li>
     * </ol>
     *
     * @param request        the current HTTP request
     * @param response       the current HTTP response
     * @param authentication the fully authenticated OAuth2 principal
     * @throws IOException if the redirect fails
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest  request,
                                        HttpServletResponse response,
                                        Authentication      authentication) throws IOException {
        /*
         * Step 1 — extract the UserPrincipal from the Authentication object.
         * CustomOAuth2UserService.loadUser() returns a UserPrincipal, so this
         * cast is always safe in the OAuth2 flow.
         */
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        /*
         * Step 2 — collect the user's role authority string (e.g., "ROLE_USER").
         */
        String roleAuthority = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        /*
         * Step 3 — generate a signed JWT for the authenticated Google user.
         * We use the email-based overload because we don't have an
         * Authentication token created by AuthenticationManager here.
         */
        String token = jwtTokenProvider.generateTokenFromEmail(user.getEmail(), roleAuthority);

        log.info("OAuth2 login successful for: " + user.getEmail()
                    + " — issuing JWT and redirecting to frontend.");

        /*
         * Step 4 — build the redirect URL:
         * <redirectUri>?token=<jwt>
         */
        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .build()
                .toUriString();

        /*
         * Step 5 — perform the HTTP redirect.
         * getRedirectStrategy() is inherited from SimpleUrlAuthenticationSuccessHandler
         * and returns a DefaultRedirectStrategy that handles both absolute and
         * relative target URLs.
         */
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
