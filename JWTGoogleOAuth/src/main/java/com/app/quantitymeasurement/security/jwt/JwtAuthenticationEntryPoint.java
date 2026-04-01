package com.app.quantitymeasurement.security.jwt;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JwtAuthenticationEntryPoint
 *
 * Implements Spring Security's {@link AuthenticationEntryPoint} to handle
 * requests that reach a protected endpoint without a valid JWT (or with no
 * JWT at all).
 *
 * <p>Spring Security invokes this component when:</p>
 * <ul>
 *   <li>An unauthenticated request (no {@code Authorization} header, or an
 *       expired/invalid JWT) attempts to access a {@code .authenticated()}
 *       endpoint.</li>
 *   <li>An authenticated request attempts to access a resource that requires
 *       a specific role — in that case {@link JwtAccessDeniedHandler} is
 *       invoked instead (403 Forbidden).</li>
 * </ul>
 *
 * <p>Without this component, Spring Security would return a redirect to a
 * login page, which is inappropriate for a stateless REST API. This entry
 * point short-circuits that behaviour and returns a structured JSON
 * {@code 401 Unauthorized} response instead.</p>
 *
 * <p><b>Example response body:</b></p>
 * <pre>
 * HTTP/1.1 401 Unauthorized
 * Content-Type: application/json
 *
 * {
 *   "timestamp": "2024-01-01T12:00:00",
 *   "status":    401,
 *   "error":     "Unauthorized",
 *   "message":   "Full authentication is required to access this resource",
 *   "path":      "/api/v1/quantities/add"
 * }
 * </pre>
 *
 * @author Abhishek Puri Goswami
 * @version 18.0
 * @since 18.0
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /** Jackson mapper for serialising the error response to JSON. */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Called by Spring Security when an unauthenticated request reaches a
     * protected endpoint. Writes a {@code 401 Unauthorized} JSON response
     * directly to the HTTP response, bypassing the standard MVC dispatch.
     *
     * <p>Because this method writes directly to the response output stream,
     * it must set the content type and character encoding before writing, and
     * must not call {@code response.sendRedirect()} or forward the request.</p>
     *
     * @param request       the request that triggered the authentication failure
     * @param response      the response to write the 401 body to
     * @param authException the exception that describes why authentication failed
     * @throws IOException if writing to the response output stream fails
     */
    @Override
    public void commence(HttpServletRequest        request,
                         HttpServletResponse       response,
                         AuthenticationException   authException) throws IOException {

        log.warn("Unauthorized request to " + request.getRequestURI()
                       + " — " + authException.getMessage());

        /*
         * Build the structured error response body.
         * LinkedHashMap preserves insertion order for a predictable JSON key sequence.
         */
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status",    HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error",     "Unauthorized");
        body.put("message",   authException.getMessage());
        body.put("path",      request.getRequestURI());

        /*
         * Set response headers before writing the body.
         * UTF-8 charset ensures special characters in error messages survive serialisation.
         */
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        /*
         * Serialise the body map to JSON and write it to the response output stream.
         */
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
