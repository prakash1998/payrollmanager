package com.pra.payrollmanager.dal;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.dal.base.BaseDAL;
import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CheckedException;
import com.pra.payrollmanager.exception.util.EntityType;
import com.pra.payrollmanager.repository.UserRepo;

@Repository
public class UserDAL extends BaseDAL<String, UserDAO, UserRepo> {
	
	public static final String USERS_TABLE = "USERS";
	
	@Override
	protected String tableName() {
		return USERS_TABLE;
	}

	public UserDAO getByFirstName(String name) throws DataNotFoundEx {
		UserDAO result = mongoTemplate.findOne(Query.query(Criteria.where("firstName").is(name)), UserDAO.class);
		if (result == null)
			throw CheckedException.notFoundEx(entityType(), "name");
		return result;
	}

	@Override
	protected EntityType entityType() {
		return EntityType.USER;
	}
	
}
