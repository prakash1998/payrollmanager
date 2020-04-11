package com.pra.payrollmanager.security.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CheckedException;

@Repository
public class SecurityUserDAL extends BaseDALWithCompanyPrefix<String, SecurityUser> {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	protected EntityName entity() {
		return EntityName.SECURITY_USER;
	}

	public boolean existsById(String key, String tablePrefix) {
		return mongoTemplate.exists(Query.query(Criteria.where("_id").is(key)), SecurityUser.class,
				tablePrefix + entity().table());
	}

	public SecurityUser findById(String key, String tablePrefix) throws DataNotFoundEx {
		if (this.existsById(key, tablePrefix)) {
			return mongoTemplate.findById(key, SecurityUser.class, tablePrefix + entity().table());
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(key));
		}
	}

	public void createSuperUser(SecurityUser superUser, String tablePrefix) {
		mongoTemplate.insert(superUser, tablePrefix + entity().table() );
	}

	public void updateLogin(SecurityUser user) throws DataNotFoundEx {
		String tablePrefix = user.getCompany().getTablePrefix();
		if (this.existsById(user.getUsername(), tablePrefix)) {
			mongoTemplate.save(user, tablePrefix + entity().table());
		} else {
			throw CheckedException.notFoundEx(entity(), user.getUserId());
		}
	}
}
