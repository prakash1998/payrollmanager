package com.pra.payrollmanager.admin.stock.product;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
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
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = false)
public class ProductDTO extends BaseAuditDTO<ProductDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6414862975908609314L;

	@NotNull(groups = {ValidationGroups.onUpdate.class})
	private ObjectId id;
	private String name;

}
