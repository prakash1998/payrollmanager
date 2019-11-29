package com.pra.payrollmanager.security.authorization;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authorization.dao.SecurityUser;


@Service
public class SecurityUserService implements UserDetailsService {

	public UserDetails loadUserByUsername(String userName) {
		return SecurityUser.builder()
				.username(userName)
				.password("password")
				.build();
	}
}
