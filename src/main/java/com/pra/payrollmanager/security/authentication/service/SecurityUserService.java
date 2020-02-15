package com.pra.payrollmanager.security.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.exception.util.EntityType;
import com.pra.payrollmanager.exception.util.ExceptionType;
import com.pra.payrollmanager.exception.util.UncheckedException;
import com.pra.payrollmanager.security.authentication.dao.SecurityUser;
import com.pra.payrollmanager.security.authentication.dto.SecurityUserDTO;
import com.pra.payrollmanager.security.authentication.repo.SecurityUserRepo;

@Service
@CacheConfig(cacheNames = CacheNameStore.SECURITY_USER_STORE)
public class SecurityUserService implements UserDetailsService {

	@Autowired
	SecurityUserRepo userRepo;

	@Autowired
	BCryptPasswordEncoder bcryptEncoder;

	public boolean userExists(String userName) {
		Optional<SecurityUser> user = userRepo.findById(userName);
		return user.isPresent();
	}

	@Cacheable
	public SecurityUser loadUserByUsername(String userName) {
		System.out.println("fetching user from db........");
		Optional<SecurityUser> user = userRepo.findById(userName);
		if (user.isPresent()) {
			return user.get();
		}
		throw UncheckedException.appException(EntityType.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND, userName);
	}

	public void createUser(SecurityUser user) {
		Optional<SecurityUser> existingUser = userRepo.findById(user.getUsername());

		if (existingUser.isPresent())
			throw UncheckedException.appException(EntityType.SECURITY_USER, ExceptionType.DUPLICATE_ENTITY,
					user.getUsername());

		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}

	public void updateUserPassword(SecurityUserDTO user) throws CredentialNotMatchedEx {
		Optional<SecurityUser> existingUser = userRepo.findById(user.getUserName());
		if (existingUser.isPresent()) {
			SecurityUser dbUser = existingUser.get();
			if (!dbUser.getPassword().equals(user.getOldPassword()))
				throw CheckedException.wrongCredentialEx(EntityType.SECURITY_USER, user.getUserName());
			dbUser.setPassword(bcryptEncoder.encode(user.getNewPassword()));
			userRepo.save(dbUser);
		} else {
			throw UncheckedException.appException(EntityType.SECURITY_USER, ExceptionType.ENTITY_NOT_FOUND,
					user.getUserName());
		}
	}

	public void login(SecurityUser user) {
		if (user.isLoggedIn())
			return;
		user.setLoggedIn(true);
		userRepo.save(user);
	}

	@CacheEvict
	public void logOutUser(String userName) {
		SecurityUser dbUser = this.loadUserByUsername(userName);
		if (dbUser.isLoggedIn()) {
			dbUser.setLoggedIn(false);
			userRepo.save(dbUser);
		}
	}

	public void logOut(SecurityUser user) {
		if (user.isLoggedIn()) {
			user.setLoggedIn(false);
			userRepo.save(user);
		}
	}

	public void deleteUser(SecurityUser user) {
		userRepo.delete(user);
	}

}
