package com.pra.payrollmanager.exception.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authorization.AuthorityService;

@Service
public class ExceptionInterceptor {
	
	@Autowired
	private AuthorityService authService;
	
	public void intercept(Exception e) {
		if(authService.inGodMode()) {
			e.printStackTrace();
		}
	}
}
