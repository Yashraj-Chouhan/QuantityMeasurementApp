package com.app.quantitymeasurement.security.jwt;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JwtAccessDeniedHandler
 *
 * Implements Spring Security's {@link AccessDeniedHandler} to handle requests
 * from authenticated users who lack sufficient authority (role) to access a
 * protected endpoint.
 *
 * <p><b>When is this handler invoked?</b></p>
 * <ul>
 *   <li>A user with role {@code USER} attempts to access an {@code ADMIN}-only
 *       endpoint (e.g., {@code GET /api/v1/quantities/history/errored}).</li>
 *   <li>Any authenticated request that fails a {@code hasRole()} or
 *       {@code hasAuthority()} check configured in {@code SecurityConfig}.</li>
 * </ul>
 *
 * <p>This is distinct from {@link JwtAuthenticationEntryPoint}, which handles
 * <em>unauthenticated</em> requests (401). This handler handles requests that
 * are authenticated but <em>not authorised</em> (403).</p>
 *
 * <p><b>Example response body:</b></p>
 * <pre>
 * HTTP/1.1 403 Forbidden
 * Content-Type: application/json
 *
 * {
 *   "timestamp": "2024-01-01T12:00:00",
 *   "status":    403,
 *   "error":     "Forbidden",
 *   "message":   "Access is denied",
 *   "path":      "/api/v1/quantities/history/errored"
 * }
 * </pre>
 *
 * @author Abhishek Puri Goswami
 * @version 18.0
 * @since 18.0
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /** Jackson mapper for serialising the error response to JSON. */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Called by Spring Security when an authenticated user attempts to access
     * a resource they are not authorised to use.
     *
     * <p>Writes a {@code 403 Forbidden} JSON response directly to the HTTP
     * response output stream, bypassing MVC dispatch.</p>
     *
     * @param request               the request that triggered the access denial
     * @param response              the response to write the 403 body to
     * @param accessDeniedException the exception that describes why access was denied
     * @throws IOException if writing to the response output stream fails
     */
    @Override
    public void handle(HttpServletRequest    request,
                       HttpServletResponse   response,
                       AccessDeniedException accessDeniedException) throws IOException {

        log.warn("Access denied for " + request.getRequestURI()
                       + " — " + accessDeniedException.getMessage());

        /*
         * Build the structured error response body.
         * LinkedHashMap preserves insertion order for a predictable JSON key sequence.
         */
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status",    HttpServletResponse.SC_FORBIDDEN);
        body.put("error",     "Forbidden");
        body.put("message",   accessDeniedException.getMessage());
        body.put("path",      request.getRequestURI());

        /*
         * Set response headers and status before writing the body.
         */
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
