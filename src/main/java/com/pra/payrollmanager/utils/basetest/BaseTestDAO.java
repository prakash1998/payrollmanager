package com.pra.payrollmanager.utils.basetest;

import javax.persistence.Id;

import org.springframework.data.annotation.TypeAlias;

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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TypeAlias("z")
public class BaseTestDAO extends BaseAuditDAO<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5961748235598614135L;
	@Id
	private Integer id;
	
	@Override
	public Integer primaryKeyValue() {
		return id;
	}

//	@Override
//	public BaseTestDTO toPlainDTO() {
//		return BaseTestDTO.builder()
//				.id(id)
//				.build();
//	}

}
