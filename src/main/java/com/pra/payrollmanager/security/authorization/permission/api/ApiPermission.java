package com.pra.payrollmanager.security.authorization.permission.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class ApiPermission extends BaseAuditDAOWithDTO<String,ApiPermissionDTO> {

	@Id
	private String id;
	@NotNull
	private int numericId;
	private String display;
	private String category;
	@Builder.Default
	private Set<ApiServices> services = new HashSet<>();

	public static ApiPermission of(int numericId, String id, Set<ApiServices> services) {
		String category = id.split("__")[0];
		return ApiPermission.builder()
				.id(id)
				.numericId(numericId)
				.display(id.replace("__","_").replace("_", " "))
				.category(category)
				.services(services)
				.build();
	}

	public static ApiPermission of(int numericId, ApiServices... services) {
		return ApiPermission.builder()
				.numericId(numericId)
				.services(new HashSet<>(Arrays.asList(services)))
				.build();
	}
	
	public static ApiPermission of(int numericId) {
		return of(numericId,ApiServices.AUDIT,ApiServices.REALTIME);
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public ApiPermissionDTO toDto() {
		return ApiPermissionDTO.builder()
				.id(id)
				.numericId(numericId)
				.display(display)
				.category(category)
				.services(services)
				.build();
	}

}
