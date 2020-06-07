package com.pra.payrollmanager.user.root.company;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.validation.FieldValidator;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("company")
public class CompanyDetailsControl extends BaseControl<CompanyDetailsService> {

	@GetMapping(value = "self", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> getSelf() throws DataNotFoundEx, AnyThrowable {
		SecurityCompany company = authService.getSecurityCompany();
		return Response.payload(service.getById(company.getId()));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<CompanyDetailsDTO>> getCompanyDetails() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> getCompany(@PathVariable("id") @NotNull String companyId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(companyId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> createCompanyDetails(@Valid @RequestBody CompanyDetailsDTO company)
			throws DuplicateDataEx, MethodArgumentNotValidException, NoSuchMethodException,
			AnyThrowable {

		FieldValidator.validateNotNull(company, "getSuperUserPassword",
				"Super user Password must be provided");
		return Response.payload(service.create(company));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> updateCompanyDetails(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(company));
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> disableCompnay(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		service.lockCompany(company);
		return Response.ok();
	}

}
