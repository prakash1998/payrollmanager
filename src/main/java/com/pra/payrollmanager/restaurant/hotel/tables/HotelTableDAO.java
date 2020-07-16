package com.pra.payrollmanager.restaurant.hotel.tables;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.pra.payrollmanager.base.data.BaseDAO;

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
@TypeAlias("j")
public class HotelTableDAO implements BaseDAO<ObjectId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;


	@Id
	private ObjectId id;
	
	private String display;
	
	private Double capacity;
	
	@Override
	public ObjectId primaryKeyValue() {
		return id;
	}
}
