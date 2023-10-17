package com.ptit.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan("com.ptit")
@EntityScan("com.ptit.model")
@EnableJpaRepositories("com.ptit.repository")
public class BookstoreApplication {

	public static void main(String[] args) { 
		SpringApplication.run(BookstoreApplication.class, args);
	}

}
