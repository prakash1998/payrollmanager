package com.pra.payrollmanager.security.authentication.company;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authorization.AuthorityService;

@Service
public class SecurityCompanyPermissionService {
	
	@Autowired
	AuthorityService authService;
	
	public Set<Integer> loadApiFeaturesForCompany(){
		return authService.getSecurityCompany().getResourceFeatures().keySet();
	}

}
