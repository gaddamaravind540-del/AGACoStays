package com.agacostays.auth.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordUtil {

    private PasswordUtil() {}

    public static boolean matches(PasswordEncoder encoder, String raw, String hash) {
        return encoder.matches(raw, hash);
    }
}
