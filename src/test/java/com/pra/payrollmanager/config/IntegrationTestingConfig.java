package com.pra.payrollmanager.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;

@TestConfiguration
@Profile("unit-test")
public class IntegrationTestingConfig {

	@Bean
    @Primary
    UserDetailsService testUserDetailsService() {
      return new MockUserDetailService();
    }
	
	

}
