package com.pra.payrollmanager.utils;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public interface DBQueryUtils {
	
	public static Query idEqualsQuery(Object value) {
		return Query.query(idEqualsCriteria(value));
	}

	public static Query equalsQuery(String field, Object value) {
		return Query.query(Criteria.where(field).is(value));
	}
	
	public static Query idInQuery(Object... values) {
		return Query.query(idInCriteria(values));
	}

	public static Query inQuery(String field, Object... values) {
		return Query.query(Criteria.where(field).in(values));
	}
	
	public static Criteria idEqualsCriteria(Object value) {
		return whereId().is(value);
	}
	
	public static Criteria idInCriteria(Object... values) {
		return whereId().in(values);
	}
	
	public static Criteria whereId() {
		return Criteria.where("_id");
	}

	public static Query startsWith(String field, String text) {
		return Query.query(startsWithCriteria(field, text));
	}
	
	public static Criteria startsWithCriteria(String field, String text) {
		return Criteria.where(field).regex("^" + RegExUtils.regExLiteral(text));
	}

	public static Query endsWith(String field, String text) {
		return Query.query(endsWithCriteria(field, text));
	}
	
	public static Criteria endsWithCriteria(String field, String text) {
		return Criteria.where(field).regex(RegExUtils.regExLiteral(text) + "$");
	}

}
