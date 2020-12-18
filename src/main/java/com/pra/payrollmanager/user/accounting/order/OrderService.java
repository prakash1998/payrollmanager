package com.pra.payrollmanager.user.accounting.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.base.services.AuditServiceDTO;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.user.accounting.order.detail.OrderItemDAL;

@Service
public class OrderService
		extends AuditServiceDTO<ObjectId, OrderDAO, OrderDTO, OrderDAL> {

	@Autowired
	private OrderItemDAL orderItemDAL;

	// public List<OrderDTO> zipOrderItems(List<OrderDAO> orders,
	// List<OrderItemDAO> items) {
	// var orderItemsmap = items.stream()
	// .collect(Collectors.groupingBy(item -> item.getId(),
	// Collectors.toList()));
	//
	// return orders.stream().map(order -> {
	// return toDTO(order)
	// .setOrderItems(orderItemsmap.getOrDefault(order.getId(),
	// new ArrayList<>()));
	// }).collect(Collectors.toList());
	// }

	@Override
	public List<OrderDTO> postProcessMultiGet(List<OrderDAO> objList) {
		// var orderIds = objList.stream()
		// .flatMap(order -> order.getItems().stream())
		// .collect(Collectors.toSet());
		// return zipOrderItems(objList, orderItemDAL.findByIds(orderIds));
		return objList.stream().map(item -> {
			var items = orderItemDAL.findByIds(item.getItems());
			return super.toDTO(item).setOrderItems(items);
		}).collect(Collectors.toList());
	}

	@Override
	public OrderDTO postProcessGet(OrderDAO obj) {
		var dto = super.postProcessGet(obj);
		var items = orderItemDAL.findByIds(obj.getItems());
		return dto.setOrderItems(items);
	}

	// @Override
	// public OrderDTO postProcessSave(OrderDTO dtoToSave, OrderDAO objFromDB) {
	// OrderDTO dto = super.postProcessSave(dtoToSave, objFromDB);
	// return dto.setOrderItems(dtoToSave.getOrderItems());
	// }

	@Override
	@Transactional
	public OrderDTO create(OrderDTO obj) throws DuplicateDataEx, AnyThrowable {
		var updates = orderItemDAL.bulkOp(BulkOp.fromAdded(obj.getOrderItems()));
		obj = obj.setOrderItems(BulkOp.applyOps(obj.getOrderItems(), updates))
				.setUpdates(BulkOp.empty());
		return super.create(obj);
	}

	@Override
	@Transactional
	public OrderDTO update(OrderDTO obj) throws DataNotFoundEx, AnyThrowable {
		var updates = orderItemDAL.bulkOp(obj.getUpdates());
		obj = obj.setOrderItems(BulkOp.applyOps(obj.getOrderItems(), updates))
				.setUpdates(BulkOp.empty());
		return super.update(obj);
	}

	@Override
	public OrderDAO toDAO(OrderDTO dto) {
		return super.toDAO(dto)
				.setItems(dto.getOrderItems().stream()
						.map(i -> i.getId())
						.collect(Collectors.toSet()));
	}

}
