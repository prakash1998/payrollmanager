package com.pra.payrollmanager.user.root.company;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.validation.FieldValidator;

import io.swagger.annotations.ApiOperation;

@ApiOperation("Manage Restaurants")
@RestController
@RequestMapping("restaurant")
public class RestaurantDetailsControl extends BaseControl<CompanyDetailsService> {

	@GetMapping(value = "self", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> getSelf() throws DataNotFoundEx, AnyThrowable {
		SecurityCompany company = authService.getSecurityCompany();
		return Response.payload(service.getById(company.getId()));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<CompanyDetailsDTO>> getRestaurantDetails() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> getRestaurantDetail(@PathVariable("id") @NotNull String companyId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(companyId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> createRestaurantDetails(@Valid @RequestBody CompanyDetailsDTO company)
			throws DuplicateDataEx, MethodArgumentNotValidException, NoSuchMethodException,
			AnyThrowable {

		FieldValidator.validateNotNull(company, "getSuperUserPassword",
				"Super user Password must be provided");
		return Response.payload(service.create(company));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> updateRestaurantDetails(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(company));
	}

	@PutMapping(value = "lock", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> lockRestaurant(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		service.lockCompany(company);
		return Response.ok();
	}

	@PutMapping(value = "activate", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> activateRestaurant(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		service.activateCompany(company);
		return Response.ok();
	}

}
