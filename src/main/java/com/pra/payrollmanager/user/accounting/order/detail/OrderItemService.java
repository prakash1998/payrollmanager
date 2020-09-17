package com.pra.payrollmanager.user.accounting.order.detail;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class OrderItemService
		extends ServiceDAO<ObjectId, OrderItemDAO, OrderItemDAL> {

}
