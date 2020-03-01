package com.pra.payrollmanager.base;

import java.util.List;
import java.util.stream.Collectors;

import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

abstract public class BaseServiceDTO<PK,
		DAO extends BaseDAOWithDTO<PK, DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDALWithCompanyPostfix<PK, DAO>> extends BaseServiceDAO<PK, DAO, DAL> {

	public boolean exists(DTO obj) {
		return super.exists(obj.toDAO());
	}

	public void create(DTO obj) throws DuplicateDataEx {
		create(obj.toDAO());
	}

	public void update(DTO obj) throws DataNotFoundEx {
		update(obj.toDAO());
	}

	public void delete(DTO obj) throws DataNotFoundEx {
		delete(obj.toDAO());
	}

	public List<DTO> getAllDtos() {
		return super.getAll().stream()
				.map(WithDTO::toDTO)
				.collect(Collectors.toList());
	}

	public DTO getDtoById(PK id) throws DataNotFoundEx {
		return super.getById(id).toDTO();
	}
}
