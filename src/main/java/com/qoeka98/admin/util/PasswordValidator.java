package com.qoeka98.admin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final int MIN_LENGTH = 10;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{" + MIN_LENGTH + ",}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(String password) {
        if (password == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


}
