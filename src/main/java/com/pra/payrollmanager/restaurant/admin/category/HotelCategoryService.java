package com.pra.payrollmanager.restaurant.admin.category;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDAO;

@Service
public class HotelCategoryService
		extends ServiceDAO<ObjectId, HotelCategory, HotelCategoryDAL> {

}
