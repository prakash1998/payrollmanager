package com.pra.payrollmanager.security.authentication.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;
import com.pra.payrollmanager.user.root.permissions.endpoint.EndpointPermission;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.user.root.permissions.security.SecurityPermission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SecurityCompany extends BaseAuditDAO<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3356823150761471502L;

	@Id
	private String id;
	private String tablePrefix;
	@Builder.Default
	private Boolean enabled = true;
	@Builder.Default
	private Boolean accountLocked = false;
	@Builder.Default
	private Boolean accountExpired = false;
	@Builder.Default
	private Set<Integer> permissions = new HashSet<>();
	@Builder.Default
	private Set<Integer> endpointPermissions = new HashSet<>();
	@Builder.Default
	private Map<Integer, Set<ApiFeatures>> resourceFeatures = new HashMap<>();

	public boolean hasAccessTo(SecurityPermission permission) {
		return permissions.contains(permission.getNumericId());
	}

	public boolean hasAccessTo(EndpointPermission permission) {
		return endpointPermissions.contains(permission.getNumericId());
	}

	public boolean hasAccessTo(FeaturePermission permission, ApiFeatures service) {
		Integer apiId = permission.getNumericId();
		Set<ApiFeatures> allowedServices = resourceFeatures.get(apiId);
		if (allowedServices == null)
			return false;
		return allowedServices.contains(service);
	}

	public static class SecurityCompanyBuilder {
		private String id;

		public SecurityCompanyBuilder id(String id) {
			this.id = id;
			this.tablePrefix = id.trim().toUpperCase() + "_";
			return this;
		}
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

}