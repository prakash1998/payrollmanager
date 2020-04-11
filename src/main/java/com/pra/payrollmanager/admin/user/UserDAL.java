package com.pra.payrollmanager.admin.user;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.next.AuditDALWithCompany;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CheckedException;

@Repository
public class UserDAL extends AuditDALWithCompany<String, UserDAO> {

	public UserDAO getByFirstName(String name) throws DataNotFoundEx {
		UserDAO result = super.findOneWith(Query.query(Criteria.where("firstName").is(name)));
		if (result == null)
			throw CheckedException.notFoundEx(entity(), "name");
		return result;
	}

	@Override
	public EntityName entity() {
		return EntityName.USER;
	}



}
