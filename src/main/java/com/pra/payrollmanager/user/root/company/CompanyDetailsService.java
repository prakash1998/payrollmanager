package com.pra.payrollmanager.user.root.company;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.services.AuditServiceDTO;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.NotUseThisMethod;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.company.SecurityCompanyService;

@Service
public class CompanyDetailsService
		extends AuditServiceDTO<String, CompanyDetailsDAO, CompanyDetailsDTO, CompanyDetailsDAL> {

	@Autowired
	SecurityCompanyService securityCompanyService;

	private CompanyDetailsDTO injectPermissions(CompanyDetailsDTO dto) {
		try {
			SecurityCompany company = securityCompanyService.getById(dto.getId());
			dto.setPermissions(company.getPermissions());
			dto.setResourceFeatures(company.getResourceFeatures());
			dto.setEndpoints(company.getEndpointPermissions());
			return dto;
		} catch (DataNotFoundEx e) {
			throw new RuntimeException("Inconsistancy in company");
		}
	}

	private CompanyDetailsDTO injectPermissionsFromTo(CompanyDetailsDTO from, CompanyDetailsDTO to) {
		to.setPermissions(from.getPermissions());
		to.setResourceFeatures(from.getResourceFeatures());
		to.setEndpoints(from.getEndpoints());
		return to;
	}

	@Override
	public CompanyDetailsDTO getById(String id) throws DataNotFoundEx, AnyThrowable {
		CompanyDetailsDTO dto = super.getById(id);
		return injectPermissions(dto);
	}

	@Override
	public List<CompanyDetailsDTO> getAll() {
		return super.getAll().stream()
				.map(dto -> {
					return injectPermissions(dto);
				})
				.collect(Collectors.toList());
	}

	@Override
	public CompanyDetailsDTO create(CompanyDetailsDTO company) throws DuplicateDataEx, AnyThrowable {
		securityCompanyService.create(company);
		CompanyDetailsDTO savedCompany = super.create(company);
		return injectPermissionsFromTo(company, savedCompany);
	}

	@Override
	@Transactional
	public CompanyDetailsDTO update(CompanyDetailsDTO company) throws DataNotFoundEx, AnyThrowable {
		SecurityCompany securityCompany = company.toSecurityCompany();
		securityCompanyService.update(securityCompany);
		CompanyDetailsDTO updatedCompany = super.update(company);
		return injectPermissionsFromTo(company, updatedCompany);
	}

	public void lockCompany(CompanyDetailsDTO company) throws DataNotFoundEx, AnyThrowable {
		securityCompanyService.lockCompany(company.toSecurityCompany());
//		super.delete(company);
	}
	
	public void activateCompany(CompanyDetailsDTO company) throws DataNotFoundEx, AnyThrowable {
		securityCompanyService.unlockCompany(company.toSecurityCompany());
//		super.delete(company);
	}

	@Override
	public CompanyDetailsDTO delete(CompanyDetailsDTO company) {
		throw new NotUseThisMethod();
	}

}
