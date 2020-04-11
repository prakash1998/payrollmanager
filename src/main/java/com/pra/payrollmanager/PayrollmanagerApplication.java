package com.pra.payrollmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCaching(proxyTargetClass = true)
@EnableScheduling
@EnableWebSecurity
@SpringBootApplication
public class PayrollmanagerApplication {
	

	public static void main(String[] args) {
		log.debug("App started");
		SpringApplication.run(PayrollmanagerApplication.class, args);
	}


}
