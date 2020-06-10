package com.pra.payrollmanager.user.root.company;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class CompanyDetailsDAO extends BaseAuditDAOWithDTO<String, CompanyDetailsDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	private String id;
	private String name;
	private String desc;
	
	private String category;
	
	private String address;
	
	private Location location;
	
	@Builder.Default
	private Set<String> screenIds = new HashSet<>();


	@Override
	public String primaryKeyValue() {
		return id;
	}

	@Override
	public CompanyDetailsDTO toPlainDTO() {
		return CompanyDetailsDTO.builder()
				.id(id)
				.name(name)
				.category(category)
				.description(desc)
				.address(address)
				.screenIds(screenIds)
				.location(location)
				.build();
	}

}
