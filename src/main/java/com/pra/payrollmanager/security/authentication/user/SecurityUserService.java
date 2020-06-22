package com.pra.payrollmanager.security.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.AuditServiceDAO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.exception.util.ExceptionType;
import com.pra.payrollmanager.exception.util.UncheckedException;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.company.SecurityCompanyService;

@Service
@CacheConfig(cacheNames = CacheNameStore.SECURITY_USER_STORE)
public class SecurityUserService extends AuditServiceDAO<String, SecurityUser, SecurityUserDAL>
		implements UserDetailsService {

	@Autowired
	BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	SecurityCompanyService securityCompanyService;

	@Cacheable
	public SecurityUser loadUserByUsername(String userId) {
		String[] companyUser = userId.split("-");
		String companyId = companyUser[0];
		String userName = companyUser[1];
		SecurityCompany company = securityCompanyService.loadCompanyById(companyId);
		try {
			System.out.println("fetching user from db. .......");
			return dataAccessLayer.findById(userName, company.getTablePrefix())
					.setUserId(userId)
					.setCompany(company);
		} catch (DataNotFoundEx e) {
			throw UncheckedException.appException(CompanyEntityNames.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND,
					userName);
		}
	}

	private SecurityUser withEncodedPassword(SecurityUser user) {
		return user.setPassword(bcryptEncoder.encode(user.getPassword()));
	}

	@Override
	public SecurityUser create(SecurityUser user) {
		try {
			return super.create(this.withEncodedPassword(user));
		} catch (DuplicateDataEx e) {
			throw UncheckedException.appException(CompanyEntityNames.SECURITY_USER, ExceptionType.DUPLICATE_ENTITY,
					user.getUsername());
		}
	}

	public void createSuperUser(SecurityUser user, String tablePrefix) {
		dataAccessLayer.createSuperUser(this.withEncodedPassword(user), tablePrefix);
	}

	public void updateUserPassword(UserPasswordResetDTO user) throws CredentialNotMatchedEx {
		SecurityUser dbUser = super.getById(user.getUserName());

		if (!bcryptEncoder.matches(user.getOldPassword(), dbUser.getPassword()))
			throw CustomExceptions.wrongCredentialEx(CompanyEntityNames.SECURITY_USER, user.getUserName());

		dbUser.setPassword(user.getNewPassword());
		super.update(this.withEncodedPassword(dbUser));
	}

	@CacheEvict
	public void login(String userId) {
		SecurityUser userWithCompany = this.loadUserByUsername(userId);
		if (userWithCompany.getLoggedIn())
			return;
		dataAccessLayer.logInOnToken(userWithCompany);
	}

	public void loginUser(SecurityUser user) {
		this.login(user.getUserId());
	}

	@CacheEvict
	public void logout(String userId) {
		SecurityUser userWithCompany = this.loadUserByUsername(userId);
		if (userWithCompany.getLoggedIn()) {
			dataAccessLayer.logOut(userWithCompany.getUsername());
		}
	}

	public void logoutUser(SecurityUser user) {
		this.logout(user.getUserId());
	}

	public void deleteUser(SecurityUser user) {
		try {
			super.delete(user);
		} catch (DataNotFoundEx e) {
			throw UncheckedException.appException(CompanyEntityNames.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND,
					user.getUsername());
		}
	}

}
