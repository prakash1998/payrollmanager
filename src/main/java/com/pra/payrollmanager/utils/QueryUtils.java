package com.pra.payrollmanager.utils;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public interface QueryUtils {

	public static Query startsWith(String field, String text) {
		return Query.query(Criteria.where(field).regex("^" + RegExUtils.regExLiteral(text)));
	}

	public static Query endsWith(String field, String text) {
		return Query.query(Criteria.where(field).regex(RegExUtils.regExLiteral(text) + "$"));
	}

}
