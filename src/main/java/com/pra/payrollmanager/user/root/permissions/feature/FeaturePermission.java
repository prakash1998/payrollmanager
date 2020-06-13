package com.pra.payrollmanager.user.root.permissions.feature;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.mongodb.lang.NonNull;
import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.security.authorization.permission.ApiFeatures;

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
public class FeaturePermission extends BaseAuditDAO<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6946870937925008437L;

	@Id
	private String id;
	@NonNull
	private Integer numericId;
	private String display;
	private String description;
	@Builder.Default
	private Set<ApiFeatures> features = new HashSet<>();

	public static FeaturePermission of(int numericId, String id, Set<ApiFeatures> features) {
		return FeaturePermission.builder()
				.id(id)
				.numericId(numericId)
				.display(id.replace("_", " "))
				.description("-")
				.features(features)
				.build();
	}

	public static FeaturePermission of(int numericId, ApiFeatures... features) {
		return FeaturePermission.builder()
				.numericId(numericId)
				.features(new HashSet<>(Arrays.asList(features)))
				.build();
	}

	public static FeaturePermission of(int numericId) {
		return of(numericId, ApiFeatures.AUDIT, ApiFeatures.REALTIME);
	}

	@Override
	public String primaryKeyValue() {
		return id;
	}

//	@Override
//	public FeaturePermissionDTO toPlainDTO() {
//		return FeaturePermissionDTO.builder()
//				.id(id)
//				.numericId(numericId)
//				.display(display)
//				.description(description)
//				.features(features)
//				.build();
//	}

}
