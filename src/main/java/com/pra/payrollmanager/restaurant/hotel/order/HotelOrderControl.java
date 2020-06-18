package com.pra.payrollmanager.restaurant.hotel.order;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;

@Validated
@RestController
@RequestMapping("hotel/order")
public class HotelOrderControl extends BaseControl<HotelOrderService> {

	@PostMapping(value = "place", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelOrderDAO> placeOrder(@Valid @RequestBody PlaceOrderDTO order)
			throws DuplicateDataEx, AnyThrowable {
		
		return Response.payload(service.placeOrder(order));
	}

}
