package com.pra.payrollmanager.security.authentication.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.apputils.cachemanager.AppCacheService;
import com.pra.payrollmanager.base.services.AuditServiceDAO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.NotUseThisMethod;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;

@Service
public class SecurityCompanyService extends AuditServiceDAO<String, SecurityCompany, SecurityCompanyDAL> {

	@Autowired
	SecurityUserService securityUserService;

	@Autowired
	AppCacheService cacheService;

	public SecurityCompany loadCompanyById(String companyId) {
		return cacheService.cached(CacheNameStore.SECURITY_COMPANY_STORE, companyId, (key) -> {
			return super.getById(companyId);
		});
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
	public SecurityCompany update(SecurityCompany company) throws DataNotFoundEx {

		cacheService.removeByKey(CacheNameStore.SECURITY_COMPANY_STORE, company.getId());
		cacheService.clearCaches(CacheNameStore.SECURITY_USER_STORE);

		return super.update(company);
	}

	public void lockCompany(SecurityCompany company) throws DataNotFoundEx {
		super.update(company.setAccountLocked(true));
	}

	public void unlockCompany(SecurityCompany company) throws DataNotFoundEx {
		super.update(company.setAccountLocked(false));
	}

}
