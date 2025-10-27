package com.fallensakura.util;

import org.springframework.util.DigestUtils;

public class PasswordUtil {
    public static String encrypt(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public static boolean matches(String raw, String encrypted) {
        return encrypt(raw).equals(encrypted);
    }
}

