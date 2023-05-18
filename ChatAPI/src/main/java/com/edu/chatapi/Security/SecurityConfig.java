package com.edu.chatapi.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/api/*/all")
                .hasAuthority("SCOPE_all")
                .requestMatchers(HttpMethod.POST, "/api/**")
                .hasAuthority("SCOPE_write")
                .requestMatchers(HttpMethod.GET, "/api/**")
                .hasAuthority("SCOPE_read")
                .requestMatchers(HttpMethod.DELETE, "/api/**")
                .hasAuthority("SCOPE_delete")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }
}
