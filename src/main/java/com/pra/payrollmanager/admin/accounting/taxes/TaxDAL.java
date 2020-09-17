package com.pra.payrollmanager.admin.accounting.taxes;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class TaxDAL extends AbstractDAL<ObjectId, TaxDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.TAX;
	}

}
