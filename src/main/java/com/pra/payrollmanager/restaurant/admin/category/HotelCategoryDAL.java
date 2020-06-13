package com.pra.payrollmanager.restaurant.admin.category;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CommonEntityNames;

@Repository
public class HotelCategoryDAL extends AbstractDAL<ObjectId, HotelCategory> {

	@Override
	public CommonEntityNames entity() {
		return CommonEntityNames.HOTEL_CATEGORY;
	}


}
