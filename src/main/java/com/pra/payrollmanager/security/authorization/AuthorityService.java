package com.pra.payrollmanager.security.authorization;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.admin.company.CompanyDetailsDTO;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserPermissionService;
import com.pra.payrollmanager.security.authorization.permission.SecurityPermission;

@Service
public class AuthorityService {

	@Value("${auth.god_mode}")
	private boolean GOD_MODE;

	@Autowired
	SecurityUserPermissionService securityUserPermissionService;

	public boolean haveAuthentication() {
		return SecurityContextHolder.getContext()
				.getAuthentication() != null;
	}

	public Authentication getAuthentication() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth == null) {
			throw new AuthorizationServiceException("No Authentication Provided");
		}
		return auth;
	}

	public void validatePermissions(SecurityPermission... permissions) {

		if (GOD_MODE) {
			return;
		}

		SecurityUser user = this.getSecurityUser();
		SecurityCompany company = this.getSecurityCompany();

		boolean companyAuthorized = false;
		for (int i = 0; i < permissions.length; i++) {
			if (company.hasAccessTo((permissions[i]))) {
				companyAuthorized = true;
				break;
			}
		}

		if (!companyAuthorized) {
			StringBuilder apiPermissions = new StringBuilder();
			for (int i = 0; i < permissions.length; i++) {
				apiPermissions.append(permissions[i].getId()).append(",");
			}
			throw new AuthorizationServiceException(
					"Unauthorized Access : None of [" + apiPermissions.toString() + "] is assigned to company");
		}

		if (user.getUsername().equals(CompanyDetailsDTO.SUPER_USER_NAME))
			return;

		Set<Integer> userPermissions = securityUserPermissionService.loadPermissionsForUserId(user.getUserId());

		boolean userAuthorized = false;
		for (int i = 0; i < permissions.length; i++) {
			if (userPermissions.contains(permissions[i].getNumericId())) {
				userAuthorized = true;
				break;
			}
		}

		if (!userAuthorized) {
			StringBuilder apiPermissions = new StringBuilder();
			for (int i = 0; i < permissions.length; i++) {
				apiPermissions.append(permissions[i].getId()).append(",");
			}
			throw new AuthorizationServiceException(
					"Unauthorized Access : None of [" + apiPermissions.toString() + "] is assigned");
		}
	}

	public SecurityUser getSecurityUser() {
		return (SecurityUser) this.getAuthentication().getPrincipal();
	}

	public SecurityCompany getSecurityCompany() {
		return getSecurityUser().getCompany();
	}

	public String getUserId() {
		return getSecurityUser().getUserId();
	}

	public String getUserName() {
		return this.getAuthentication().getName();
	}

	public String getTablePrefix() {
		return this.getSecurityCompany().getTablePrefix();
	}

}
