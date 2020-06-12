package com.pra.payrollmanager.admin.common.user;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.base.data.BaseAuditDAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false )
public class UserDAO extends BaseAuditDAO<String> {

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

//	@Override
//	public UserDTO toPlainDTO() {
//		return UserDTO.builder()
//				.userName(userName)
//				.password(null)
//				.firstName(firstName)
//				.lastName(lastName)
//				.phone(phone)
//				.email(email)
//				.build();
//	}
}
