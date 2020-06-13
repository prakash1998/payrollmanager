package com.pra.payrollmanager.restaurant.hotel.tables;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class HotelTableDAL extends AbstractDAL<ObjectId, HotelTableDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.HOTEL_TABLE;
	}


}
