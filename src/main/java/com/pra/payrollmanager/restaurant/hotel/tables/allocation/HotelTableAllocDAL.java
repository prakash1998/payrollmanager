package com.pra.payrollmanager.restaurant.hotel.tables.allocation;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class HotelTableAllocDAL extends AbstractDAL<ObjectId, HotelTableAllocDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.HOTEL_TABLE_ALLOC;
	}


}
