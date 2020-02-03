package com.pra.payrollmanager.utils.basetest;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.pra.payrollmanager.dao.base.BaseDAO;
import com.pra.payrollmanager.dao.base.BaseDAOWithDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseTestDAO implements BaseDAOWithDTO<Integer,BaseTestDTO>{

	@Id
	private Integer id;
	
	@Override
	public Integer primaryKeyValue() {
		return id;
	}

	@Override
	public BaseTestDTO toDTO() {
		return BaseTestDTO.builder()
				.id(id)
				.build();
	}

}
