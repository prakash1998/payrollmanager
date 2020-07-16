package com.pra.payrollmanager.user.root.permissions.endpoint;

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
@TypeAlias("t")
public class EndpointPermission implements BaseDAO<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3045137574424856418L;
	@Id
	private String id;
	private String display;
	private String category;
	private String description;

	@Override
	public String primaryKeyValue() {
		return id;
	}

}
