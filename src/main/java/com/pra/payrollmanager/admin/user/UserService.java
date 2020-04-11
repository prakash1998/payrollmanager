package com.pra.payrollmanager.admin.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.services.next.AuditRTServiceDTO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.NotUseThisMethod;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMapDAL;

@Service
public class UserService extends AuditRTServiceDTO<String, UserDAO, UserDTO, UserDAL> {

	@Autowired
	SecurityUserService securityUserService;

	@Autowired
	UserRoleMapDAL userRoleMapDAL;

	@Override
	public List<UserDTO> getAll() {
		return super.getAll().stream()
				.map(dto -> {
					dto.setRoleIds(userRoleMapDAL.getRoleIdsForUser(dto.getUserName()));
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public UserDTO getById(String userName) throws DataNotFoundEx {
		UserDTO dto = super.getById(userName);
		dto.setRoleIds(userRoleMapDAL.getRoleIdsForUser(userName));
		return dto;
	}

	@Override
	@Transactional
	public UserDTO create(UserDTO user) throws DuplicateDataEx {
		securityUserService.create(user.toSecurityUser());
		UserDTO savedObj = super.create(user);
		userRoleMapDAL.replaceEntries(user.getUserName(), user.getRoleIds());
		return savedObj;
	}

	@Transactional
	@CacheEvict(cacheNames = CacheNameStore.SECURITY_USER_PERMISSION_STORE, allEntries = true)
	public UserDTO updateUser(UserDTO user) throws DataNotFoundEx, DuplicateDataEx {
		UserDTO updatedObj = super.update(user);
		userRoleMapDAL.replaceEntries(user.getUserName(), user.getRoleIds());
		return updatedObj;
	}

	@Override
	public UserDTO update(UserDTO user) {
		throw new NotUseThisMethod();
	}

	@Override
	@Transactional
	public UserDTO delete(UserDTO user) throws DataNotFoundEx {
		securityUserService.deleteUser(user.toSecurityUser());
		UserDTO deletedUser = super.delete(user);
		userRoleMapDAL.deleteEntriesByUserId(user.getUserName());
		return deletedUser;
	}

	public UserDTO findByFirstName(String name) throws DataNotFoundEx {
		return dataAccessLayer.getByFirstName(name).toDTO();
	}

	@Override
	public String mqTopic() {
		return KafkaTopics.USERS;
	}

	@Override
	public Set<String> targetedUserIds() {
		return new HashSet<>();
	}

}
