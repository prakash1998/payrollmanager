package com.pra.payrollmanager.base.dal.audit;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.pra.payrollmanager.base.BaseAuditDAO;
import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.translation.JsonJacksonMapperService;

public abstract class BaseDALWithCompanyPrefixWithAuditLog<PK, DAO extends BaseAuditDAO<PK>>
		extends BaseDALWithCompanyPrefix<PK, DAO> {
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
	public DAO update(DAO obj) throws DataNotFoundEx {
		DAO dbObj = super.update(obj);
		if (!dbObj.equals(obj)) {
			this.auditExistingDAO(dbObj);
		}
		return dbObj;
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
