package org.fbertos.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("org.fbertos.persistence.model")
@ComponentScan(basePackages={"org.fbertos"})
@EnableJpaRepositories("org.fbertos.persistence.dao")
public class ApplicationRestService {

 public static void main(String[] args) {
  SpringApplication.run(ApplicationRestService.class, args);
 }
}