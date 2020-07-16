package com.pra.payrollmanager.user.stock.book;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.security.authorization.mappings.roleresource.ResourceRelated;

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
@TypeAlias("y")
public class StockBookDAO extends BaseAuditDAO<ObjectId> implements ResourceRelated {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	private ObjectId id;
	private Double quantity;

	private String note;

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}

	@Override
	public ObjectId resourceId() {
		return id;
	}


	// @Override
	// public StockBookDTO toPlainDTO() {
	// return StockBookDTO.builder()
	// .productId(id)
	// .quantity(quantity)
	// .updateNote(note)
	// .build();
	// }

}
