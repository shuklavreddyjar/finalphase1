package com.kirana.finalphase1.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Returns the authenticated user's ID.
     * This is the MongoDB ObjectId (hex string) stored in JWT subject.
     */
    public static String getCurrentUserId() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated access");
        }

        return auth.getPrincipal().toString();
    }
}
