package com.pra.payrollmanager.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.MappingContextTypeInformationMapper;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.util.TypeInformation;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
// @EnableMongoAuditing
@EnableTransactionManagement
// @Profile({ "dev", "prod" })
public class MongoConfig {

	@Autowired
	private MongoDbFactory mongoDbFactory;
	@Autowired
	private MongoMappingContext mongoMappingContext;

	@Bean
	public MappingMongoConverter mappingMongoConverter() {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver,
				mongoMappingContext);
		DefaultMongoTypeMapper customTypeMapper = new DefaultMongoTypeMapper("_c", mongoMappingContext);
		converter.setTypeMapper(customTypeMapper);

		verifyConflictExplicitlyAsItIsNotDoingByItSelf();

		return converter;
	}

	public void verifyConflictExplicitlyAsItIsNotDoingByItSelf() {
		MappingContextTypeInformationMapper mapper = new MappingContextTypeInformationMapper(mongoMappingContext);

		List<String> typeAliasList = new ArrayList<>();

		for (PersistentEntity<?, ?> entity : mongoMappingContext.getPersistentEntities()) {
			TypeInformation<?> typeInfo = entity.getTypeInformation();
//			Class<?> type = typeInfo.getType();
			Object alias = entity.getTypeAlias().getValue();
			mapper.createAliasFor(typeInfo);
			if (alias != null) {
				typeAliasList.add(alias.toString());
			}
		}

		log.info("There are no conflicts in typeAlias of Entity.");
		Collections.sort(typeAliasList);
		log.info("Entity Alias List: " + typeAliasList);
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
