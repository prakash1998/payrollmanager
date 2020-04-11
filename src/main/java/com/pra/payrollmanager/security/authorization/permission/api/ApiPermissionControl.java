package com.pra.payrollmanager.security.authorization.permission.api;

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
@RequestMapping("auth/api-permissions")
public class ApiPermissionControl extends NewBaseControl<ApiPermissionService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<ApiPermissionDTO>> getAllPermissions() {
		authService.validatePermissions(SecurityPermissions.API_PERMISSION__MANAGER);
		return Response.builder()
				.ok()
				.payload(service.getAll())
				.build();
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<ApiPermissionDTO> getPermission(@PathVariable String id) throws DataNotFoundEx {
		authService.validatePermissions(SecurityPermissions.API_PERMISSION__MANAGER);
		return Response.builder()
				.ok()
				.payload(service.getById(id))
				.build();
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updatePermission(@Valid @RequestBody ApiPermissionDTO permission) throws DataNotFoundEx {
		authService.validatePermissions(SecurityPermissions.API_PERMISSION__MANAGER);
		service.update(permission);
		return Response.builder()
				.ok()
				.build();
	}

}
