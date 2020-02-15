package com.pra.payrollmanager.dto.base;

import com.pra.payrollmanager.dao.base.BaseDAO;

abstract public class BaseDTO<DAO extends BaseDAO<?>> {

//	Map<String, String> extra = new HashMap<String, String>();

	abstract public DAO toDAO();

//	@JsonAnyGetter
//	String getExtra(String key) {
//		return extra.get(key);
//	}

//	@JsonAnySetter
//	void setExtra(String key, String value) {
//		extra.put(key, value);
//	}
}
