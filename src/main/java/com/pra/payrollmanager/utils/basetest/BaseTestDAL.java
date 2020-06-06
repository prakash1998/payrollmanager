package com.pra.payrollmanager.utils.basetest;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCompany;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class BaseTestDAL extends DALWithCompany<Integer, BaseTestDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.BASE_TEST;
	}

}
