package com.pra.payrollmanager.security.authentication.user;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CustomExceptions;
import com.pra.payrollmanager.security.authorization.ResourceFeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class SecurityUserDAL extends AuditDAL<String, SecurityUser> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.SECURITY_USER;
	}
	
	@Override
	public FeaturePermission apiPermission() {
		return ResourceFeaturePermissions.ADMIN__USERS;
	}
	
	@Override
	public void validateModification(SecurityUser dbObj, SecurityUser objToSave) {
		
	}
	
	@Override
	public SecurityUser injectAuditInfoOnCreate(SecurityUser obj) {
		return obj;
	}
	
	@Override
	public SecurityUser injectAuditInfoOnUpdate(SecurityUser dbObj, SecurityUser obj) {
		return obj;
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
		mongoTemplate().insert(superUser, tablePrefix + entity().table());
	}

	public void updateLogin(SecurityUser user) throws DataNotFoundEx {
		String tablePrefix = user.getCompany().getTablePrefix();
		if (this.existsById(user.getUsername(), tablePrefix)) {
			mongoTemplate().save(user, tablePrefix + entity().table());
		} else {
			throw CustomExceptions.notFoundEx(entity(), user.getUserId());
		}
	}
}
