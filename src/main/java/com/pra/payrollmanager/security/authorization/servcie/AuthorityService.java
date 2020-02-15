package com.pra.payrollmanager.security.authorization.servcie;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authentication.dao.SecurityUser;
import com.pra.payrollmanager.security.authorization.dao.SecurityPermission;

@Service
public class AuthorityService {

	@Value("${auth.god_mode}")
	private boolean GOD_MODE;

	public void validatePermissions(SecurityPermission... permissions) {

		if (GOD_MODE) {
			return;
		}

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (auth == null) {
			throw new AuthorizationServiceException("No Authentication Provided");
		}

		SecurityUser user = (SecurityUser) auth.getPrincipal();

		boolean authorized = false;

		for (int i = 0; i < permissions.length; i++) {
			if (user.hasAccessTo((permissions[i]))) {
				authorized = true;
				break;
			}
		}

		if (!authorized) {
			StringBuilder apiPermissions = new StringBuilder();
			for (int i = 0; i < permissions.length; i++) {
				apiPermissions.append(permissions[i].getId()).append(",");
			}
			throw new AuthorizationServiceException(
					"Unauthorized Access : None of [" + apiPermissions.toString() + "] is assigned");
		}
	}

	public String getUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
