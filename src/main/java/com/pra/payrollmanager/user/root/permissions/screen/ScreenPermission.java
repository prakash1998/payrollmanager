package com.pra.payrollmanager.user.root.permissions.screen;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAO;

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
public class ScreenPermission extends BaseAuditDAO<String> {

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

//	@Override
//	public ScreenPermissionDTO toPlainDTO() {
//		return ScreenPermissionDTO.builder()
//				.id(id)
//				.display(display)
//				.category(category)
//				.description(description)
//				.build();
//	}
}
