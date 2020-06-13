package com.pra.payrollmanager.restaurant.hotel.tables;

import org.bson.types.ObjectId;

import com.pra.payrollmanager.base.data.BaseAuditDAO;

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

	private ObjectId id;

	private String display;
	
	private Double capacity;
	
	@Builder.Default
	private Boolean allocated = false;

	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}
}
