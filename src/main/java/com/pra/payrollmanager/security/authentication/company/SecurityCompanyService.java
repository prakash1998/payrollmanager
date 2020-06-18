package com.pra.payrollmanager.security.authentication.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.services.AuditServiceDAO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.entity.CommonEntityNames;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.NotUseThisMethod;
import com.pra.payrollmanager.exception.util.ExceptionType;
import com.pra.payrollmanager.exception.util.UncheckedException;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;

@Service
@CacheConfig(cacheNames = CacheNameStore.SECURITY_COMPANY_STORE)
public class SecurityCompanyService extends AuditServiceDAO<String, SecurityCompany, SecurityCompanyDAL> {

	@Autowired
	SecurityUserService securityUserService;

	@Cacheable
	public SecurityCompany loadCompanyById(String companyId) {
		System.out.println("fetching company from db........");
		try {
			return super.getById(companyId);
		} catch (DataNotFoundEx e) {
			throw UncheckedException.appException(CommonEntityNames.COMPANY, ExceptionType.ENTITY_NOT_FOUND, companyId);
		}
	}

	@Override
	public SecurityCompany create(SecurityCompany company) throws DuplicateDataEx {
		throw new NotUseThisMethod();
	}

	@Transactional
	public void createSecurityCompany(SecurityCompany securityCompany, SecurityUser superUser) throws DuplicateDataEx {
		super.create(securityCompany);
		securityUserService.createSuperUser(superUser, securityCompany.getTablePrefix());
	}

	@Override
	@CacheEvict(cacheNames = { CacheNameStore.SECURITY_COMPANY_STORE, CacheNameStore.SECURITY_USER_STORE },
			allEntries = true)
	public SecurityCompany update(SecurityCompany company) throws DataNotFoundEx {
		return super.update(company);
	}

	public void lockCompany(SecurityCompany company) throws DataNotFoundEx {
		super.update(company.setAccountLocked(true));
	}

	public void unlockCompany(SecurityCompany company) throws DataNotFoundEx {
		super.update(company.setAccountLocked(false));
	}

}
