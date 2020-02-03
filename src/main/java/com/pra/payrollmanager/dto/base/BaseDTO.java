package com.pra.payrollmanager.dto.base;

import com.pra.payrollmanager.dao.base.BaseDAO;

abstract public class BaseDTO<DAO extends BaseDAO<?>> {

	abstract public DAO toDAO();

}
