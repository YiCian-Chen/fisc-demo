package edu.tku.db.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "edu.tku.db")
@EntityScan(basePackages = "edu.tku.db")
public class DatabaseConfiguration {
}
