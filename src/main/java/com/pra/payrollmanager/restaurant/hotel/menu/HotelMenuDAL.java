package com.pra.payrollmanager.restaurant.hotel.menu;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class HotelMenuDAL extends AbstractDAL<ObjectId, HotelMenu> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.HOTEL_MENU;
	}


}
