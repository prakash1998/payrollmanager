package com.pra.payrollmanager.admin.stock.product;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("products")
public class ProductControl extends BaseControl<ProductService> {

	@GetMapping("allowed")
	public Response<List<ProductDTO>> getAllowedProducts() {
		return Response.payload(service.getOnlyAllowed());
	}

	@GetMapping
	public Response<List<ProductDTO>> getAllProducts() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}")
	public Response<ProductDTO> getProduct(@PathVariable("id") @NotNull ObjectId productId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(productId));
	}

	@PostMapping
	public Response<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(product));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@PutMapping
	public Response<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO product)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(product));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@DeleteMapping
	public Response<ProductDTO> deleteProduct(@Valid @RequestBody ProductDTO product)
			throws AnyThrowable, DataNotFoundEx {
		return Response.payload(service.delete(product));
	}

}
