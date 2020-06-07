package com.pra.payrollmanager.user.root.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
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
		SecurityUser user = authService.getSecurityUser();
		String userId = user.getUserId();
		return Response.payload(service.getAll().stream()
				.filter(res -> res.getModifier().equals(userId))
				.collect(Collectors.toList()));
	}

//	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public Response<CompanyDetailsDTO> getRestaurantDetail(@PathVariable("id") @NotNull String restaurantId)
//			throws DataNotFoundEx, AnyThrowable {
//		return Response.payload(service.getById(restaurantId));
//	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> createRestaurantDetails(@Valid @RequestBody CompanyDetailsDTO restaurant)
			throws DuplicateDataEx, MethodArgumentNotValidException, NoSuchMethodException,
			AnyThrowable {

		FieldValidator.validateNotNull(restaurant, "getSuperUserPassword",
				"Super user Password must be provided");
		return Response.payload(service.create(restaurant));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> updateRestaurantDetails(@Valid @RequestBody CompanyDetailsDTO restaurant)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(restaurant));
	}

	@PutMapping(value = "lock", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> lockRestaurant(@Valid @RequestBody CompanyDetailsDTO restaurant)
			throws DataNotFoundEx, AnyThrowable {
		service.lockCompany(restaurant);
		return Response.ok();
	}

	@PutMapping(value = "activate", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> activateRestaurant(@Valid @RequestBody CompanyDetailsDTO restaurant)
			throws DataNotFoundEx, AnyThrowable {
		service.activateCompany(restaurant);
		return Response.ok();
	}

}
