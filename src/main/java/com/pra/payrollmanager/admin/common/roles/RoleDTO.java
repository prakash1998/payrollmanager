package com.pra.payrollmanager.admin.common.roles;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.base.data.BulkOp;

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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RoleDTO extends BaseAuditDTO<RoleDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8557333859873513279L;

	@NotNull
	private String roleId;
	private String roleName;
	@Builder.Default
	private Set<Integer> permissions = new HashSet<>();
	@Builder.Default
	private Set<String> endpoints = new HashSet<>();
	@Builder.Default
	private Set<String> screenIds = new HashSet<>();
	@Builder.Default
	private Set<ObjectId> resources = new HashSet<>();
	@Builder.Default
	private Set<String> users = new HashSet<>();

	// @Override
	// public RoleDAO toPlainDAO() {
	// return RoleDAO.builder()
	// .id(roleId)
	// .name(roleName)
	// .screenIds(screenIds)
	// .build();
	// }

}
