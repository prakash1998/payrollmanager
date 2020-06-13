package com.pra.payrollmanager.admin.stock.product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pra.payrollmanager.base.data.BaseAuditDTO;

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

	@NotNull
	@Size(min = 3, max = 24, message = "Product ID length must be between {min} and {max} charaters.")
	private String productId;
	private String productName;

//	@Override
//	public ProductDAO toPlainDAO() {
//		return ProductDAO.builder()
//				.id(productId)
//				.name(productName)
//				.build();
//	}

}
