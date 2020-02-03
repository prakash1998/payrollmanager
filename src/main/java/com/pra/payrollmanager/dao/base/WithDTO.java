package com.pra.payrollmanager.dao.base;

import com.pra.payrollmanager.dto.base.BaseDTO;

public interface WithDTO<DTO extends BaseDTO<?>> {
	
	public DTO toDTO();
	
}