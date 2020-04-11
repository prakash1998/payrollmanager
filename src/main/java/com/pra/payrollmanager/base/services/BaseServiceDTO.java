package com.pra.payrollmanager.base.services;

import java.util.List;
import java.util.stream.Collectors;

import com.pra.payrollmanager.base.BaseDAOWithDTO;
import com.pra.payrollmanager.base.BaseDTO;
import com.pra.payrollmanager.base.WithDTO;
import com.pra.payrollmanager.base.dal.BaseDALWithCompanyPrefix;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

abstract public class BaseServiceDTO<PK,
		DAO extends BaseDAOWithDTO<PK, DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDALWithCompanyPrefix<PK, DAO>> extends BaseServiceDAO<PK, DAO, DAL> {

	public boolean exists(DTO obj) {
		return super.exists(obj.toDAO());
	}

	public List<DTO> getAllDtos() {
		return super.getAll().stream()
				.map(WithDTO::toDTO)
				.collect(Collectors.toList());
	}

	public DTO getDtoById(PK id) throws DataNotFoundEx {
		return super.getById(id).toDTO();
	}

	public DTO create(DTO obj) throws DuplicateDataEx {
		return super.create(obj.toDAO()).toDTO();
	}

	public DTO update(DTO obj) throws DataNotFoundEx {
		return super.update(obj.toDAO()).toDTO();
	}

	public void delete(DTO obj) throws DataNotFoundEx {
		super.delete(obj.toDAO());
	}

}
