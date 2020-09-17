package com.pra.payrollmanager.user.accounting.order.detail;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;

@Repository
public class OrderItemDAL extends AbstractDAL<ObjectId, OrderItemDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.ACCOUNTING_ORDER_DETAIL;
	}

}
