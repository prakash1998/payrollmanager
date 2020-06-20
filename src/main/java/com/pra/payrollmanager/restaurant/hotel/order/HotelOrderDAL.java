package com.pra.payrollmanager.restaurant.hotel.order;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.dal.AuditDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.security.authorization.FeaturePermissions;
import com.pra.payrollmanager.user.root.permissions.feature.FeaturePermission;

@Repository
public class HotelOrderDAL extends AuditDAL<ObjectId, HotelOrderDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.HOTEL_ORDERS;
	}

	@Override
	public FeaturePermission apiPermission() {
		return FeaturePermissions.HOTEL__ORDERS;
	}
	
	@Override
	public boolean auditLogEnabled() {
		return false;
	}
	
	public Integer todaysNextOrder() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "orderNumber"));
		query.limit(1);
		HotelOrderDAO lastOrder= this.findOneWith(query);
		
		if(lastOrder == null)
			return 1;
		return lastOrder.getOrderNumber() + 1; 
	}


}
