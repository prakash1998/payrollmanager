package com.pra.payrollmanager.admin.stock.product;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
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
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;

@RestController
@RequestMapping("products")
public class ProductControl extends BaseControl<ProductService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<ProductDTO>> getAllProducts() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<ProductDTO> getProduct(@PathVariable("id") @NotNull String productId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(productId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(product));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO product)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(product));
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<ProductDTO> deleteProduct(@Valid @RequestBody ProductDTO product)
			throws AnyThrowable, DataNotFoundEx {
		return Response.payload(service.delete(product));
	}

}
