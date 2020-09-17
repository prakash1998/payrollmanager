package com.pra.payrollmanager.admin.accounting.taxes;

import java.util.List;

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
@RequestMapping("accounts/taxes")
public class TaxControl extends BaseControl<TaxService> {

	@GetMapping
	public Response<List<TaxDAO>> getAllTaxs() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}")
	public Response<TaxDAO> getTax(@PathVariable("id") @NotNull ObjectId taxId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(taxId));
	}

	@PostMapping
	public Response<TaxDAO> createTax(@Valid @RequestBody TaxDAO tax)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(tax));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@PutMapping
	public Response<TaxDAO> updateTax(@Valid @RequestBody TaxDAO tax)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(tax));
	}

//	@Validated(ValidationGroups.onUpdate.class)
//	@DeleteMapping
//	public Response<TaxDAO> deleteTax(@Valid @RequestBody TaxDAO tax)
//			throws AnyThrowable, DataNotFoundEx {
//		return Response.payload(service.delete(tax));
//	}

}
