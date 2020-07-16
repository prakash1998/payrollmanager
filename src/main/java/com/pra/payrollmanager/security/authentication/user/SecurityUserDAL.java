package com.pra.payrollmanager.security.authentication.user;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;
import com.pra.payrollmanager.utils.DBQueryUtils;

@Repository
public class SecurityUserDAL extends AuditDAL<String, SecurityUser> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.SECURITY_USER;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.ADMIN__USERS;
	}

	@Override
	public boolean modificationValid(SecurityUser dbObj, SecurityUser objToSave) {
		return true;
	}

	public boolean existsById(String key, String tablePrefix) {
		return mongoTemplate().exists(Query.query(Criteria.where("_id").is(key)), SecurityUser.class,
				tablePrefix + entity().table());
	}

	public SecurityUser findById(String key, String tablePrefix) throws DataNotFoundEx {
		if (this.existsById(key, tablePrefix)) {
			return mongoTemplate().findById(key, SecurityUser.class, tablePrefix + entity().table());
		} else {
			throw CustomExceptions.notFoundEx(entity(), String.valueOf(key));
		}
	}

	public void createSuperUser(SecurityUser superUser, String tablePrefix) {
		mongoTemplate().insert(setAuditInfoOnCreate(superUser), tablePrefix + entity().table());
	}

	public void logInOnToken(SecurityUser user) throws DataNotFoundEx {
		String tablePrefix = user.getCompany().getTablePrefix();
		if (this.existsById(user.getUsername(), tablePrefix)) {
			String tableName = tablePrefix + entity().table();
			Update update = Update.update("loggedIn", true);
			mongoTemplate().updateFirst(DBQueryUtils.idEqualsQuery(user.getUsername()), update, tableName);
			// mongoTemplate().save(user, tablePrefix + entity().table());
		} else {
			throw CustomExceptions.notFoundEx(entity(), user.getUserId());
		}
	}

	public void logIn(String userName) throws DataNotFoundEx {
		if (this.existsById(userName)) {
			Update update = Update.update("loggedIn", true);
			this.applyPatch(userName, update);
		} else {
			throw CustomExceptions.notFoundEx(entity(), userName);
		}
	}

	public void logOut(String userName) throws DataNotFoundEx {
		if (this.existsById(userName)) {
			Update update = Update.update("loggedIn", false);
			this.applyPatch(userName, update);
		} else {
			throw CustomExceptions.notFoundEx(entity(), userName);
		}
	}
}
