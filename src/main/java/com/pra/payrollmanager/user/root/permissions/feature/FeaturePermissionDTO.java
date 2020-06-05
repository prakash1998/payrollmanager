package com.pra.payrollmanager.user.root.permissions.feature;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;

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
public class FeaturePermissionDTO extends BaseAuditDTO<FeaturePermission> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -713631996138431627L;
	
	@NotNull
	private Integer numericId;
	@NotNull
	private String id;
	private String display;
	private String description;
	@Builder.Default
	private Set<ApiFeatures> features = new HashSet<>();

	@Override
	public FeaturePermission toPlainDAO() {
		return FeaturePermission.builder()
				.id(id)
				.numericId(numericId)
				.display(display)
				.description(description)
				.features(features)
				.build();
	}

}
