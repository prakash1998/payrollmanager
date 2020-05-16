package com.pra.payrollmanager.utils.basetest;

import javax.persistence.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseTestDAO extends BaseAuditDAOWithDTO<Integer,BaseTestDTO>{

	@Id
	private Integer id;
	
	@Override
	public Integer primaryKeyValue() {
		return id;
	}

	@Override
	public BaseTestDTO toPlainDTO() {
		return BaseTestDTO.builder()
				.id(id)
				.build();
	}

}
