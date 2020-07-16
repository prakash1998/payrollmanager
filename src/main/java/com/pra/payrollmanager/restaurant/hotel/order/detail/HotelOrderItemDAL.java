package com.pra.payrollmanager.restaurant.hotel.order.detail;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class HotelOrderItemDAL extends AbstractDAL<ObjectId, HotelOrderItemDAO> {
	

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.HOTEL_ORDER_DETAIL;
	}
	
	public List<HotelOrderItemDAO> getbyOrderId(ObjectId orderId){
		return this.findWith(DBQueryUtils.equalsQuery("orderId", orderId));
	}
	
	public List<HotelOrderItemDAO> getbyOrderIds(List<ObjectId> orderIds){
		return this.findWith(DBQueryUtils.inArrayQuery("orderId", orderIds.toArray()));
	}


}
