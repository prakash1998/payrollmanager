package com.pra.payrollmanager.dal;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.dal.base.BaseDAL;
import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.exception.util.EntityType;
import com.pra.payrollmanager.repository.UserRepo;

@Repository
public class UserDAL extends BaseDAL<String, UserDAO, UserRepo> {

	public UserDAO getByFirstName(String name) throws DataNotFoundEx {
		UserDAO result = repo.findByFirstName(name);

		if (result == null)
			throw CheckedException.notFoundEx(entityType(), "name");

		return result;
	}

	@Override
	protected EntityType entityType() {
		return EntityType.USER;
	}
}
