package com.pra.payrollmanager.base.data;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public abstract class BaseMapDAO<KEY extends Serializable, VAL extends Serializable> implements BaseDAO<ObjectId> {

	public static final String MAP_PK_JOIN_STR = "``";
	
	public static final String KEY_FIELD = "_k";

	public static final String VALUE_FIELD = "_v";

	/**
	 * 
	 */
	private static final long serialVersionUID = -2341684790259937907L;

	@Id
	private ObjectId id;

//	@Transient
	@Field(KEY_FIELD)
	private KEY key;
//	@Transient
	@Field(VALUE_FIELD)
	private VAL value;
	
	
//	public void setRelationKey(String value) {
//		this.relationKey = value;
//		Object[] pair = value.split(MAP_PK_JOIN_STR);
//		this.key = (KEY)pair[0];
//		this.value = (VAL)pair[1];
//	}
	
//	public static <P,Q> String primaryKeyFrom(P key, Q value) {
//		return String.format("%s%s%s", key.toString(), MAP_PK_JOIN_STR, value.toString());
//	}

	public BaseMapDAO(KEY key, VAL value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}

}
