package com.study.user.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(12);

    public static String encode(final String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Password must not be empty");
        }

        return ENCODER.encode(password);
    }

    public static boolean matches(final String password, final String hashedPassword) {
        if (StringUtils.isBlank(password)) {
            return false;
        }

        return ENCODER.matches(password, hashedPassword);
    }
}
