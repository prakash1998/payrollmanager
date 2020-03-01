package com.pra.payrollmanager.base;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.config.JsonJacksonMapperService;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;

public abstract class BaseDALWithCompanyPostfixWithAuditLog<PK, DAO extends BaseAuditDAO<PK>>
		extends BaseDALWithCompanyPostfix<PK, DAO> {
	// , REPO extends BaseRepo<DAO, PK>

	public static final String AUDIT_POSTFIX = "_ODT";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private JsonJacksonMapperService mapper;

	public String auditTableName() {
		return this.tableName().concat(AUDIT_POSTFIX);
	}

	@Override
	public void update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		mongoTemplate.save(obj, this.tableName());
		this.auditExistingDAO(dbObj);
	}

	@Override
	public void delete(DAO obj) throws DataNotFoundEx {
		DAO dbObj = this.findById(obj.primaryKeyValue());
		mongoTemplate.remove(obj, this.tableName());
		this.auditExistingDAOwithDeletedFlag(dbObj);
	}

	private void auditExistingDAOwithDeletedFlag(DAO obj) {
		obj.setDeleted(true);
		this.auditExistingDAO(obj);
	}

	private void auditExistingDAO(DAO obj) {
		String jsonString = mapper.objectToJson(obj);
		Document doc = Document.parse(jsonString);
		mongoTemplate.insert(doc, this.auditTableName());
	}

}
