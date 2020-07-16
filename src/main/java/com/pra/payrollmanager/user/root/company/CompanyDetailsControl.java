package com.pra.payrollmanager.user.root.company;

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
import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.UnAuthorizedEx;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.validation.ValidationGroups;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@Validated
@RequestMapping("company")
public class CompanyDetailsControl extends BaseControl<CompanyDetailsService> {

	@GetMapping("self")
	public Response<CompanyDetailsDTO> getSelf() throws DataNotFoundEx, AnyThrowable {
		SecurityCompany company = authService.getSecurityCompany();
		return Response.payload(service.getById(company.getId()));
	}

	@PutMapping("self")
	public Response<CompanyDetailsDTO> updateSelf(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		SecurityCompany userCompany = authService.getSecurityCompany();
		if (!userCompany.getId().equals(company.getId())) {
			throw new UnAuthorizedEx("Unauthorized Operation");
		}
		return Response.payload(service.updateSelf(company));
	}

	@PostMapping("bulk/test")
	public Response<Void> bulkOp(@Valid @RequestBody BulkOp<CompanyDetailsDTO> company)
			throws DataNotFoundEx, AnyThrowable {
		return Response.ok();
	}

	@GetMapping
	public Response<List<CompanyDetailsDTO>> getCompanyDetails() {
		return Response.payload(service.getAll());
	}

	@GetMapping("/{id}")
	public Response<CompanyDetailsDTO> getCompany(@PathVariable("id") @NotNull String companyId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(companyId));
	}

	@Validated(ValidationGroups.onCreate.class)
	@PostMapping
	public Response<CompanyDetailsDTO> createCompanyDetails(@Valid @RequestBody CompanyDetailsDTO company)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(company));
	}

	@PutMapping
	public Response<CompanyDetailsDTO> updateCompanyDetails(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(company));
	}

	@PutMapping("lock")
	public Response<Void> lockCompany(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		service.lockCompany(company);
		return Response.ok();
	}

	@PutMapping("activate")
	public Response<Void> activateCompany(@Valid @RequestBody CompanyDetailsDTO company)
			throws DataNotFoundEx, AnyThrowable {
		service.activateCompany(company);
		return Response.ok();
	}

}
