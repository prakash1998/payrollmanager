package com.pra.payrollmanager.user.stock.book;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAO;

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
public class StockBookDAO extends BaseAuditDAO<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	private String id;
	private Double quantity;

	private String note;

	@Override
	public String primaryKeyValue() {
		return id;
	}

//	@Override
//	public StockBookDTO toPlainDTO() {
//		return StockBookDTO.builder()
//				.productId(id)
//				.quantity(quantity)
//				.updateNote(note)
//				.build();
//	}

}
