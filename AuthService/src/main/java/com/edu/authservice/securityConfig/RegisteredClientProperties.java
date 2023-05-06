package com.edu.authservice.securityConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "client")
@Data
public class RegisteredClientProperties {

    private String clientId = "admin-client";
    private String clientSecret = "secret";
    private Set<String> scopes = Set.of("message.write", "message.read", "message.delete");
}
