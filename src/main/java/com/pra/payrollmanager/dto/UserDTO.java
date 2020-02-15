package com.pra.payrollmanager.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.dto.base.BaseDTO;
import com.pra.payrollmanager.security.authentication.dao.SecurityUser;

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
public class UserDTO extends BaseDTO<UserDAO> {

	@NotNull
	@Size(min = 5, message = "userName should have atleast {min} characters but found ${validatedValue}.")
	private String userName;
	@NotNull
	private String password;

	@NotNull
	private String firstName;
	private String lastName;

	@Email
	@NotNull
	private String email;

	@Pattern(regexp = "(^$|[0-9]{10})", message = "please enter valid phone Number")
	private String phone;

	@Override
	public UserDAO toDAO() {
		return UserDAO.builder()
				.userName(userName)
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.phone(phone)
				.build();
	}

	public SecurityUser toSecurityUser() {
		return SecurityUser.builder()
				.username(userName)
				.password(password)
				.build();
	}
}
