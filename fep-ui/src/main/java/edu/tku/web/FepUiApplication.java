package edu.tku.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication( scanBasePackages = { "edu.tku" })
@EntityScan
@EnableJpaRepositories
public class FepUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FepUiApplication.class, args);
	}

}
