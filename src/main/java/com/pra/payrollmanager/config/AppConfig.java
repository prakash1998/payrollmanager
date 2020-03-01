package com.pra.payrollmanager.config;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pra.payrollmanager.security.authorization.SecurityPermissions;
import com.pra.payrollmanager.security.authorization.permission.SecurityPermissionDAL;

@Configuration
@EnableMongoAuditing
public class AppConfig {

	@Autowired
	SecurityPermissionDAL securityPermissionRepo;

	@Autowired
	JsonJacksonMapperService mapperService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
				.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
		return modelMapper;
		// https://github.com/modelmapper/modelmapper/issues/212
	}

	// auditing injection
	@Bean
	public AuditorAware<String> myAuditorProvider() {
		return () -> Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getName);
	}

	// on application load
	@Bean
	public SmartInitializingSingleton loadSecurityPermissionsToDatabase() {
		return () -> {
			SecurityPermissions.persistPermissionsIfNot(securityPermissionRepo);
		};
	}

	// json configuration
	@Bean
	public ObjectMapper objectMapper() {
		return mapperService.mapper();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
						.allowedHeaders("*");
			}
		};
	}

}
