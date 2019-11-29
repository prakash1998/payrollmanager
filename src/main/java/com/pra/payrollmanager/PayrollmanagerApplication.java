package com.pra.payrollmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class PayrollmanagerApplication {

	public static void main(String[] args) {
		System.out.print("App started");
		SpringApplication.run(PayrollmanagerApplication.class, args);
	}

}
