package com.pra.payrollmanager.security.authorization;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserPermissionService;
import com.pra.payrollmanager.user.root.company.CompanyDetailsDTO;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermission;
import com.pra.payrollmanager.user.root.permissions.security.SecurityPermission;

@Service
public class AuthorityService {

	@Value("${auth.god_mode}")
	private boolean GOD_MODE;

	public boolean inGodMode() {
		return GOD_MODE;
	}

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

	public void validateEndpointPermission(EndpointPermission permission) {
		if (inGodMode())
			return;

		SecurityUser user = this.getSecurityUser();
		SecurityCompany company = this.getSecurityCompany();

		if (!company.hasAccessTo(permission)) {
			throw new AuthorizationServiceException(
					"Unauthorized Access : [" + permission.getDisplay() + "] is not assigned to company");
		}

		if (user.getUsername().equals(CompanyDetailsDTO.SUPER_USER_NAME))
			return;

		Set<Integer> userPermissions = securityUserPermissionService.loadEndpointPermissionsForUserId(user.getUserId());

		if (!userPermissions.contains(permission.getNumericId())) {
			throw new AuthorizationServiceException(
					"Unauthorized Access :  [" + permission.getDisplay() + "] is not assigned");
		}
	}

	public void validatePermissions(SecurityPermission... permissions) {

		List<SecurityPermission> fullPermissions = Stream.of(permissions)
				.map(p -> SecurityPermissions.universalSecurityPermissionMap.get(p.getNumericId()))
				.collect(Collectors.toList());

		if (inGodMode()) {
			return;
		}

		SecurityUser user = this.getSecurityUser();
		SecurityCompany company = this.getSecurityCompany();

		boolean companyAuthorized = false;
		for (SecurityPermission permission : fullPermissions) {
			if (company.hasAccessTo(permission)) {
				companyAuthorized = true;
				break;
			}
		}

		if (!companyAuthorized) {
			StringBuilder apiPermissions = new StringBuilder();
			for (SecurityPermission permission : fullPermissions) {
				apiPermissions.append(permission.getId()).append(",");
			}
			throw new AuthorizationServiceException(
					"Unauthorized Access : None of [" + apiPermissions.toString() + "] is assigned to company");
		}

		if (user.getUsername().equals(CompanyDetailsDTO.SUPER_USER_NAME))
			return;

		Set<Integer> userPermissions = securityUserPermissionService.loadPermissionsForUserId(user.getUserId());

		boolean userAuthorized = false;
		for (SecurityPermission permission : fullPermissions) {
			if (userPermissions.contains(permission.getNumericId())) {
				userAuthorized = true;
				break;
			}
		}

		if (!userAuthorized) {
			StringBuilder apiPermissions = new StringBuilder();
			for (SecurityPermission permission : fullPermissions) {
				apiPermissions.append(permission.getId()).append(",");
			}
			throw new AuthorizationServiceException(
					"Unauthorized Access : None of [" + apiPermissions.toString() + "] is assigned");
		}
	}

	public SecurityUser getSecurityUser() {
		return (SecurityUser) this.getAuthentication().getPrincipal();
	}

	public SecurityCompany getSecurityCompany() {
		return this.getSecurityUser().getCompany();
	}

	public String getUserId() {
		return this.getSecurityUser().getUserId();
	}

	public String getUserName() {
		return this.getSecurityUser().getUsername();
	}

	public String getCompanyTablePrefix() {
		return this.getSecurityCompany().getTablePrefix();
	}

}
