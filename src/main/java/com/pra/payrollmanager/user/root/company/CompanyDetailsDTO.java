package com.pra.payrollmanager.user.root.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;
import com.pra.payrollmanager.validation.ValidationGroups;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel(value = "Restaurant")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
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
	private String description;
	private String address;
	
	@Builder.Default
	private Set<Integer> imgIds = new HashSet<>();
	
	private boolean locked;
	private Location location;

	private List<String> categories;
	
	@NotNull(groups = {ValidationGroups.onCreate.class})
	private String superUserPassword;

	@ApiModelProperty(hidden = true) 
	@Builder.Default
	private Set<Integer> endpoints = new HashSet<>();

	@ApiModelProperty(hidden = true) 
	@Builder.Default
	private Set<Integer> permissions = new HashSet<>();

	@ApiModelProperty(hidden = true) 
	@Builder.Default
	private Map<Integer, Set<ApiFeatures>> resourceFeatures = new HashMap<>();

	@ApiModelProperty(hidden = true) 
	@Builder.Default
	private Set<String> screenIds = new HashSet<>();
	
//	@Override
//	public CompanyDetailsDAO toPlainDAO() {
//		return CompanyDetailsDAO.builder()
//				.id(id)
//				.name(name)
//				.category(category)
//				.desc(description)
//				.address(address)
//				.screenIds(screenIds)
//				.location(location)
//				.build();
//	}

	public SecurityCompany toSecurityCompany() {
		return SecurityCompany.builder()
				.id(id)
				.permissions(permissions)
				.endpointPermissions(endpoints)
				.screenIds(screenIds)
				.resourceFeatures(resourceFeatures)
				.build()
				.copyAuditInfoFrom(this,SecurityCompany.class);
	}

	public SecurityUser toSuperUser() {
		return SecurityUser.builder()
				.username(SUPER_USER_NAME)
				.password(superUserPassword)
				.build()
				.copyAuditInfoFrom(this,SecurityUser.class);
	}

}
