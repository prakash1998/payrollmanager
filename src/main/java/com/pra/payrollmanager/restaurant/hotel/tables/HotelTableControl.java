package com.pra.payrollmanager.restaurant.hotel.tables;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.restaurant.hotel.tables.allocation.HotelTableAllocDAO;
import com.pra.payrollmanager.restaurant.hotel.tables.allocation.HotelTableAllocService;
import com.pra.payrollmanager.validation.ValidationGroups;

@Validated
@RestController
@RequestMapping("hotel/table")
public class HotelTableControl extends BaseControl<HotelTableService> {
	
	@Autowired
	HotelTableAllocService tableAllocService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<HotelTableDTO>> getAllTables() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelTableDTO> getTable(@PathVariable("id") @NotNull ObjectId tableId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(tableId));
	}
	
	@PutMapping(value = "allocate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelTableAllocDAO> allocateTable(@PathVariable("id") @NotNull ObjectId tableId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(tableAllocService.allocateTable(tableId));
	}
	
	@PutMapping(value = "clear/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelTableAllocDAO> clearTable(@PathVariable("id") @NotNull ObjectId tableId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(tableAllocService.clearTable(tableId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelTableDTO> createTable(@Valid @RequestBody HotelTableDTO table)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(table));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelTableDTO> updateTable(@Valid @RequestBody HotelTableDTO table)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(table));
	}

	@Validated(ValidationGroups.onUpdate.class)
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<HotelTableDTO> deleteTable(@Valid @RequestBody HotelTableDTO table)
			throws AnyThrowable, DataNotFoundEx {
		return Response.payload(service.delete(table));
	}

}
