package com.pra.payrollmanager.security.authorization.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.dto.response.Response;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.security.authorization.dto.SecurityPermissionDTO;
import com.pra.payrollmanager.security.authorization.permissions.SecurityPermissions;
import com.pra.payrollmanager.security.authorization.servcie.AuthorityService;
import com.pra.payrollmanager.security.authorization.servcie.SecurityPermissionService;

@RestController
@RequestMapping("auth/permissions")
public class SecurityPermissionControl {

	@Autowired
	AuthorityService authService;

	@Autowired
	SecurityPermissionService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<SecurityPermissionDTO>> getAllPermissions() {
		authService.validatePermissions(SecurityPermissions.SECURITY_PERMISSION_MANAGER);
		return Response.builder()
				.ok()
				.payload(service.getAll())
				.build();
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<SecurityPermissionDTO> getPermission(@PathVariable String id) throws DataNotFoundEx {
		authService.validatePermissions(SecurityPermissions.SECURITY_PERMISSION_MANAGER);
		return Response.builder()
				.ok()
				.payload(service.findById(id))
				.build();
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updatePermission(@Valid @RequestBody SecurityPermissionDTO permission) throws DataNotFoundEx {
		authService.validatePermissions(SecurityPermissions.SECURITY_PERMISSION_MANAGER);
		service.updatePermission(permission);
		return Response.builder()
				.ok()
				.build();
	}

}
