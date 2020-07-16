package com.pra.payrollmanager.admin.stock.product;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.Resource;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.ResourceType;

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
	private ObjectId id;
	private String name;

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
