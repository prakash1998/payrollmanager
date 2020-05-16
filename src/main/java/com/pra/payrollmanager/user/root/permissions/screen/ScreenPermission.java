package com.pra.payrollmanager.user.root.permissions.screen;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class ScreenPermission extends BaseAuditDAOWithDTO<String, ScreenPermissionDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3045137574424856418L;
	@Id
	private String id;
	private String display;
	private String category;
	private String description;

	public static ScreenPermission of(String id) {
		String category = id.split("__")[0];
		return ScreenPermission.builder()
				.id(id)
				.display(id.replace("__", "_").replace("_", " "))
				.category(category)
				.description("-")
				.build();
	}

	public static ScreenPermission of() {
		return ScreenPermission.builder().build();
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public ScreenPermissionDTO toPlainDTO() {
		return ScreenPermissionDTO.builder()
				.id(id)
				.display(display)
				.category(category)
				.description(description)
				.build();
	}
}
