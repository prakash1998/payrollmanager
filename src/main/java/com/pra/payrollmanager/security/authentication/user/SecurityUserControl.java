package com.pra.payrollmanager.security.authentication.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.security.authorization.SecurityPermissions;

@RestController
@RequestMapping("auth")
public class SecurityUserControl {

	@Autowired
	AuthorityService authService;

	@Autowired
	SecurityUserService service;

	@PostMapping(path = "password/update", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updatePassword(@Valid @RequestBody UserPasswordResetDTO user) throws CredentialNotMatchedEx {
		authService.validatePermissions(SecurityPermissions.USER__PASSWORD_UPDATE);
		service.updateUserPassword(user);
		return Response.builder()
				.ok()
				.message("Password Updated Successfully")
				.build();
	}

	@GetMapping(path = "logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> logOut() {
		service.logout(authService.getUserId());
		return Response.builder()
				.ok()
				.message("Logged Out Successfully")
				.build();
	}

}
