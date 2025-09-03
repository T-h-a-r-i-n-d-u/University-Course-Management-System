
package com.ucms.ucmsapi.common;

import org.springframework.security.core.Authentication;

public final class AuthUtil {
    private AuthUtil() {}

    public static boolean hasRole(Authentication auth, String role) {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
    public static boolean isLecturerOrAdmin(Authentication auth) {
        return hasRole(auth, "LECTURER") || hasRole(auth, "ADMIN");
    }
}
