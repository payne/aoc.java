package org.mattpayne.aoc.java.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("org.mattpayne.aoc.java")
@EnableJpaRepositories("org.mattpayne.aoc.java")
@EnableTransactionManagement
public class DomainConfig {
}
