package com.pra.payrollmanager.restaurant.hotel.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.restaurant.hotel.order.detail.HotelOrderItemDAO;
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
public class HotelOrderDTO extends BaseAuditDTO<HotelOrderDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@NotNull(groups = { ValidationGroups.onUpdate.class })
	private ObjectId id;

	private Integer orderNumber;

	private ObjectId tableId;

	@Builder.Default
	private List<HotelOrderItemDAO> orderItems = new ArrayList<>();

	public List<HotelOrderItemDAO> getOrderItemsWith(ObjectId orderId) {
		return orderItems.stream()
				.map(item -> item.setOrderId(orderId))
				.collect(Collectors.toList());
	}

}
