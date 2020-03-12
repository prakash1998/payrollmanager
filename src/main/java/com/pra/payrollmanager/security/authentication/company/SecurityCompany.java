package com.pra.payrollmanager.security.authentication.company;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.security.authorization.permission.SecurityPermission;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false )
public class SecurityCompany extends BaseAuditDAO<String> {

	@Id
	private String id;
	private String tablePostfix;
	@Builder.Default
	private boolean companyEnabled = true;
	@Builder.Default
	private boolean accountLocked = false;
	@Builder.Default
	private boolean accountExpired = false;
	@Builder.Default
	private Set<Integer> permissions = new HashSet<>();

	public boolean hasAccessTo(SecurityPermission permission) {
		return permissions.contains(permission.getNumericId());
	}

	public static class SecurityCompanyBuilder {
		private String id;

		public SecurityCompanyBuilder id(String id) {
			this.id = id;
			this.tablePostfix = "_" + id.toUpperCase();
			return this;
		}
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

}