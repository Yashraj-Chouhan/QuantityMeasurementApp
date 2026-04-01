package com.app.quantitymeasurement.enums;

/**
 * Role
 *
 * Enumeration of the application-level security roles assigned to users.
 *
 * <p>Spring Security requires role names to be prefixed with {@code ROLE_} when
 * they are stored in the {@link org.springframework.security.core.GrantedAuthority}
 * collection. This enum stores the bare names (e.g., {@code USER}, {@code ADMIN});
 * the prefix is added automatically by
 * {@link org.springframework.security.core.authority.SimpleGrantedAuthority}
 * when the value is passed as {@code "ROLE_" + role.name()}.</p>
 *
 * <p>Role hierarchy:</p>
 * <ul>
 *   <li>{@code USER}  — standard authenticated user; can perform all quantity
 *       measurement operations (compare, convert, add, subtract, divide) and view
 *       their own history records.</li>
 *   <li>{@code ADMIN} — elevated privileges; can additionally view all errored
 *       operation records across all users.</li>
 * </ul>
 *
 * @author Abhishek Puri Goswami
 * @version 18.0
 * @since 18.0
 */
public enum Role {

    /**
     * Standard user role.
     * Granted to every account created via the local registration flow
     * or via Google OAuth2 login.
     */
    USER,

    /**
     * Administrator role.
     * Must be assigned manually in the database; it is never granted
     * automatically during registration or OAuth2 sign-in.
     */
    ADMIN
}
