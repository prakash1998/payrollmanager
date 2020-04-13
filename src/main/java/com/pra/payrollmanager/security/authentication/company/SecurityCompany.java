package com.pra.payrollmanager.security.authentication.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.security.authorization.permission.SecurityPermission;
import com.pra.payrollmanager.security.authorization.permission.api.ApiPermission;
import com.pra.payrollmanager.security.authorization.permission.api.ApiServices;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class SecurityCompany extends BaseAuditDAO<String> {

	@Id
	private String id;
	private String tablePrefix;
	@Builder.Default
	private boolean companyEnabled = true;
	@Builder.Default
	private boolean accountLocked = false;
	@Builder.Default
	private boolean accountExpired = false;
	@Builder.Default
	private Set<Integer> permissions = new HashSet<>();
	@Builder.Default
	private Map<Integer, Set<ApiServices>> apiPermissions = new HashMap<>();

	public boolean hasAccessTo(SecurityPermission permission) {
		return permissions.contains(permission.getNumericId());
	}

	public boolean hasAccessTo(ApiPermission permission, ApiServices service) {
		Integer apiId = permission.getNumericId();
		Set<ApiServices> allowedServices = apiPermissions.get(apiId);
		if(allowedServices == null)
			return false;
		return allowedServices.contains(service);
	}

	public static class SecurityCompanyBuilder {
		private String id;

		public SecurityCompanyBuilder id(String id) {
			this.id = id;
			this.tablePrefix = id.toUpperCase() + "_";
			return this;
		}
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

}