package com.pra.payrollmanager.security.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.exception.util.ExceptionType;
import com.pra.payrollmanager.exception.util.UncheckedException;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.company.SecurityCompanyService;

@Service
@CacheConfig(cacheNames = CacheNameStore.SECURITY_USER_STORE)
public class SecurityUserService extends ServiceDAO<String, SecurityUser, SecurityUserDAL>
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
			return dataAccessLayer.findById(userName, company.getTablePrefix()).withUserId(userId)
					.withCompany(company);
		} catch (DataNotFoundEx e) {
			throw UncheckedException.appException(EntityName.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND, userName);
		}
	}

	private SecurityUser withEncodedPassword(SecurityUser user) {
		return user.withPassword(bcryptEncoder.encode(user.getPassword()));
	}

	@Override
	public SecurityUser create(SecurityUser user) {
		try {
			return super.create(this.withEncodedPassword(user));
		} catch (DuplicateDataEx e) {
			throw UncheckedException.appException(EntityName.SECURITY_USER, ExceptionType.DUPLICATE_ENTITY,
					user.getUsername());
		}
	}

	public void createSuperUser(SecurityUser user, String tablePrefix) {
		dataAccessLayer.createSuperUser(this.withEncodedPassword(user), tablePrefix);
	}

	public void updateUserPassword(UserPasswordResetDTO user) throws CredentialNotMatchedEx {
		try {
			SecurityUser dbUser = super.getById(user.getUserName());
			if (!dbUser.getPassword().equals(user.getOldPassword()))
				throw CheckedException.wrongCredentialEx(EntityName.SECURITY_USER, user.getUserName());
			super.update(this.withEncodedPassword(dbUser));
		} catch (DataNotFoundEx e) {
			throw UncheckedException.appException(EntityName.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND,
					user.getUserName());
		}
	}

	@CacheEvict
	public void login(String userId) {
		SecurityUser dbUser = this.loadUserByUsername(userId);
		if (dbUser.isLoggedIn())
			return;
		try {
			dataAccessLayer.updateLogin(dbUser.withLoggedIn(true));
		} catch (DataNotFoundEx e) {
			throw UncheckedException.appException(EntityName.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND, userId);
		}
	}

	public void loginUser(SecurityUser user) {
		this.login(user.getUserId());
	}

	@CacheEvict
	public void logout(String userId) {
		SecurityUser dbUser = this.loadUserByUsername(userId);
		if (dbUser.isLoggedIn()) {
			try {
				super.update(dbUser.withLoggedIn(false));
			} catch (DataNotFoundEx e) {
				throw UncheckedException.appException(EntityName.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND,
						userId);
			}
		}
	}

	public void logoutUser(SecurityUser user) {
		this.logout(user.getUserId());
	}

	public void deleteUser(SecurityUser user) {
		try {
			super.delete(user);
		} catch (DataNotFoundEx e) {
			throw UncheckedException.appException(EntityName.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND,
					user.getUsername());
		}
	}

}
