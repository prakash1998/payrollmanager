package com.pra.payrollmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
// @EnableMongoAuditing
@EnableTransactionManagement
@Profile({ "dev", "prod" })
public class MongoConfig {

	@Autowired
	MongoDbFactory mongoDbFactory;
	@Autowired
	MongoMappingContext mongoMappingContext;

	@Bean
	public MappingMongoConverter mappingMongoConverter() {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		converter.setTypeMapper(new DefaultMongoTypeMapper("_c",mongoMappingContext));

		return converter;
	}

	@Bean
	MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	// auditing injection
	// @Bean
	// public AuditorAware<String> myAuditorProvider() {
	// return () -> Optional.ofNullable(SecurityContextHolder.getContext())
	// .map(SecurityContext::getAuthentication)
	// .filter(Authentication::isAuthenticated)
	// .map(Authentication::getName);
	// }
}
