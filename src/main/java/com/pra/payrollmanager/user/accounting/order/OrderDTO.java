package com.pra.payrollmanager.user.accounting.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.user.accounting.order.detail.OrderItemDAO;
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
public class OrderDTO extends BaseAuditDTO<OrderDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@NotNull(groups = { ValidationGroups.onUpdate.class })
	private ObjectId id;

	@NotNull(message = "Order ID must be provided.")
	private Integer ref;

	@NotNull(message = "Customer must be provided.")
	private String customer;

	@NotNull(message = "Order Date must be provided.")
	private LocalDate date;

	@Builder.Default
	private List<OrderItemDAO> orderItems = new ArrayList<>();

	@Builder.Default
	private BulkOp<OrderItemDAO> updates = new BulkOp<>();

}
