package com.pra.payrollmanager.base;

public interface BaseDTO<DAO extends BaseDAO<?>> {

	public DAO toDAO();

}
