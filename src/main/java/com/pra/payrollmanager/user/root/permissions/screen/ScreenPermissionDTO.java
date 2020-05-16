package com.pra.payrollmanager.user.root.permissions.screen;

import javax.validation.constraints.NotNull;

import com.pra.payrollmanager.base.data.BaseAuditDTO;

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
public class ScreenPermissionDTO extends BaseAuditDTO<ScreenPermission> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 75499830592266708L;

	@NotNull
	private String id;
	private String display;
	private String category;
	private String description;

	@Override
	public ScreenPermission toPlainDAO() {
		return ScreenPermission.builder()
				.id(id)
				.display(display)
				.category(category)
				.description(description)
				.build();
	}

}
