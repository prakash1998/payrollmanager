package com.pra.payrollmanager.admin.stock.product;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.Resource;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.ResourceType;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class ProductDAO extends BaseAuditDAOWithDTO<String, ProductDTO> implements Resource<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	private String id;
	private String name;

	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public ProductDTO toPlainDTO() {
		return ProductDTO.builder()
				.productId(id)
				.productName(name)
				.build();
	}

	@Override
	public ResourceType resourceType() {
		return ResourceType.PRODUCT;
	}

	@Override
	public String resourceId() {
		return this.id;
	}

	@Override
	public String display() {
		return name;
	}

}
