package com.pra.payrollmanager.base.services.next;

import java.util.List;
import java.util.stream.Collectors;

import com.pra.payrollmanager.base.BaseDAOWithDTO;
import com.pra.payrollmanager.base.BaseDTO;
import com.pra.payrollmanager.base.WithDTO;
import com.pra.payrollmanager.base.dal.next.DataStoreService;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

public interface NewBaseServiceDTO<PK,
		DAO extends BaseDAOWithDTO<PK, DTO>,
		DTO extends BaseDTO<DAO>,
		DAL extends DataStoreService<PK, DAO>>
		extends NewBaseService<PK, DAO, DTO, DAL> {

	@Override
	default boolean exists(DTO obj) {
		return dataAccessLayer().exists(obj.toDAO());
	}

	@Override
	default DTO getById(PK id) throws DataNotFoundEx {
		return dataAccessLayer().findById(id).toDTO();
	}

	@Override
	default List<DTO> getAll() {
		return dataAccessLayer().findAll().stream()
				.map(WithDTO::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	default DTO create(DTO obj) throws DuplicateDataEx {
		return dataAccessLayer().create(obj.toDAO()).toDTO();
	}

	@Override
	default DTO update(DTO obj) throws DataNotFoundEx {
		return dataAccessLayer().update(obj.toDAO()).toDTO();
	}

	@Override
	default DTO delete(DTO obj) throws DataNotFoundEx {
		return dataAccessLayer().delete(obj.toDAO()).toDTO();
	}

	@Override
	default DTO deleteById(PK key) throws DataNotFoundEx {
		return dataAccessLayer().deleteById(key).toDTO();
	}

}
