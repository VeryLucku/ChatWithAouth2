package com.edu.chatapi.Errorhandling;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class ApiErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        var errorAttributes = super.getErrorAttributes(webRequest, options);

        var error = getError(webRequest);
        var status = errorAttributes.get("status");
        errorAttributes.clear();
        errorAttributes.put("status", status);
        if (error != null) {
            errorAttributes.put("error", error.getMessage());
        }

        return errorAttributes;
    }
}
