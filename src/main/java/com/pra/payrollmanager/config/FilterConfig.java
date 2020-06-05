package com.pra.payrollmanager.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pra.payrollmanager.filter.JwtRequestFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<JwtRequestFilter> jwtFilter(JwtRequestFilter filter){
		FilterRegistrationBean<JwtRequestFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled(false);
		return registration;
	}
}
