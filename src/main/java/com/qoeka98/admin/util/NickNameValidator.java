package com.qoeka98.admin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NickNameValidator {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 20;
    private static final String NICKNAME_PATTERN = "^[가-힣a-zA-Z0-9]{" + MIN_LENGTH + "," + MAX_LENGTH + "}$";

    private static final Pattern pattern = Pattern.compile(NICKNAME_PATTERN);

    public static boolean isValid(String nickname) {
        if (nickname == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(nickname);
        return matcher.matches();
    }


}
