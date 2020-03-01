package com.pra.payrollmanager.admin.company;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.BaseServiceAuditDTO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.NotUseThisMethod;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.company.SecurityCompanyService;

@Service
public class CompanyDetailsService
		extends BaseServiceAuditDTO<String, CompanyDetailsDAO, CompanyDetailsDTO, CompanyDetailsDAL> {

	@Autowired
	SecurityCompanyService securityCompanyService;

	@Override
	public List<CompanyDetailsDTO> getAllDtos() {
		return super.getAllDtos().stream()
				.map(dto -> {
					try {
						dto.setPermissions(securityCompanyService.getById(dto.getCompanyId()).getPermissions());
					} catch (DataNotFoundEx e) {
						throw new RuntimeException("Database Inconsistancy CompanyDetails - SecurityCompany");
					}
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void create(CompanyDetailsDTO company) throws DuplicateDataEx {
		securityCompanyService.create(company);
		super.create(company.toDAO());
	}

	@Override
	@Transactional
	public void update(CompanyDetailsDTO company) throws DataNotFoundEx {
		SecurityCompany securityCompany = company.toSecurityCompany();
		securityCompanyService.update(securityCompany);
		super.update(company.toDAO());
	}

	public void disableCompany(CompanyDetailsDTO company) throws DataNotFoundEx {
		securityCompanyService.disableCompany(company.toSecurityCompany());
		super.delete(company.toDAO());
	}

	@Override
	public void delete(CompanyDetailsDTO company) {
		throw new NotUseThisMethod();
	}

}
