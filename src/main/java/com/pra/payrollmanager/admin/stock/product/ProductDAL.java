package com.pra.payrollmanager.admin.stock.product;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.base.dal.DALWithCompany;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class ProductDAL extends DALWithCompany<String, ProductDAO> {

	@Override
	public EntityName entity() {
		return EntityName.PRODUCT;
	}


}
