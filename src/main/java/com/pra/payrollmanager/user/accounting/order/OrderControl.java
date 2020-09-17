package com.pra.payrollmanager.user.accounting.order;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.pra.payrollmanager.validation.ValidationGroups;

@Validated
@RestController
@RequestMapping("accounts/order")
public class OrderControl extends BaseControl<OrderService> {

	@GetMapping(value = "/{id}")
	public Response<OrderDTO> getTable(@PathVariable("id") @NotNull ObjectId orderId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(orderId));
	}

	@Validated(ValidationGroups.onCreate.class)
	@PostMapping
	public Response<OrderDTO> createOrder(@Valid @RequestBody OrderDTO order)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(order));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@PutMapping
	public Response<OrderDTO> updateOrder(@Valid @RequestBody OrderDTO order)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(order));
	}

}
