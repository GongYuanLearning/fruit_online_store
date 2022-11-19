package com.lzj.fruit.util;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ServiceUtil {
    private static String USER_NAME_PATTERN = "[a-zA-Z0-9_]+";

    private ServiceUtil() {}

    public static String decode(String value) {
        if (Objects.nonNull(value) && !value.isEmpty()) {
            return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        return value;
    }

    public static boolean isValidUsername(String value) {
        return Objects.nonNull(value) && value.matches(USER_NAME_PATTERN);
    }

    public static boolean notNullOrEmpty(String str) {
        return Objects.nonNull(str) &&
                !str.trim().isEmpty();
    }
}

