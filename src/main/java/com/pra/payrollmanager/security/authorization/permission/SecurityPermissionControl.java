package com.pra.payrollmanager.security.authorization.permission;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.NewBaseControl;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.security.authorization.SecurityPermissions;

@RestController
@RequestMapping("auth/permissions")
public class SecurityPermissionControl extends NewBaseControl<SecurityPermissionService>{

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<SecurityPermissionDTO>> getAllPermissions() {
		authService.validatePermissions(SecurityPermissions.SECURITY_PERMISSION__MANAGER);
		return Response.builder()
				.ok()
				.payload(service.getAll())
				.build();
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<SecurityPermissionDTO> getPermission(@PathVariable String id) throws DataNotFoundEx {
		authService.validatePermissions(SecurityPermissions.SECURITY_PERMISSION__MANAGER);
		return Response.builder()
				.ok()
				.payload(service.getById(id))
				.build();
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updatePermission(@Valid @RequestBody SecurityPermissionDTO permission) throws DataNotFoundEx {
		authService.validatePermissions(SecurityPermissions.SECURITY_PERMISSION__MANAGER);
		service.update(permission);
		return Response.builder()
				.ok()
				.build();
	}

}
