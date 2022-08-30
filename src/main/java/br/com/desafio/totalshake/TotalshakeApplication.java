package br.com.desafio.totalshake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TotalshakeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TotalshakeApplication.class, args);
	}

}