package com.pra.payrollmanager.restaurant.hotel.order.detail;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.RTServiceDAO;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Service
public class HotelOrderDetailService
		extends RTServiceDAO<ObjectId, HotelOrderDetailDAO, HotelOrderDetailDAL> {

	@Override
	public String mqTopic() {
		return KafkaTopics.HOTEL_ORDER_DETAIL;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.HOTEL__ORDER_DETAIL;
	}
	

	
}
