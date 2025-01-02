package com.qoeka98.admin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // 이메일 형식 패턴
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static final String ALLOWED_DOMAIN = "@foodreview.com";

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        // 이메일 형식 검사
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }

        // 특정 도메인 검사
        return email.endsWith(ALLOWED_DOMAIN);
    }

}
