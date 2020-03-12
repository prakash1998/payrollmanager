package com.pra.payrollmanager.base;

public interface WithDTO<DTO extends BaseDTO<?>> {
	
	public DTO toDTO();
	
}