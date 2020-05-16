package com.pra.payrollmanager.user.stock.book;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.DALWithCommon;
import com.pra.payrollmanager.constants.EntityName;

@Repository
public class StockBookDAL extends DALWithCommon<String, StockBookDAO> {

	@Override
	public EntityName entity() {
		return EntityName.PRODUCT;
	}


}
