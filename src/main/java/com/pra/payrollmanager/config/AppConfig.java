package com.pra.payrollmanager.config;

import java.util.Arrays;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pra.payrollmanager.security.authorization.permissions.SecurityPermissions;
import com.pra.payrollmanager.security.authorization.repo.SecurityPermissionRepo;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@EnableSwagger2
@EnableMongoAuditing
public class AppConfig {

	@Autowired
	SecurityPermissionRepo securityPermissionRepo;

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

	/**
	 * Group BRS contains operations related to reservations and agency mangement
	 */
	@Bean
	public Docket swaggerBRSApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("BRS")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.starterkit.springboot.brs.controller.v1.api"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()));
	}

	/**
	 * Group User contains operations related to user mangement such as login/logout
	 */
	@Bean
	public Docket swaggerUserApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("User")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.starterkit.springboot.brs.config"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()));
	}

	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Bus Reservation System - REST APIs")
				.description("Spring Boot starter kit application.").termsOfServiceUrl("")
				.contact(new Contact("Arpit Khandelwal", "https://medium.com/the-resonant-web",
						"khandelwal.arpit@outlook.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("0.0.1")
				.build();
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	    mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    

	private ApiKey apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}
}
