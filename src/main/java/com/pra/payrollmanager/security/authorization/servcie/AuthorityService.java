package com.pra.payrollmanager.security.authorization.servcie;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authorization.dao.SecurityPermission;

@Service
public class AuthorityService {

	public void validatePermissions(SecurityPermission ... permissions) {
		SimpleGrantedAuthority [] authorities = 
				SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray(new SimpleGrantedAuthority[0]);
		boolean authorized = false;

		StringBuilder permissionDenied = new StringBuilder();
		for(int i = 0 ; i<  permissions.length; i++) {
			for(int j = 0 ; j <authorities.length ; j++) {
				if(permissions[i].getId().equals(authorities[j].getAuthority())) {
					authorized = true;
					break;
				}
			}
			permissionDenied.append(permissions[i].getId()).append(",");
		}
		if(!authorized)
			throw new AuthorizationServiceException("Unauthorized Access : None of ["+permissionDenied.toString()+"] is assigned");
	}

	public String getUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
