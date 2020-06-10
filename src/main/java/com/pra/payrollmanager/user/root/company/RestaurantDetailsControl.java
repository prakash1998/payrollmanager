package com.pra.payrollmanager.user.root.company;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;

import io.swagger.annotations.ApiOperation;

@ApiOperation("Manage Restaurants")
@RestController
@RequestMapping("restaurant")
public class RestaurantDetailsControl  {
	
	@Autowired
	CompanyDetailsControl companyDetailsControl;

	@GetMapping(value = "self", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> getSelf() throws DataNotFoundEx, AnyThrowable {
		return companyDetailsControl.getSelf();
	}

	@PutMapping(value = "self",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> updateSelf(@Valid @RequestBody CompanyDetailsDTO restaurant) throws DataNotFoundEx, AnyThrowable {
		return companyDetailsControl.updateSelf(restaurant);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<CompanyDetailsDTO>> getRestaurantDetails() {
		return companyDetailsControl.getCompanyDetails();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> getRestaurantDetail(@PathVariable("id") @NotNull String restaurantId)
			throws DataNotFoundEx, AnyThrowable {
		return companyDetailsControl.getCompany(restaurantId);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> createRestaurantDetails(@Valid @RequestBody CompanyDetailsDTO restaurant)
			throws DuplicateDataEx, MethodArgumentNotValidException, NoSuchMethodException,
			AnyThrowable {
		return companyDetailsControl.createCompanyDetails(restaurant);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> updateRestaurantDetails(@Valid @RequestBody CompanyDetailsDTO restaurant)
			throws DataNotFoundEx, AnyThrowable {
		return companyDetailsControl.updateCompanyDetails(restaurant);
	}

	@PutMapping(value = "lock", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> lockRestaurant(@Valid @RequestBody CompanyDetailsDTO restaurant)
			throws DataNotFoundEx, AnyThrowable {
		return companyDetailsControl.lockCompany(restaurant);
	}

	@PutMapping(value = "activate", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> activateRestaurant(@Valid @RequestBody CompanyDetailsDTO restaurant)
			throws DataNotFoundEx, AnyThrowable {
		return companyDetailsControl.activateCompany(restaurant);
	}

}
