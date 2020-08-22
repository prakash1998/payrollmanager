package com.pra.payrollmanager.base.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.utils.ObjectUtils;

public interface BaseServiceDTO<PK,
		DAO extends BaseDAO<PK>,
		DTO,
		DAL extends BaseDAL<PK, DAO>>
		extends BaseService<PK, DAO, DTO, DAL> {

	ModelMapper modelMapper();

	Class<DTO> dtoClazz();

	default DTO toDTO(DAO dao) {
		return modelMapper().map(dao, dtoClazz());
	}

	default DAO toDAO(DTO dto) {
		return modelMapper().map(dto, dataAccessLayer().daoClazz());
	}

	default DTO postProcessGet(DAO obj) {
		return toDTO(obj);
	}

	default List<DTO> postProcessMultiGet(List<DAO> objList) {
		return objList.stream()
				.map(obj -> toDTO(obj))
				.collect(Collectors.toList());
	}

	default DTO postProcessSave(DTO dtoToSave, DAO objFromDB) {
		return ObjectUtils.copyInNullOrEmpty(dtoToSave, toDTO(objFromDB));
	}

	@Override
	default boolean exists(DTO obj) {
		return dataAccessLayer().exists(toDAO(obj));
	}

	@Override
	default DTO getById(PK id) throws DataNotFoundEx, AnyThrowable {
		return postProcessGet(dataAccessLayer().findById(id));
	}

	@Override
	default List<DTO> getByIds(Set<PK> ids) {
		return postProcessMultiGet(dataAccessLayer().findByIds(ids));
	}

	@Override
	default List<DTO> getAll() {
		return postProcessMultiGet(dataAccessLayer().findAll());
	}

	@Override
	default DTO create(DTO obj) throws DuplicateDataEx, AnyThrowable {
		return postProcessSave(obj, dataAccessLayer().create(toDAO(obj)));
	}

	@Override
	default DTO update(DTO obj) throws DataNotFoundEx, AnyThrowable {
		return postProcessSave(obj, dataAccessLayer().update(toDAO(obj)));
	}

	// @Override
	// default DTO upsert(DTO obj) throws AnyThrowable {
	// return dataAccessLayer().upsert(obj.toDAO()).toDTO();
	// }

	@Override
	default DTO delete(DTO obj) throws DataNotFoundEx, AnyThrowable {
		return toDTO(dataAccessLayer().delete(toDAO(obj)));
	}

	@Override
	default DTO deleteById(PK key) throws DataNotFoundEx, AnyThrowable {
		return toDTO(dataAccessLayer().deleteById(key));
	}

}
