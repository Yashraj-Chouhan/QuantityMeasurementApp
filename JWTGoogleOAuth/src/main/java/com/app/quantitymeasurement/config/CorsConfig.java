package com.app.quantitymeasurement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * CorsConfig
 *
 * <p>Provides a {@link CorsConfigurationSource} bean consumed by Spring Security's
 * CORS filter. This keeps CORS configuration centralised and makes allowed origins
 * configurable per-environment through {@code application-*.properties}
 * (or OS environment variables in production).</p>
 *
 * <p><b>Default behaviour:</b></p>
 * <ul>
 *   <li>Allowed origins     — driven by {@code app.cors.allowed-origins} (comma-separated list)</li>
 *   <li>Allowed methods     — GET, POST, PUT, PATCH, DELETE, OPTIONS</li>
 *   <li>Allowed headers     — Authorization, Content-Type, Accept, X-Requested-With, Origin</li>
 *   <li>Exposed headers     — Authorization (so clients can read the JWT from response)</li>
 *   <li>Allow credentials   — {@code true} (required for cookies / Authorization header)</li>
 *   <li>Max age             — 3600 seconds (preflight cached for 1 hour)</li>
 *   <li>Applied to paths    — {@code /**} (all endpoints)</li>
 * </ul>
 *
 * @author Abhishek Puri Goswami
 * @version 19.0
 * @since 19.0
 */
@Configuration
public class CorsConfig {

    /**
     * Comma-separated list of origins allowed to make cross-origin requests.
     *
     * <p>Example values:
     * <pre>
     *   dev  : http://localhost:3000,http://localhost:5173
     *   prod : https://your-frontend.example.com
     * </pre>
     *
     * Defaults to {@code http://localhost:3000} if the property is not set,
     * which is the canonical React / Next.js dev-server port.
     * </p>
     */
    @Value("${app.cors.allowed-origins:http://localhost:3000}")
    private String[] allowedOrigins;

    /**
     * Builds and returns a {@link CorsConfigurationSource} that applies the
     * CORS policy declared on this class to every URL path.
     *
     * @return a fully configured {@link UrlBasedCorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origins allowed to call the API
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));

        // HTTP methods the browser is permitted to use
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Request headers the browser may send
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With",
                "Origin"
        ));

        // Response headers the frontend JavaScript code is allowed to read
        configuration.setExposedHeaders(List.of("Authorization"));

        // Required so the browser sends cookies / Authorization headers
        configuration.setAllowCredentials(true);

        // Cache the preflight response for 1 hour (reduces OPTIONS round-trips)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
