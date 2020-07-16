package com.pra.payrollmanager.admin.stock.product;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.AuditServiceDTO;

@Service
public class ProductService
		extends AuditServiceDTO<ObjectId, ProductDAO, ProductDTO, ProductDAL> {

	public List<ProductDTO> getOnlyAllowed() {
		return dataAccessLayer.findWithAccessControl().stream()
				.map(item -> toDTO(item))
				.collect(Collectors.toList());
	}

}
