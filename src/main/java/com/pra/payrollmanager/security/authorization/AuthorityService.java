package com.pra.payrollmanager.security.authorization;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserPermissionService;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.Resource;
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

	public boolean hasAuthentication() {
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

	private void validateSecurityCompany(SecurityCompany company) {
		if (!company.getEnabled()) {
			throw new AuthorizationServiceException("Disabled :  " + company.getId() + " is disabled by Admin.");
		}
		if (company.getAccountLocked()) {
			throw new AuthorizationServiceException("Locked :  " + company.getId() + " is locked by Admin.");
		}
		if (company.getAccountExpired()) {
			throw new AuthorizationServiceException("Expired :  " + company.getId() + " is expired.");
		}
	}

	private void validateSecurityUser(SecurityUser user) {
		if (!user.isEnabled()) {
			throw new AuthorizationServiceException("Disabled :  " + user.getUsername() + " is disabled by Admin.");
		}
		if (user.getAccountLocked()) {
			throw new AuthorizationServiceException("Locked :  " + user.getUsername() + " is locked by Admin.");
		}
		if (user.getAccountExpired()) {
			throw new AuthorizationServiceException("Expired :  " + user.getUsername() + " is expired.");
		}
	}
	
	public void validateEndpointPermission(EndpointPermission permission) {
		if (inGodMode())
			return;

		SecurityCompany company = this.getSecurityCompany();
		validateSecurityCompany(company);
		SecurityUser user = this.getSecurityUser();
		validateSecurityUser(user);

		if (!company.hasAccessTo(permission)) {
			throw new AuthorizationServiceException(
					"Unauthorized Access : [" + permission.getDisplay() + "] is not assigned to company");
		}

		if (this.isSuperUser())
			return;

		Set<String> userPermissions = securityUserPermissionService.loadEndpointsForUser();

		if (!userPermissions.contains(permission.getId())) {
			throw new AuthorizationServiceException(
					"Unauthorized Access :  [" + permission.getDisplay() + "] is not assigned");
		}
	}

	public void validatePermissions(SecurityPermission... permissions) {
		if (inGodMode()) {
			return;
		}

		SecurityCompany company = this.getSecurityCompany();
		SecurityUser user = this.getSecurityUser();

		List<SecurityPermission> fullPermissions = Stream.of(permissions)
				.map(p -> SecurityPermissions.universalSecurityPermissionMap.get(p.getNumericId()))
				.collect(Collectors.toList());

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

		Set<Integer> userPermissions = securityUserPermissionService.loadPermissionsForUser();

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
	
	public Set<ObjectId> getUserResourceIds() {
		return securityUserPermissionService.loadResourcesForUser();
	}

	public boolean hasAccessToResource(Resource resource) {
		return hasAccessToResourceId(resource.resourceId());
	}

	public boolean hasAccessToResourceId(ObjectId resourceId) {
		if (inGodMode()) {
			return true;
		}
		return getUserResourceIds().contains(resourceId);
	}

	public SecurityUser getSecurityUser() {
		return (SecurityUser) this.getAuthentication().getPrincipal();
	}

	public SecurityCompany getSecurityCompany() {
		return this.getSecurityUser().getCompany();
	}

	public String getUserId() {
		if (this.inGodMode() && !this.hasAuthentication()) {
			return "god-su";
		}

		return this.getSecurityUser().getUserId();
	}
	
	public boolean isSuperUser() {
		return this.getSecurityUser().getUsername().equals(CompanyDetailsDTO.SUPER_USER_NAME);
	}

	public String getUserName() {

		if (this.inGodMode() && !this.hasAuthentication()) {
			return "god";
		}

		return this.getSecurityUser().getUsername();
	}

	public String getCompanyTablePrefix() {
		return this.getSecurityCompany().getTablePrefix();
	}

}
