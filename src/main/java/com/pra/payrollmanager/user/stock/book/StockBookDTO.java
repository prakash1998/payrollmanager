package com.pra.payrollmanager.user.stock.book;

import javax.validation.constraints.NotNull;

import com.pra.payrollmanager.base.data.BaseAuditDTO;

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
public class StockBookDTO extends BaseAuditDTO<StockBookDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6414862975908609314L;

	@NotNull
	private String productId;
	@NotNull
	private double quantity;

	private String updateNote;

	@Override
	public StockBookDAO toPlainDAO() {
		return StockBookDAO.builder()
				.id(productId)
				.quantity(quantity)
				.note(updateNote)
				.build();
	}

}
