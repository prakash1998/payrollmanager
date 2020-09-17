package com.pra.payrollmanager.admin.product;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.AuditServiceDAO;

@Service
public class ProductService
		extends AuditServiceDAO<ObjectId, ProductDAO, ProductDAL> {

	public List<ProductDAO> getOnlyAllowed() {
		return dataAccessLayer.findWithAccessControl();
	}

}
