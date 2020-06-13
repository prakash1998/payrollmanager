package com.pra.payrollmanager.user.root.permissions.endpoint;

import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;
import com.pra.payrollmanager.base.data.BaseAuditDAO;

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
public class EndpointPermission extends BaseAuditDAO<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3045137574424856418L;
	@Id
	private String id;
	@NonNull
	private Integer numericId;

	private String display;
	private String category;
	private String description;

	public static EndpointPermission of(int numericId, String id) {
		String category = id.split("__")[0];
		return EndpointPermission.builder()
				.id(id)
				.numericId(numericId)
				.display(id.replace("__", "_").replace("_", " "))
				.category(category)
				.description("-")
				.build();
	}

	public static EndpointPermission of(int numericId) {
		return EndpointPermission.builder()
				.numericId(numericId)
				.build();
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

//	@Override
//	public EndpointPermissionDTO toPlainDTO() {
//		return EndpointPermissionDTO.builder()
//				.id(id)
//				.numericId(numericId)
//				.display(display)
//				.category(category)
//				.description(description)
//				.build();
//	}
}
