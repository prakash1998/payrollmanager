package com.pra.payrollmanager.restaurant.hotel.tables;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import com.pra.payrollmanager.base.data.BaseDTO;
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
public class HotelTableDTO implements BaseDTO<HotelTableDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;


	@NotNull(groups = {ValidationGroups.onUpdate.class})
	private ObjectId id;
	
	private String display;
	
	private Double capacity;
	
	@Builder.Default
	private Boolean allocated = false;
	
}
