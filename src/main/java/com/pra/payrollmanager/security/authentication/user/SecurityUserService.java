package com.pra.payrollmanager.security.authentication.user;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.apputils.cachemanager.AppCacheService;
import com.pra.payrollmanager.base.services.AuditServiceDAO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.company.SecurityCompanyService;

@Service
public class SecurityUserService extends AuditServiceDAO<String, SecurityUser, SecurityUserDAL>
		implements UserDetailsService {

	@Autowired
	BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	SecurityCompanyService securityCompanyService;

	@Autowired
	AppCacheService cacheService;

	public SecurityUser loadUserByUsername(String userId) {
		return cacheService.cached(CacheNameStore.SECURITY_USER_STORE, userId, (key) -> {
			Pair<String, String> companyUserPair = UserIdConversionUtils.splitCompanyUser(userId);
			SecurityCompany company = securityCompanyService.loadCompanyById(companyUserPair.getValue0());
			System.out.println("fetching user from db. .......");
			return dataAccessLayer.findById(companyUserPair.getValue1(), company.getTablePrefix())
					.setUserId(userId)
					.setCompany(company);
		});
	}

	private SecurityUser withEncodedPassword(SecurityUser user) {
		return user.setPassword(bcryptEncoder.encode(user.getPassword()));
	}

	@Override
	public SecurityUser create(SecurityUser user) {
		return super.create(this.withEncodedPassword(user));
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

	public void login(String userId) {
		cacheService.removeByKey(CacheNameStore.SECURITY_USER_STORE, userId);
		SecurityUser userWithCompany = this.loadUserByUsername(userId);
		if (userWithCompany.getLoggedIn())
			return;
		dataAccessLayer.logInOnToken(userWithCompany);
	}

	public void loginUser(SecurityUser user) {
		this.login(user.getUserId());
	}

	public void logout(String userId) {
		cacheService.removeByKey(CacheNameStore.SECURITY_USER_STORE, userId);
		SecurityUser userWithCompany = this.loadUserByUsername(userId);
		if (userWithCompany.getLoggedIn()) {
			dataAccessLayer.logOut(userWithCompany.getUsername());
		}
	}

	public void logoutUser(SecurityUser user) {
		this.logout(user.getUserId());
	}

	public void deleteUser(SecurityUser user) {
		super.delete(user);
	}

}
