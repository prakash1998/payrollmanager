package com.pra.payrollmanager.admin.company;


import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false )
public class CompanyDetailsDAO extends BaseAuditDAOWithDTO<String,CompanyDetailsDTO>{

	@Id
	private String id;
	private String name;
	private String address;
	
	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public CompanyDetailsDTO toDto() {
		return CompanyDetailsDTO.builder()
				.companyId(id)
				.companyName(name)
				.address(address)
				.build();
	}

}
