package com.edu.chatapi.Helpers;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDExtractor {

    public static final String SWAGGER_BASE_UUID_REGEX = "\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}";

    public static UUID extract(String input) {
        Pattern pairRegex = Pattern.compile(SWAGGER_BASE_UUID_REGEX);
        Matcher matcher = pairRegex.matcher(input);
        matcher.find();
        return UUID.fromString(matcher.group(0));
    }
}
