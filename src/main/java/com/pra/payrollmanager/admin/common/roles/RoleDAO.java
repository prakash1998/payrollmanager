package com.pra.payrollmanager.admin.common.roles;

import java.util.HashSet;
import java.util.Set;

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
public class RoleDAO extends BaseAuditDAO<String> {

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

//	@Override
//	public RoleDTO toPlainDTO() {
//		return RoleDTO.builder()
//				.roleId(id)
//				.roleName(name)
//				 .screenIds(screenIds)
//				.build();
//	}

}
