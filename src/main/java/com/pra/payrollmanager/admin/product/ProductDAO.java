package com.pra.payrollmanager.admin.product;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.Resource;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.ResourceType;
import com.pra.payrollmanager.validation.ValidationGroups;

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
@TypeAlias("c")
public class ProductDAO extends BaseAuditDAO<ObjectId> implements Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	@NotNull(groups = { ValidationGroups.onUpdate.class })
	private ObjectId id;
	@NotNull
	private String uId;
	private String name;
	private String unit;
	private List<ObjectId> taxes;

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}

	@Override
	public ResourceType resourceType() {
		return ResourceType.PRODUCT;
	}

	@Override
	public String display() {
		return name;
	}

}
