package com.pra.payrollmanager.restaurant.hotel.tables;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.services.ServiceDAO;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.restaurant.hotel.tables.allocation.HotelTableAllocService;

@Service
public class HotelTableService
		extends ServiceDAO<ObjectId, HotelTableDAO, HotelTableDAL> {
	
	@Autowired
	HotelTableAllocService tableAlloationService;
	
	@Override
	@Transactional
	public HotelTableDAO create(HotelTableDAO obj) throws DuplicateDataEx {
		HotelTableDAO created = super.create(obj);
		tableAlloationService.create(created.toAllocation());
		return created;
	}
	
	@Override
	@Transactional
	public HotelTableDAO delete(HotelTableDAO obj) throws DataNotFoundEx {
		HotelTableDAO deleted = super.delete(obj);
		tableAlloationService.delete(deleted.toAllocation());
		return deleted;
	}
	
}
