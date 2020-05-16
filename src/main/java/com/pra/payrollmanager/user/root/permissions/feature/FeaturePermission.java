package com.pra.payrollmanager.user.root.permissions.feature;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;
import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.security.authorization.permission.ResourceFeatures;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class FeaturePermission extends BaseAuditDAOWithDTO<String, FeaturePermissionDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6946870937925008437L;

	@Id
	private String id;
	@NonNull
	private int numericId;
	private String display;
	private String description;
	@Builder.Default
	private Set<ResourceFeatures> features = new HashSet<>();

	public static FeaturePermission of(int numericId, String id, Set<ResourceFeatures> features) {
		return FeaturePermission.builder()
				.id(id)
				.numericId(numericId)
				.display(id.replace("_", " "))
				.description("-")
				.features(features)
				.build();
	}

	public static FeaturePermission of(int numericId, ResourceFeatures... features) {
		return FeaturePermission.builder()
				.numericId(numericId)
				.features(new HashSet<>(Arrays.asList(features)))
				.build();
	}

	public static FeaturePermission of(int numericId) {
		return of(numericId, ResourceFeatures.AUDIT, ResourceFeatures.REALTIME);
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public FeaturePermissionDTO toPlainDTO() {
		return FeaturePermissionDTO.builder()
				.id(id)
				.numericId(numericId)
				.display(display)
				.description(description)
				.features(features)
				.build();
	}

}
