package com.pra.payrollmanager.admin.common.roles;

import java.util.HashSet;
import java.util.Set;

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
public class RoleDAO extends BaseAuditDAOWithDTO<String, RoleDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6609365859435987819L;

	@Id
	private String id;
	private String name;
	@Builder.Default
	private Set<String> screenIds = new HashSet<>();

	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public RoleDTO toPlainDTO() {
		return RoleDTO.builder()
				.roleId(id)
				.roleName(name)
				 .screenIds(screenIds)
				.build();
	}

}
