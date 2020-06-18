package com.pra.payrollmanager.config;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
// @Profile("dev")
public class SwaggerConfig {

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Base App - REST APIs")
				.description("Payroll Manager App.").termsOfServiceUrl("")
				.contact(new Contact("Prakash Dudhat", "",
						"dudhatprakash992@gmail.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("0.0.1")
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("Bearer", "Authorization", "header");
	}

	SecurityReference securityReference = SecurityReference.builder()
			.reference("Bearer")
			.scopes(new AuthorizationScope[0])
			.build();

	SecurityContext securityContext = SecurityContext.builder()
			.securityReferences(Arrays.asList(securityReference))
			.build();

	private Docket swaggerDocket(String groupName, String basePackage) {
		return new Docket(DocumentationType.SWAGGER_2)
				.directModelSubstitute(ObjectId.class, String.class)
				.groupName(groupName)
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()))
				.securityContexts(Arrays.asList(securityContext));
	}

	/**
	 * Group Security contains apis related to security / token
	 */
	@Bean
	public Docket swaggerSecurityApi() {
		return swaggerDocket("Security", "com.pra.payrollmanager.security");
	}

	// /**
	// * Group Admin contains operations related to administration
	// */
	// @Bean
	// public Docket swaggerAdminApi() {
	// return swaggerDocket("Admin", "com.pra.payrollmanager.admin");
	// }

	// /**
	// * Group User contains operations used by users
	// */
	// @Bean
	// public Docket swaggerUserApi() {
	// return swaggerDocket("User", "com.pra.payrollmanager.user");
	// }

	/**
	 * Group User contains operations used by restaurant
	 */
	@Bean
	public Docket swaggerRestaurantApi() {
		return swaggerDocket("Restaurant", "com.pra.payrollmanager.restaurant.hotel");
	}

	@Bean
	public Docket swaggerRestaurantAdmimApi() {
		return swaggerDocket("Admin", "com.pra.payrollmanager.restaurant.admin");
	}

}
