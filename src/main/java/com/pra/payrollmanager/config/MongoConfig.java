package com.pra.payrollmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableMongoAuditing
@EnableTransactionManagement
public class MongoConfig {

	@Bean
	MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	// auditing injection
//	@Bean
//	public AuditorAware<String> myAuditorProvider() {
//		return () -> Optional.ofNullable(SecurityContextHolder.getContext())
//				.map(SecurityContext::getAuthentication)
//				.filter(Authentication::isAuthenticated)
//				.map(Authentication::getName);
//	}
}