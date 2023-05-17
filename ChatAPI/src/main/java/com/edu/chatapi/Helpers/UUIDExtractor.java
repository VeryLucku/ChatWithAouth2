package com.edu.chatapi.Helpers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UUIDExtractor {

    public static final String SWAGGER_BASE_UUID_REGEX = "\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}";

    public Optional<UUID> extract(HttpServletRequest request) {
        String input = request.getServletPath();
        Pattern pairRegex = Pattern.compile(SWAGGER_BASE_UUID_REGEX);
        Matcher matcher = pairRegex.matcher(input);
        boolean found = matcher.find();

        UUID id;
        if (found) {
            id = UUID.fromString(matcher.group(0));
        } else {
            try {
                id = UUID.fromString(request.getParameter("chatId"));
            } catch (IllegalArgumentException e) {
                id = null;
            }
        }

        return id == null ?
                Optional.empty() :
                Optional.of(id);
    }
}
