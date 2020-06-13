package com.pra.payrollmanager.restaurant.hotel.menu;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class HotelMenuService
		extends ServiceDAO<ObjectId, HotelMenu, HotelMenuDAL> {

}
