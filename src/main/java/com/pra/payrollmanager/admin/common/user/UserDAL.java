package com.pra.payrollmanager.admin.common.user;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class UserDAL extends AuditDAL<String, UserDAO> {

	public UserDAO getByFirstName(String name) throws DataNotFoundEx {
		UserDAO result = super.findOneWith(Query.query(Criteria.where("firstName").is(name)));
		if (result == null)
			throw CustomExceptions.notFoundEx(entity(), "name");
		return result;
	}

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.USER;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.ADMIN__USERS;
	}

}
