package com.pra.payrollmanager.security.authentication.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.services.ServiceDAO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.NotUseThisMethod;
import com.pra.payrollmanager.exception.util.ExceptionType;
import com.pra.payrollmanager.exception.util.UncheckedException;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.user.root.company.CompanyDetailsDTO;

@Service
@CacheConfig(cacheNames = CacheNameStore.SECURITY_COMPANY_STORE)
public class SecurityCompanyService extends ServiceDAO<String, SecurityCompany, SecurityCompanyDAL> {

	@Autowired
	SecurityUserService securityUserService;

	@Cacheable
	public SecurityCompany loadCompanyById(String companyId) {
		System.out.println("fetching company from db........");
		try {
			return super.getById(companyId);
		} catch (DataNotFoundEx e) {
			throw UncheckedException.appException(EntityName.COMPANY, ExceptionType.ENTITY_NOT_FOUND, companyId);
		}
	}

	@Override
	public SecurityCompany create(SecurityCompany company) throws DuplicateDataEx {
		throw new NotUseThisMethod();
	}

	@Transactional
	public void create(CompanyDetailsDTO company) throws DuplicateDataEx {
		SecurityCompany securityCompany = company.toSecurityCompany();
		super.create(securityCompany);
		securityUserService.createSuperUser(company.toSuperUser(), securityCompany.getTablePrefix());
	}

	@Override
	@CacheEvict(cacheNames = { CacheNameStore.SECURITY_COMPANY_STORE, CacheNameStore.SECURITY_USER_STORE },
			allEntries = true)
	public SecurityCompany update(SecurityCompany company) throws DataNotFoundEx {
		return super.update(company);
	}

	public void disableCompany(SecurityCompany company) throws DataNotFoundEx {
		super.update(company.withCompanyEnabled(false));
	}

}
