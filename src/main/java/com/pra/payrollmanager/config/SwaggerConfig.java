package com.pra.payrollmanager.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
//@Profile("dev")
public class SwaggerConfig {

	private ApiKey apiKey() {
		return new ApiKey("Bearer", "Authorization", "header");
	}

	/**
	 * Group Security contains apis related to security / token
	 */
	@Bean
	public Docket swaggerSecurityApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Security")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pra.payrollmanager.security"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()));
	}

	/**
	 * Group Admin contains operations related to administration
	 */
	@Bean
	public Docket swaggerAdminApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Admin")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pra.payrollmanager.admin"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()));
	}

	/**
	 * Group Admin contains operations related to administration
	 */
	@Bean
	public Docket swaggerUserApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("User")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pra.payrollmanager.user"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()));
	}

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

}
