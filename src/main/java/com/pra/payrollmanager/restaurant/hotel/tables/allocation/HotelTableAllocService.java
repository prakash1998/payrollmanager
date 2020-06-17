package com.pra.payrollmanager.restaurant.hotel.tables.allocation;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.RTServiceDAO;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Service
public class HotelTableAllocService
		extends RTServiceDAO<ObjectId, HotelTableAllocDAO, HotelTableAllocDAL> {
	
	@Override
	public String mqTopic() {
		return KafkaTopics.HOTEL_TABLES;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.HOTEL__TABLES;
	}
	
	public HotelTableAllocDAO allocateTable(ObjectId tableId) {
		return super.update(HotelTableAllocDAO.builder()
				.id(tableId)
				.allocated(true)
				.build());
	}
	
	public HotelTableAllocDAO clearTable(ObjectId tableId) {
		return super.update(HotelTableAllocDAO.builder()
				.id(tableId)
				.allocated(true)
				.build());
	}

}
