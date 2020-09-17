package com.pra.payrollmanager.base.dal;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.data.BaseMapDAO;
import com.pra.payrollmanager.base.data.BulkOp;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.utils.DBQueryUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract public class MapDAL<KEY extends Serializable,
		VAL extends Serializable,
		DAO extends BaseMapDAO<KEY, VAL>>
		extends AbstractDAL<ObjectId, DAO> {

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
		// return super.findWith(DBQueryUtils.startsWith("_id", key.toString() +
		// BaseMapDAO.MAP_PK_JOIN_STR))
		// .stream().map(map -> map.getValue())
		// .collect(Collectors.toSet());
		return super.findWith(DBQueryUtils.equalsQuery(BaseMapDAO.KEY_FIELD, key))
				.stream().map(map -> map.getValue())
				.collect(Collectors.toSet());

	}

	public Map<KEY, Set<VAL>> getValuesForAllKeys() {
		return super.findAll()
				.stream()
				.collect(Collectors.groupingBy(item -> item.getKey(),
						Collectors.mapping(item -> item.getValue(), Collectors.toSet())));
	}

	public Map<KEY, Set<VAL>> getValuesForKeys(Collection<KEY> keyList) {
		return super.findWith(DBQueryUtils.inArrayQuery(BaseMapDAO.KEY_FIELD, keyList.toArray()))
				.stream()
				.collect(Collectors.groupingBy(item -> item.getKey(),
						Collectors.mapping(item -> item.getValue(), Collectors.toSet())));
	}

	public Set<KEY> getKeysForValue(VAL value) {
		// return super.findWith(DBQueryUtils.endsWith("_id", BaseMapDAO.MAP_PK_JOIN_STR
		// + value.toString()))
		// .stream().map(map -> map.getKey())
		// .collect(Collectors.toSet());
		return super.findWith(DBQueryUtils.equalsQuery(BaseMapDAO.VALUE_FIELD, value))
				.stream().map(map -> map.getKey())
				.collect(Collectors.toSet());
	}

	public Map<VAL, Set<KEY>> getKeysForValues(Collection<KEY> valueList) {
		return super.findWith(DBQueryUtils.inArrayQuery(BaseMapDAO.VALUE_FIELD, valueList.toArray()))
				.stream()
				.collect(Collectors.groupingBy(item -> item.getValue(),
						Collectors.mapping(item -> item.getKey(), Collectors.toSet())));
	}

	public Map<VAL, Set<KEY>> getKeysForAllValues() {
		return super.findAll()
				.stream()
				.collect(Collectors.groupingBy(item -> item.getValue(),
						Collectors.mapping(item -> item.getKey(), Collectors.toSet())));
	}

	public abstract DAO getInstance(KEY key, VAL value);

	@Transactional
	public void replaceEntries(KEY key, Set<VAL> values) throws DuplicateDataEx {
		this.deleteEntriesByKey(key);
		List<DAO> dataMaps = values.stream()
				.map(val -> getInstance(key, val))
				.collect(Collectors.toList());
		super.bulkOp(BulkOp.fromAdded(dataMaps));
	}

	// public void updateSet(KEY key,BulkOp<VAL> changedValues) throws
	// DuplicateDataEx {
	// Set<String> removed = changedValues.getRemoved().stream()
	// .map(val -> BaseMapDAO.primaryKeyFrom(key, val))
	// .collect(Collectors.toSet());
	// if(!removed.isEmpty()) {
	// super.deleteByIds(removed);
	// }
	// List<DAO> dataMaps = changedValues.getAdded().stream()
	// .map(val -> getInstance(key, val))
	// .collect(Collectors.toList());
	// if(!dataMaps.isEmpty())
	// super.insert(dataMaps);
	// }

	public void deleteEntriesByKey(KEY key) {
		// super.deleteWith(DBQueryUtils.startsWith("_id", key.toString() +
		// BaseMapDAO.MAP_PK_JOIN_STR));
		super.deleteWith(DBQueryUtils.equalsQuery(BaseMapDAO.KEY_FIELD, key));
	}

	public void deleteEntriesByValue(VAL value) {
		// super.deleteWith(DBQueryUtils.endsWith("_id", BaseMapDAO.MAP_PK_JOIN_STR +
		// value.toString()));
		super.deleteWith(DBQueryUtils.equalsQuery(BaseMapDAO.VALUE_FIELD, value));
	}
}
