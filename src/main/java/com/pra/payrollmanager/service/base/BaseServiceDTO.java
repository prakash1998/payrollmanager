package com.pra.payrollmanager.service.base;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pra.payrollmanager.dal.base.BaseDAL;
import com.pra.payrollmanager.dao.base.BaseDAOWithDTO;
import com.pra.payrollmanager.dao.base.WithDTO;
import com.pra.payrollmanager.dto.base.BaseDTO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

import lombok.Setter;

abstract public class BaseServiceDTO<PK,
		DAO extends BaseDAOWithDTO<PK, DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends BaseDAL<PK, DAO, ?>> {

	@Autowired
	@Setter
	protected DAL dataAccessLayer;

	public void create(DTO obj) throws DuplicateDataEx {
		create(obj.toDAO());
	}

	protected void create(DAO obj) throws DuplicateDataEx {
		dataAccessLayer.create(obj);
	}

	public void update(DTO obj) throws DataNotFoundEx {
		update(obj.toDAO());
	}

	protected void update(DAO obj) throws DataNotFoundEx {
		dataAccessLayer.update(obj);
	}

	public void delete(DTO obj) throws DataNotFoundEx {
		delete(obj.toDAO());
	}

	protected void delete(DAO obj) throws DataNotFoundEx {
		dataAccessLayer.delete(obj);
	}

	public List<DTO> getAll() {
		return dataAccessLayer.getAll().stream()
				.map(WithDTO::toDTO)
				.collect(Collectors.toList());
	}

	public DTO getById(PK id) throws DataNotFoundEx {
		return dataAccessLayer.findById(id).toDTO();
	}
}
