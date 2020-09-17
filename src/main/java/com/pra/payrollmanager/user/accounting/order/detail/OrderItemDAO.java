package com.pra.payrollmanager.user.accounting.order.detail;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TypeAlias("4")
public class OrderItemDAO extends BaseAuditDAO<ObjectId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	private ObjectId id;

	@NotNull
	private Integer index;

	@NotNull
	private Double qty;

	@NotNull
	private Double price;

	private List<ObjectId> taxes;

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}
}
