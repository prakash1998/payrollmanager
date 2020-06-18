package com.pra.payrollmanager.restaurant.hotel.order.detail;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class HotelOrderDetailDAL extends AbstractDAL<ObjectId, HotelOrderDetailDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.HOTEL_ORDER_DETAIL;
	}


}
