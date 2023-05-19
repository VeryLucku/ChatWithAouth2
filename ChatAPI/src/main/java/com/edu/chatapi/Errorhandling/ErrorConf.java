package com.edu.chatapi.Errorhandling;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ErrorConf {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new ApiErrorAttributes();
    }
}
