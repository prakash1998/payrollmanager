package com.pra.payrollmanager.user.root.permissions.security;

import javax.validation.constraints.NotNull;

import com.pra.payrollmanager.base.data.BaseAuditDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = false)
public class SecurityPermissionDTO extends BaseAuditDTO<SecurityPermission> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 75499830592266708L;
	
	@NotNull
	private Integer numericId;
	@NotNull
	private String id;
	private String display;
	private String category;
	private String description;

//	@Override
//	public SecurityPermission toPlainDAO() {
//		return SecurityPermission.builder()
//				.id(id)
//				.numericId(numericId)
//				.display(display)
//				.category(category)
//				.description(description)
//				.build();
//	}

}
