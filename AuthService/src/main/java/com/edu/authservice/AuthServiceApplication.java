package com.edu.authservice;

import com.edu.authservice.Model.UserData;
import com.edu.authservice.Persistance.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}


	@Bean
	public ApplicationRunner dataLoader(
			UserRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByUsername("admin") == null) {
				repo.save(
						new UserData(UUID.randomUUID(),"admin", encoder.encode("admin"), "ROLE_ADMIN"));
			}
		};
	}
}
