package com.pra.payrollmanager.security.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.BaseDALWithCompanyPostfix;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.util.CheckedException;

@Repository
public class SecurityUserDAL extends BaseDALWithCompanyPostfix<String, SecurityUser> {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	protected EntityName entity() {
		return EntityName.SECURITY_USER;
	}

	public boolean existsById(String key, String companyPostfix) {
		return mongoTemplate.exists(Query.query(Criteria.where("_id").is(key)), SecurityUser.class,
				entity().table() + companyPostfix);
	}

	public SecurityUser findById(String key, String companyPostfix) throws DataNotFoundEx {
		if (this.existsById(key, companyPostfix)) {
			return mongoTemplate.findById(key, SecurityUser.class, entity().table() + companyPostfix);
		} else {
			throw CheckedException.notFoundEx(entity(), String.valueOf(key));
		}
	}

	public void createSuperUser(SecurityUser superUser, String companyPostfix) {
		mongoTemplate.insert(superUser, entity().table() + companyPostfix);
	}

	public void updateLogin(SecurityUser user) throws DataNotFoundEx {
		String companyPostfix = user.getCompany().getTablePostfix();
		if (this.existsById(user.getUsername(), companyPostfix)) {
			mongoTemplate.save(user, entity().table() + companyPostfix);
		} else {
			throw CheckedException.notFoundEx(entity(), user.getUserId());
		}
	}
}
