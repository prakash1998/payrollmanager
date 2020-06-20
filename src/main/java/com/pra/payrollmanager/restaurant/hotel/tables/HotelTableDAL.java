package com.pra.payrollmanager.restaurant.hotel.tables;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.entity.CompanyEntityNames;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.restaurant.hotel.tables.allocation.HotelTableAllocDAL;
import com.pra.payrollmanager.restaurant.hotel.tables.allocation.HotelTableAllocDAO;

@Repository
public class HotelTableDAL extends AbstractDAL<ObjectId, HotelTableDAO> {

	@Override
	public CompanyEntityNames entity() {
		return CompanyEntityNames.HOTEL_TABLE;
	}

	
	@Autowired
	HotelTableAllocDAL tableAlloationDAL;
	
	@Override
	@Transactional
	public HotelTableDAO create(HotelTableDAO obj) throws DuplicateDataEx {
		HotelTableDAO created = super.create(obj);
		tableAlloationDAL.create(HotelTableAllocDAO.builder().id(created.getId()).build());
		return created;
	}
	
	@Override
	@Transactional
	public HotelTableDAO delete(HotelTableDAO obj) throws DataNotFoundEx {
		HotelTableDAO deleted = super.delete(obj);
		tableAlloationDAL.deleteById(deleted.getId());
		return deleted;
	}

}
