package com.pra.payrollmanager.restaurant.hotel.order;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.restaurant.hotel.order.detail.HotelOrderDetailDAO;

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
public class PlaceOrderDTO extends BaseAuditDTO<HotelOrderDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	private ObjectId tableId;
	
	@Builder.Default
	private List<HotelOrderDetailDAO> items = new ArrayList<>();
	

}
