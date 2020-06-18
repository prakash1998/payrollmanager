package com.pra.payrollmanager.restaurant.hotel.order.detail;

import org.bson.types.ObjectId;
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
public class HotelOrderDetailDAO extends BaseAuditDAO<ObjectId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;


	@Id
	private ObjectId id;
	
	private ObjectId orderId;
	
	private ObjectId menuId;
	
	private Double quantity;

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}
}
