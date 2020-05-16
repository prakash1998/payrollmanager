package com.pra.payrollmanager.admin.common.user;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAOWithDTO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
@EqualsAndHashCode(callSuper = false )
public class UserDAO extends BaseAuditDAOWithDTO<String, UserDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6606637995040780533L;
	
	@Id
	private String userName;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;

	@Override
	public String primaryKeyValue() {
		return this.userName;
	}

	@Override
	public UserDTO toPlainDTO() {
		return UserDTO.builder()
				.userName(userName)
				.password(null)
				.firstName(firstName)
				.lastName(lastName)
				.phone(phone)
				.email(email)
				.build();
	}
}
