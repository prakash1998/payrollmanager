package com.pra.payrollmanager.restaurant.hotel.tables;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class HotelTableService
		extends ServiceDAO<ObjectId, HotelTableDAO, HotelTableDAL> {

	
}
