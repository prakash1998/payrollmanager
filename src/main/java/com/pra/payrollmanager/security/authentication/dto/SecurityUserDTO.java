package com.pra.payrollmanager.security.authentication.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SecurityUserDTO {
	@NotNull
	private String userName;
	private String oldPassword;
	private String newPassword;
}
