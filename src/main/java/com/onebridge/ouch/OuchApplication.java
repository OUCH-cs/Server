package com.onebridge.ouch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OuchApplication {

	public static void main(String[] args) {
		SpringApplication.run(OuchApplication.class, args);
	}

}
