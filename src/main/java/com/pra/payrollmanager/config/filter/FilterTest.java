package com.pra.payrollmanager.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;

//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class FilterTest implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("########## Initiating Custom filter ##########");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		log.info("Logging Request  {} : {}", request.getMethod(), request.getRequestURI());

		// call next filter in the filter chain
		filterChain.doFilter(request, response);
		

		log.info("Logging Response :{}", response);

		log.info("Logging Response :{}", response.getContentType());
	}

	@Override
	public void destroy() {
		// TODO: 7/4/18
	}
}