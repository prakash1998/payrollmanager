package com.pra.payrollmanager.admin.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.BaseServiceAuditDTO;
import com.pra.payrollmanager.constants.CacheNameStore;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.NotUseThisMethod;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMapDAL;

@Service
public class UserService extends BaseServiceAuditDTO<String, UserDAO, UserDTO, UserDAL> {

	@Autowired
	SecurityUserService securityUserService;

	@Autowired
	UserRoleMapDAL userRoleMapDAL;

	@Override
	public List<UserDTO> getAllDtos() {
		return super.getAllDtos().stream()
				.map(dto -> {
					dto.setRoleIds(userRoleMapDAL.getRoleIdsForUser(dto.getUserName()));
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public UserDTO getDtoById(String userName) throws DataNotFoundEx {
		UserDTO dto = super.getDtoById(userName);
		dto.setRoleIds(userRoleMapDAL.getRoleIdsForUser(userName));
		return dto;
	}

	@Override
	@Transactional
	public void create(UserDTO user) throws DuplicateDataEx {
		securityUserService.create(user.toSecurityUser());
		super.create(user.toDAO());
		userRoleMapDAL.replaceEntries(user.getUserName(), user.getRoleIds());
	}

	@Transactional
	@CacheEvict(cacheNames = CacheNameStore.SECURITY_USER_PERMISSION_STORE, allEntries = true)
	public void updateUser(UserDTO user) throws DataNotFoundEx, DuplicateDataEx {
		super.update(user);
		userRoleMapDAL.replaceEntries(user.getUserName(), user.getRoleIds());
	}

	@Override
	public void update(UserDTO user) {
		throw new NotUseThisMethod();
	}

	@Override
	@Transactional
	public void delete(UserDTO user) throws DataNotFoundEx {
		securityUserService.deleteUser(user.toSecurityUser());
		super.delete(user.toDAO());
		userRoleMapDAL.deleteEntriesByUserId(user.getUserName());
	}

	public UserDTO findByFirstName(String name) throws DataNotFoundEx {
		return dataAccessLayer.getByFirstName(name).toDTO();
	}

}
