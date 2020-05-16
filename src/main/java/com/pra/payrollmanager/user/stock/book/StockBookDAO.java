package com.pra.payrollmanager.user.stock.book;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class StockBookDAO extends BaseAuditDAOWithDTO<String, StockBookDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	private String id;
	private double quantity;

	private String note;

	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public StockBookDTO toPlainDTO() {
		return StockBookDTO.builder()
				.productId(id)
				.quantity(quantity)
				.updateNote(note)
				.build();
	}

}
