package com.pra.payrollmanager.utils.basetest;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCompany;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class BaseTestDAL extends DALWithCompany<Integer, BaseTestDAO> {

	@Override
	public EntityName entity() {
		return EntityName.BASE_TEST;
	}

}
