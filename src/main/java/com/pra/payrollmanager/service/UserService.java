package com.pra.payrollmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.dal.UserDAL;
import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.dto.UserDTO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.security.authentication.service.SecurityUserService;
import com.pra.payrollmanager.service.base.BaseServiceDTO;

@Service
public class UserService extends BaseServiceDTO<String,UserDAO, UserDTO, UserDAL>{
	
	@Autowired
	SecurityUserService securityUserService;

	@Override
	@Transactional
	public void create(UserDTO user) throws DuplicateDataEx {
		securityUserService.createUser(user.toSecurityUser());
		super.create(user.toDAO());
	}
	
	@Override
	@Transactional
	public void delete(UserDTO user) throws DataNotFoundEx {
		securityUserService.deleteUser(user.toSecurityUser());
		super.delete(user.toDAO());
	}
	
	
	public UserDTO findByFirstName(String name) throws DataNotFoundEx {
		return dataAccessLayer.getByFirstName(name).toDTO();
	}

}
