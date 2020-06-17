package com.pra.payrollmanager.restaurant.hotel.tables;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAO;
import com.pra.payrollmanager.restaurant.hotel.tables.allocation.HotelTableAllocDAO;
import com.pra.payrollmanager.validation.ValidationGroups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = false)
public class HotelTableDAO extends BaseAuditDAO<ObjectId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;


	@Id
	@NotNull(groups = {ValidationGroups.onUpdate.class})
	private ObjectId id;
	
	private String display;
	
	private Double capacity;
	
	public HotelTableAllocDAO toAllocation() {
		return HotelTableAllocDAO.builder().id(id).build();
	}

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}
}
