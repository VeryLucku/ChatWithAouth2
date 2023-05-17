package com.edu.authservice;

import com.edu.authservice.securityConfig.RegisteredClientProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(RegisteredClientRepository registeredClientRepository,
										PasswordEncoder passwordEncoder,
										RegisteredClientProperties registeredClientProperties) {
		return args -> update(registeredClientRepository, passwordEncoder, registeredClientProperties);
	}

	private void update(RegisteredClientRepository registeredClientRepository, PasswordEncoder passwordEncoder, RegisteredClientProperties registeredClientProperties) {
		if (registeredClientRepository.findByClientId(registeredClientProperties.getClientId()) == null) {
			RegisteredClient registeredClient = RegisteredClient.withId(registeredClientProperties.getClientId())
					.clientId(registeredClientProperties.getClientId())
					.clientSecret(passwordEncoder.encode(registeredClientProperties.getClientSecret()))
					.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
					.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
					.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
					.redirectUri(registeredClientProperties.getRedirectUri())
					.scope(OidcScopes.OPENID)
					.scope(OidcScopes.PROFILE)
					.scopes((u) -> u.addAll(registeredClientProperties.getScopes()))
					.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
					.tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(10)).build())
					.build();
			registeredClientRepository.save(registeredClient);
		}
	}
}
