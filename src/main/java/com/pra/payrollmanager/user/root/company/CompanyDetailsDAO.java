package com.pra.payrollmanager.user.root.company;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
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
@Accessors(chain=true)
@EqualsAndHashCode(callSuper = false)
@TypeAlias("s")
public class CompanyDetailsDAO extends BaseAuditDAO<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 403084656361695436L;

	@Id
	private String id;
	private String name;
	private String description;
	
	@Builder.Default
	private Set<Integer> imgIds = new HashSet<>();
	
	private List<String> categories;
	
	private String address;
	
	private Location location;

	@Override
	public String primaryKeyValue() {
		return id;
	}

//	@Override
//	public CompanyDetailsDTO toPlainDTO() {
//		return CompanyDetailsDTO.builder()
//				.id(id)
//				.name(name)
//				.category(category)
//				.description(desc)
//				.address(address)
//				.screenIds(screenIds)
//				.location(location)
//				.build();
//	}

}
