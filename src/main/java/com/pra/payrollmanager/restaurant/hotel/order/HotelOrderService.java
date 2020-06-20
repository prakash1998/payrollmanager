package com.pra.payrollmanager.restaurant.hotel.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.base.services.AuditRTServiceDTO;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.restaurant.hotel.order.detail.HotelOrderItemDAL;
import com.pra.payrollmanager.restaurant.hotel.order.detail.HotelOrderItemDAO;

@Service
public class HotelOrderService
		extends AuditRTServiceDTO<ObjectId, HotelOrderDAO, HotelOrderDTO, HotelOrderDAL> {

	@Autowired
	HotelOrderItemDAL hotelOrderDetailDAL;

	@Override
	public String mqTopic() {
		return KafkaTopics.HOTEL_ORDERS;
	}

	public List<HotelOrderDTO> zipOrderItems(List<HotelOrderDAO> orders, List<HotelOrderItemDAO> items) {
		Map<ObjectId, List<HotelOrderItemDAO>> orderItemsmap = items.stream()
				.collect(Collectors.groupingBy(HotelOrderItemDAO::getOrderId, Collectors.toList()));

		return orders.stream().map(order -> {
			return toDTO(order).setOrderItems(orderItemsmap.getOrDefault(order.getId(), new ArrayList<>()));
		}).collect(Collectors.toList());
	}

	@Override
	public List<HotelOrderDTO> postProcessMultiGet(List<HotelOrderDAO> objList) {
		List<ObjectId> orderIds = objList.stream().map(order -> order.getId()).collect(Collectors.toList());
		return zipOrderItems(objList, hotelOrderDetailDAL.getbyOrderIds(orderIds));
	}

	@Override
	public HotelOrderDTO postProcessGet(HotelOrderDAO obj) {
		HotelOrderDTO dto = super.postProcessGet(obj);
		dto.setOrderItems(hotelOrderDetailDAL.getbyOrderId(obj.getId()));
		return dto;
	}

	@Override
	public HotelOrderDTO postProcessSave(HotelOrderDTO dtoToSave, HotelOrderDAO objFromDB) {
		HotelOrderDTO dto = super.postProcessSave(dtoToSave, objFromDB);
		dto.setOrderItems(dtoToSave.getOrderItems());
		return dto;
	}

	@Transactional
	public HotelOrderDTO placeOrder(HotelOrderDTO order) throws DuplicateDataEx, AnyThrowable {

		ObjectId orderId = ObjectId.get();

		BulkOp<HotelOrderItemDAO> savedItems = hotelOrderDetailDAL
				.bulkOp(BulkOp.fromAdded(order.getOrderItemsWith(orderId)));

		return super.create(
				order
						.setId(orderId)
						.setOrderNumber(dataAccessLayer.todaysNextOrder())
						.setOrderItems(savedItems.getAdded().stream().collect(Collectors.toList())));
	}

	@Transactional
	public HotelOrderDTO addItemsToOrder(HotelOrderDTO order) throws DuplicateDataEx, AnyThrowable {
		BulkOp<HotelOrderItemDAO> savedItems = hotelOrderDetailDAL
				.bulkOp(BulkOp.fromAdded(order.getOrderItemsWith(order.getId())));
		return order.setOrderItems(savedItems.getAdded().stream().collect(Collectors.toList()));
	}

}
