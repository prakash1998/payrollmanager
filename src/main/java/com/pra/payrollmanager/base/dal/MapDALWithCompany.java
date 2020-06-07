package com.pra.payrollmanager.base.dal;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BaseMapDAO;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.utils.QueryUtils;

abstract public class MapDALWithCompany<KEY extends Serializable,
		VAL extends Serializable,
		DAO extends BaseMapDAO<KEY, VAL>>
		extends DALWithCompany<String, DAO> {

	@SuppressWarnings("unchecked")
	@Override
	public Class<DAO> daoClazz() {
		// specified in each class in hierarchy because we can access type parameter
		// class in immediate parent only
		Type sooper = getClass().getGenericSuperclass();
		return (Class<DAO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[2];
	}

	public Set<VAL> getValuesForKey(KEY key) {
		return super.findWith(QueryUtils.startsWith("_id", key.toString() + BaseMapDAO.MAP_PK_JOIN_STR))
				.stream().map(map -> map.getValue())
				.collect(Collectors.toSet());
	}

	public abstract DAO getInstance(KEY key, VAL value);

	@Transactional
	public void replaceEntries(KEY key, Set<VAL> values) throws DuplicateDataEx {
		this.deleteEntriesByKey(key);
		// super.deleteWith(QueryUtils.startsWith("_id", key.toString() +
		// BaseMapDAO.MAP_PK_JOIN_STR)
		// .addCriteria(Criteria.where("value").nin(values)));
		List<DAO> rolePermissionMaps = values.stream()
				.map(val -> getInstance(key, val))
				.collect(Collectors.toList());
		super.createMultiple(rolePermissionMaps);
	}

	public void deleteEntriesByKey(KEY key) {
		super.deleteWith(QueryUtils.startsWith("_id", key.toString() + BaseMapDAO.MAP_PK_JOIN_STR));
	}

	public void deleteEntriesByValue(VAL value) {
		super.deleteWith(QueryUtils.endsWith("_id", BaseMapDAO.MAP_PK_JOIN_STR + value.toString()));
	}
}
