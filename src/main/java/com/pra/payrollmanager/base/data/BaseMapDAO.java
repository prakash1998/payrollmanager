package com.pra.payrollmanager.base.data;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Getter;

public abstract class BaseMapDAO<KEY extends Serializable, VAL extends Serializable> implements BaseDAO<String> {

	public static final String MAP_PK_JOIN_STR = "``";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2341684790259937907L;

	@Id
	private String relationKey;

	@Getter
	private KEY key;
	@Getter
	private VAL value;

	public BaseMapDAO(KEY key, VAL value) {
		this.relationKey = String.format("%s%s%s", key.toString(), MAP_PK_JOIN_STR, value.toString());
		this.key = key;
		this.value = value;
	}

	@Override
	public String primaryKeyValue() {
		return relationKey;
	}

}
