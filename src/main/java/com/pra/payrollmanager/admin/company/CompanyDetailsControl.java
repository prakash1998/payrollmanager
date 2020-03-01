package com.pra.payrollmanager.admin.company;

import static com.pra.payrollmanager.security.authorization.SecurityPermissions.COMAPNY_DETAILS_MANAGER;
import static com.pra.payrollmanager.security.authorization.SecurityPermissions.COMAPNY_DETAILS_VIEWER;

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
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.ValidationException;
import com.pra.payrollmanager.response.dto.Response;

@RestController
@RequestMapping("company")
public class CompanyDetailsControl extends BaseControl<CompanyDetailsService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<CompanyDetailsDTO>> getCompanyDetails() {
		authService.validatePermissions(COMAPNY_DETAILS_MANAGER, COMAPNY_DETAILS_VIEWER);
		return Response.payload(service.getAllDtos());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CompanyDetailsDTO> getCompany(@PathVariable("id") @NotNull String companyId) throws DataNotFoundEx {
		authService.validatePermissions(COMAPNY_DETAILS_MANAGER, COMAPNY_DETAILS_VIEWER);
		return Response.payload(service.getDtoById(companyId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> createCompanyDetails(@Valid @RequestBody CompanyDetailsDTO company)
			throws DuplicateDataEx, MethodArgumentNotValidException, NoSuchMethodException, SecurityException {

		if (company.getSuperUserPassword() == null) {
			ValidationException.throwError(CompanyDetailsDTO.class, "getSuperUserPassword",
					"Super user Password must be provided");
		}

		authService.validatePermissions(COMAPNY_DETAILS_MANAGER);
		service.create(company);
		return Response.ok();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updateCompanyDetails(@Valid @RequestBody CompanyDetailsDTO company) throws DataNotFoundEx {
		authService.validatePermissions(COMAPNY_DETAILS_MANAGER);
		service.update(company);
		return Response.ok();
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> disableCompnay(@Valid @RequestBody CompanyDetailsDTO company) throws DataNotFoundEx {
		authService.validatePermissions(COMAPNY_DETAILS_MANAGER);
		service.disableCompany(company);
		return Response.ok();
	}

}
