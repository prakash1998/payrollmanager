package com.pra.payrollmanager.admin.company;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.pra.payrollmanager.base.BaseAuditDTO;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CompanyDetailsDTO extends BaseAuditDTO<CompanyDetailsDAO> {

	public static final String SUPER_USER_NAME = "su";

	@NotNull
	@Size(min = 3, max = 24, message = "Company ID length must be between {min} and {max} charaters.")
	@Pattern(regexp = "^[^-]*$", message = "company ID should not contain '-'.")
	private String companyId;
	private String companyName;
	private String address;
	private String superUserPassword;

	@Builder.Default
	private Set<Integer> permissions = new HashSet<>();

	@Override
	public CompanyDetailsDAO toDAO() {
		return CompanyDetailsDAO.builder()
				.id(companyId)
				.name(companyName)
				.address(address)
				.build();
	}

	public SecurityCompany toSecurityCompany() {
		return SecurityCompany.builder()
				.id(companyId)
				.permissions(permissions)
				.build();
	}

	public SecurityUser toSecurityUser() {
		return SecurityUser.builder()
				.username(SUPER_USER_NAME)
				.password(superUserPassword)
				.build();
	}

}
