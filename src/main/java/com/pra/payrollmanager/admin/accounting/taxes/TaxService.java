package com.pra.payrollmanager.admin.accounting.taxes;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class TaxService
		extends ServiceDAO<ObjectId, TaxDAO, TaxDAL> {


}
