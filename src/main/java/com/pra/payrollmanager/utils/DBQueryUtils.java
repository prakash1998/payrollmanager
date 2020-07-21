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

	public static Query idInArrayQuery(Object[] array) {
		return Query.query(idInValuesCriteria(array));
	}

	public static Query idInValuesQuery(Object... values) {
		return Query.query(idInValuesCriteria(values));
	}

	public static Query inArrayQuery(String field, Object[] array) {
		return inValuesQuery(field, array);
	}

	public static Query inValuesQuery(String field, Object... values) {
		return Query.query(inValuesCriteria(field, values));
	}

	public static Criteria inArrayCriteria(String field, Object[] array) {
		return inValuesCriteria(field, array);
	}

	public static Criteria inValuesCriteria(String field, Object... values) {
		return Criteria.where(field).in(values);
	}

	public static Criteria idEqualsCriteria(Object value) {
		return whereId().is(value);
	}

	public static Criteria idInArrayCriteria(Object[] array) {
		return idInValuesCriteria(array);
	}

	public static Criteria idInValuesCriteria(Object... values) {
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
