package com.pra.payrollmanager.restaurant.hotel.order;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.restaurant.hotel.order.detail.HotelOrderItemDAO;
import com.pra.payrollmanager.restaurant.hotel.order.detail.HotelOrderItemService;
import com.pra.payrollmanager.validation.ValidationGroups;

@Validated
@RestController
@RequestMapping("hotel/order")
public class HotelOrderControl extends BaseControl<HotelOrderService> {

	@Autowired
	HotelOrderItemService orderItemService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelOrderDTO> getTable(@PathVariable("id") @NotNull ObjectId orderId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(orderId));
	}

	@PostMapping(value = "place", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelOrderDTO> placeOrder(@Valid @RequestBody HotelOrderDTO order)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.placeOrder(order));
	}

	@PatchMapping(value = "add/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelOrderDTO> addRequest(@PathVariable("id") @NotNull ObjectId orderId,
			@Valid @RequestBody List<HotelOrderItemDAO> orderItems)
			throws DuplicateDataEx, AnyThrowable {

		return Response.payload(service.addItemsToOrder(HotelOrderDTO.builder()
				.id(orderId)
				.orderItems(orderItems)
				.build()));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@PutMapping(value = "item", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelOrderItemDAO> updateItem(@Valid @RequestBody HotelOrderItemDAO item)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(orderItemService.update(item));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@DeleteMapping(value = "item", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelOrderItemDAO> removeItem(@Valid @RequestBody HotelOrderItemDAO item)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(orderItemService.delete(item));
	}

}
