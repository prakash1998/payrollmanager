package com.pra.payrollmanager.restaurant.hotel.category;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class HotelCategoryDAL extends AbstractDAL<String, HotelCategory> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.PRODUCT;
	}


}
