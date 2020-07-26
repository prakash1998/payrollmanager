package com.pra.payrollmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
@ComponentScan(basePackages = "com.pra.payrollmanager",
		excludeFilters = {
				@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.pra.payrollmanager.restaurant.*")
		})
public class EnterpriseApplication {

	public static void main(String[] args) {
		log.debug("App started");
		SpringApplication.run(EnterpriseApplication.class, args);
	}

}
