package com.pra.payrollmanager.admin.accounting.customer;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
@RequestMapping("accounts/customer")
public class CustomerControl extends BaseControl<CustomerService> {

	@GetMapping
	public Response<List<CustomerDAO>> getAllCustomers() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}")
	public Response<CustomerDAO> getCustomer(@PathVariable("id") @NotNull String userName)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(userName));
	}

	@PostMapping
	public Response<CustomerDAO> createCustomer(@Valid @RequestBody CustomerDAO customer)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(customer));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@PutMapping
	public Response<CustomerDAO> updateCustomer(@Valid @RequestBody CustomerDAO customer)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(customer));
	}

	// @Validated(ValidationGroups.onUpdate.class)
	// @DeleteMapping
	// public Response<CustomerDAO> deleteCustomer(@Valid @RequestBody CustomerDAO
	// customer)
	// throws AnyThrowable, DataNotFoundEx {
	// return Response.payload(service.delete(customer));
	// }

}
