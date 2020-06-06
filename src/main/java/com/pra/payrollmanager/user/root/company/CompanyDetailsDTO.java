package com.pra.payrollmanager.user.root.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "Restaurant")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CompanyDetailsDTO extends BaseAuditDTO<CompanyDetailsDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6414862975908609314L;

	public static final String SUPER_USER_NAME = "su";

	@NotNull
	@Size(min = 3, max = 24, message = "Company ID length must be between {min} and {max} charaters.")
	@Pattern(regexp = "^[^-]*$", message = "company ID should not contain '-'.")
	private String id;
	private String name;
	private String address;
	private String superUserPassword;

	@Builder.Default
	private Set<Integer> endpoints = new HashSet<>();

	@Builder.Default
	private Set<Integer> permissions = new HashSet<>();

	@Builder.Default
	private Map<Integer, Set<ApiFeatures>> resourceFeatures = new HashMap<>();

	@Builder.Default
	private Set<String> screenIds = new HashSet<>();
	
	@Override
	public CompanyDetailsDAO toPlainDAO() {
		return CompanyDetailsDAO.builder()
				.id(id)
				.name(name)
				.address(address)
				.screenIds(screenIds)
				.build();
	}

	public SecurityCompany toSecurityCompany() {
		return SecurityCompany.builder()
				.id(id)
				.permissions(permissions)
				.endpointPermissions(endpoints)
				.resourceFeatures(resourceFeatures)
				.build();
	}

	public SecurityUser toSuperUser() {
		return SecurityUser.builder()
				.username(SUPER_USER_NAME)
				.password(superUserPassword)
				.build();
	}

}
