package com.pra.payrollmanager.admin.common.user;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.apputils.cachemanager.AppCacheService;
import com.pra.payrollmanager.base.services.AuditServiceDTO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMapDAL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService extends AuditServiceDTO<String, UserDAO, UserDTO, UserDAL> {

	private final SecurityUserService securityUserService;

	private final UserRoleMapDAL userRoleMapDAL;

	@Autowired
	AppCacheService cacheService;

	@Override
	public UserDTO postProcessGet(UserDAO obj) {
		UserDTO dto = super.postProcessGet(obj);
		dto.setRoleIds(userRoleMapDAL.getValuesForKey(dto.getUserName()));
		return dto;
	}

	@Override
	public List<UserDTO> postProcessMultiGet(List<UserDAO> objList) {

		Map<String, Set<String>> userRoleMap = userRoleMapDAL.getValuesForAllKeys();

		return objList.stream().map(userDao -> {
			UserDTO user = toDTO(userDao);
			user.setRoleIds(userRoleMap.getOrDefault(user.getUserName(), new HashSet<>()));
			return user;
		}).collect(Collectors.toList());
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
	@Override
	public UserDTO update(UserDTO user) throws DataNotFoundEx, AnyThrowable {

		cacheService.clearCaches(CacheNameStore.USER_PERMISSION_STORE,
				CacheNameStore.USER_ENDPOINT_STORE,
				CacheNameStore.USER_RESOURCE_STORE);

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
		return toDTO(dataAccessLayer.getByFirstName(name));
	}

//	@Override
//	public String mqTopic() {
//		return KafkaTopics.USERS;
//	}

}
