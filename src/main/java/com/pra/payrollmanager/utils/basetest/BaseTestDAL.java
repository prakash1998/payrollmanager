package com.pra.payrollmanager.utils.basetest;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.dal.base.BaseDAL;
import com.pra.payrollmanager.exception.util.EntityType;

@Repository
public class BaseTestDAL extends BaseDAL<Integer, BaseTestDAO, BaseTestRepo>{

	@Override
	protected EntityType entityType() {
		return EntityType.BASE_TEST;
	}

}
