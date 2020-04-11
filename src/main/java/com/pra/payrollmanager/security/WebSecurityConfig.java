package com.pra.payrollmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pra.payrollmanager.security.authentication.jwt.JwtAuthenticationEntryPoint;
import com.pra.payrollmanager.security.authentication.jwt.JwtRequestFilter;
import com.pra.payrollmanager.websocket.config.WebSocketConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// return new PasswordEncoder() {
		//
		// @Override
		// public String encode(CharSequence arg0) {
		// return arg0.toString();
		// }
		//
		// @Override
		// public boolean matches(CharSequence arg0, String arg1) {
		// return arg0.toString().equals(arg1);
		// }App started
		//
		// };
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests()
				.antMatchers("/auth/token", "/auth/token/refresh", WebSocketConfig.WS_ENDPOINT_PREFIX)
				.permitAll()
				// all other requests need to be authenticated
				.anyRequest()
				.authenticated()
				.and()
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/",
				"/v2/api-docs", // swagger
				"/webjars/**", // swagger-ui webjars
				"/swagger-resources/**", // swagger-ui resources
				"/configuration/**", // swagger configuration
				"/*.html",
				"/favicon.ico",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js");
	}
}