package com.pra.payrollmanager.restaurant.hotel.info;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.user.root.company.CompanyDetailsControl;
import com.pra.payrollmanager.user.root.company.CompanyDetailsDTO;

import io.swagger.annotations.ApiOperation;

@ApiOperation("Update Restaurant Data")
@RestController
@RequestMapping("hotel")
public class HotelManagerControl  {
	
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
}
