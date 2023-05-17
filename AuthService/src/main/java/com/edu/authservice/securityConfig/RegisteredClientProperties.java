package com.edu.authservice.securityConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;


@ConfigurationProperties(prefix = "client")
@Component
@Data
public class RegisteredClientProperties {

    private UUID id;
    private String clientId;
    private String clientSecret;
    private Set<String> scopes;
    private String redirectUri;
}
