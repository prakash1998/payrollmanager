package com.pra.payrollmanager.config;

import com.pra.payrollmanager.security.authorization.AuthorityService;

public class AuthoritiyServiceForUnitTest extends AuthorityService{
	
	@Override
	public boolean inGodMode() {
		return true;
	};

}
