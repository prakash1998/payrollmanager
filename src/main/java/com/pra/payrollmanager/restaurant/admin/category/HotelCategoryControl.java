package com.pra.payrollmanager.restaurant.admin.category;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
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
@RequestMapping("hotel/category")
public class HotelCategoryControl extends BaseControl<HotelCategoryService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<HotelCategory>> getAllCategories() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelCategory> getCategory(@PathVariable("id") @NotNull ObjectId categoryId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(categoryId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelCategory> createCategory(@Valid @RequestBody HotelCategory category)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(category));
	}


	@Validated(ValidationGroups.onUpdate.class)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelCategory> updateCategory(@Valid @RequestBody HotelCategory category)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(category));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelCategory> deleteCategory(@Valid @RequestBody HotelCategory category)
			throws AnyThrowable, DataNotFoundEx {
		return Response.payload(service.delete(category));
	}

}
