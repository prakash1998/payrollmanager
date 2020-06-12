package com.pra.payrollmanager.base.services;

import org.modelmapper.ModelMapper;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;

public interface DTOSupport<DAO extends BaseDAO<?>,DTO,DAL extends BaseDAL<?, DAO>> {
	
	DAL dataAccessLayer();
	
	ModelMapper modelMapper();
	
	Class<DTO> dtoClazz();

	default DTO toDTO(DAO dao) {
		return modelMapper().map(dao, dtoClazz());
	}
	
	default DAO toDAO(DTO dto) {
		return modelMapper().map(dto,dataAccessLayer().daoClazz());
	}

}
