package com.pra.payrollmanager.admin.common.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.services.AuditRTServiceDTO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMapDAL;

@Service
public class UserService extends AuditRTServiceDTO<String, UserDAO, UserDTO, UserDAL> {

	@Autowired
	SecurityUserService securityUserService;

	@Autowired
	UserRoleMapDAL userRoleMapDAL;

	@Override
	public UserDTO postProcessGet(UserDAO obj) {
		UserDTO dto = super.postProcessGet(obj);
		dto.setRoleIds(userRoleMapDAL.getValuesForKey(dto.getUserName()));
		return dto;
	}

	// @Override
	// public UserDTO postProcessSave(UserDTO dtoToSave, UserDAO objFromDB) {
	// UserDTO savedObj = super.postProcessSave(dtoToSave, objFromDB);
	// savedObj.setRoleIds(dtoToSave.getRoleIds());
	// return savedObj;
	// }

	@Override
	@Transactional
	public UserDTO create(UserDTO user) throws DuplicateDataEx, AnyThrowable {
		securityUserService.create(user.toSecurityUser());
		userRoleMapDAL.replaceEntries(user.getUserName(), user.getRoleIds());
		return super.create(user);
	}

	@Transactional
	@CacheEvict(cacheNames = CacheNameStore.SECURITY_USER_PERMISSION_STORE, allEntries = true)
	@Override
	public UserDTO update(UserDTO user) throws DataNotFoundEx, AnyThrowable {
		userRoleMapDAL.replaceEntries(user.getUserName(), user.getRoleIds());
		return super.update(user);
	}

	@Override
	@Transactional
	public UserDTO delete(UserDTO user) throws DataNotFoundEx, AnyThrowable {
		securityUserService.deleteUser(user.toSecurityUser());
		userRoleMapDAL.deleteEntriesByValue(user.getUserName());
		return super.delete(user);
	}

	public UserDTO findByFirstName(String name) throws DataNotFoundEx {
		return dataAccessLayer.getByFirstName(name).toDTO();
	}

	@Override
	public String mqTopic() {
		return KafkaTopics.USERS;
	}

}
