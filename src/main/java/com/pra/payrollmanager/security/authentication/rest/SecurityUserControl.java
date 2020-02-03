package com.pra.payrollmanager.security.authentication.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.dto.response.Response;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.security.authentication.dto.SecurityUserDTO;
import com.pra.payrollmanager.security.authentication.service.SecurityUserService;
import com.pra.payrollmanager.security.authorization.permissions.SecurityPermissions;
import com.pra.payrollmanager.security.authorization.servcie.AuthorityService;

@RestController
@RequestMapping("auth")
public class SecurityUserControl{
	
	@Autowired
    AuthorityService authService;

	@Autowired
	SecurityUserService service;

	@PostMapping(path = "password/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updatePassword(@Valid @RequestBody SecurityUserDTO user) throws CredentialNotMatchedEx {
		authService.validatePermissions(SecurityPermissions.PASSWORD_UPDATE);
		service.updateUserPassword(user);
		return Response.builder()
				.ok()
				.message("Password Updated Successfully")
				.build();
	}
	
	@GetMapping(path = "logout",produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updatePassword()  {
		service.logOutUser(SecurityContextHolder.getContext().getAuthentication().getName());
		return Response.builder()
				.ok()
				.message("Logged Out Successfully")
				.build();
	}
	
	

}
