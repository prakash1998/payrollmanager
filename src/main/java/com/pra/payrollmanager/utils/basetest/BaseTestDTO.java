package com.pra.payrollmanager.utils.basetest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.base.data.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseTestDTO extends BaseAuditDTO<BaseTestDAO>{

	private Integer id;
	
	@Override
	public BaseTestDAO toPlainDAO() {
		return BaseTestDAO.builder().id(id).build();
	}

}
