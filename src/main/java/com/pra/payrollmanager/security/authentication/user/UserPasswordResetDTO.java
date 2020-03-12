package com.pra.payrollmanager.security.authentication.user;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserPasswordResetDTO {
	@NotNull
	private String userName;
	private String oldPassword;
	private String newPassword;
}
