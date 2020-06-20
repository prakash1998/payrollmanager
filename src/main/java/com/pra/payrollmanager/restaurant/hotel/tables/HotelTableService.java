package com.pra.payrollmanager.restaurant.hotel.tables;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.base.services.ServiceDTO;
import com.pra.payrollmanager.restaurant.hotel.tables.allocation.HotelTableAllocDAL;
import com.pra.payrollmanager.restaurant.hotel.tables.allocation.HotelTableAllocDAO;

@Service
public class HotelTableService
		extends ServiceDTO<ObjectId, HotelTableDAO, HotelTableDTO, HotelTableDAL> {

	@Autowired
	HotelTableAllocDAL tableAllocDAL;

	@Override
	public List<HotelTableDTO> postProcessMultiGet(List<HotelTableDAO> objList) {

		Map<ObjectId, Boolean> tableAllocMap = tableAllocDAL.findAll().stream()
				.collect(Collectors.toMap(HotelTableAllocDAO::getId, HotelTableAllocDAO::getAllocated));

		return super.postProcessMultiGet(objList).stream()
				.map(item -> item.setAllocated(tableAllocMap.getOrDefault(item.getId(), false)))
				.collect(Collectors.toList());
	}

	@Override
	public HotelTableDTO postProcessGet(HotelTableDAO obj) {
		HotelTableDTO dto = super.postProcessGet(obj);
		dto.setAllocated(tableAllocDAL.findById(obj.getId()).getAllocated());
		return dto;
	}

}
