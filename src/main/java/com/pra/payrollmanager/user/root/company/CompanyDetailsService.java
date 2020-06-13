package com.pra.payrollmanager.user.root.company;

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
	
	@Override
	public CompanyDetailsDTO postProcessGet(CompanyDetailsDAO obj) {
		CompanyDetailsDTO dto= super.postProcessGet(obj);
		SecurityCompany company = securityCompanyService.getById(dto.getId());
		dto.setLocked(company.getAccountLocked());
		dto.setPermissions(company.getPermissions());
		dto.setResourceFeatures(company.getResourceFeatures());
		dto.setEndpoints(company.getEndpointPermissions());
		dto.setScreenIds(company.getScreenIds());
		return dto;
	}
	

	@Override
	public CompanyDetailsDTO create(CompanyDetailsDTO company) throws DuplicateDataEx, AnyThrowable {
		CompanyDetailsDTO createdCompany =   super.create(company);
		// create security company after for copy audit info
		securityCompanyService.createFrom(createdCompany);
		return createdCompany;
	}

	@Override
	@Transactional
	public CompanyDetailsDTO update(CompanyDetailsDTO company) throws DataNotFoundEx, AnyThrowable {
		CompanyDetailsDTO updatedCompany =  super.update(company);
		// update security company after for copy audit info
		SecurityCompany securityCompany = updatedCompany.toSecurityCompany();
		securityCompanyService.update(securityCompany);
		return updatedCompany;
	}
	
	public CompanyDetailsDTO updateSelf(CompanyDetailsDTO company) throws DataNotFoundEx, AnyThrowable {
		return super.update(company);
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
