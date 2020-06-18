package com.pra.payrollmanager.restaurant.hotel.order;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.AuditRTServiceDAO;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;

@Service
public class HotelOrderService
		extends AuditRTServiceDAO<ObjectId, HotelOrderDAO, HotelOrderDAL> {
	
	@Override
	public String mqTopic() {
		return KafkaTopics.HOTEL_ORDERS;
	}
	
	public HotelOrderDAO placeOrder(PlaceOrderDTO obj) throws DuplicateDataEx {
		
//		dataAccessLayer.find
		
		HotelOrderDAO order = HotelOrderDAO.builder()
				.tableId(obj.getTableId())
				.orderNumber(null)
				.build();
		
		return super.create(order);
	}

}
