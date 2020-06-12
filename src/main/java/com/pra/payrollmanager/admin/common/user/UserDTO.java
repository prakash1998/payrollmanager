package com.pra.payrollmanager.admin.common.user;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.pra.payrollmanager.base.data.BaseAuditDTO;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends BaseAuditDTO<UserDAO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4497545622349208285L;
	
	@NotNull(message = "userName must not be null")
	@Size(min = 5, max = 50, message = "userName should have atleast {min} characters.")
	@Pattern(regexp = "^[^-]*$", message = "User Name should not contain '-'.")
	private String userName;
	private String password;

	@NotNull(message = "firstName must not be null")
	@NotBlank(message = "firstName must not be blank")
	private String firstName;
	private String lastName;

	@Builder.Default
	private Set<String> roleIds = new HashSet<>();

	@Email
	@NotNull(message = "email must not be null")
	private String email;

	@Pattern(regexp = "(^$|[0-9]{10})", message = "please enter valid phone Number")
	private String phone;

//	@Override
//	public UserDAO toPlainDAO() {
//		return UserDAO.builder()
//				.userName(userName)
//				.firstName(firstName)
//				.lastName(lastName)
//				.email(email)
//				.phone(phone)
//				.build();
//	}

	public SecurityUser toSecurityUser() {
		return SecurityUser.builder()
				.username(userName)
				.password(password)
				.build();
	}
}
