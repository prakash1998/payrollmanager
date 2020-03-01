package com.pra.payrollmanager.admin.roles;

import static com.pra.payrollmanager.security.authorization.SecurityPermissions.ROLES_MANAGER;
import static com.pra.payrollmanager.security.authorization.SecurityPermissions.ROLES_VIEWER;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;

@RestController
@RequestMapping("roles")
public class RoleControl extends BaseControl<RoleService>{
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<RoleDTO>> getRole() {
		authService.validatePermissions(ROLES_MANAGER,ROLES_VIEWER);
		return Response.payload(service.getAllDtos());
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<RoleDTO> getRole(@PathVariable("id") @NotNull String roleId) throws DataNotFoundEx {
		authService.validatePermissions(ROLES_MANAGER,ROLES_VIEWER);
		return Response.payload(service.getDtoById(roleId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> createRole(@Valid @RequestBody RoleDTO role) throws DuplicateDataEx {
		authService.validatePermissions(ROLES_MANAGER);
		service.create(role);
		return Response.ok();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updateRole(@Valid @RequestBody RoleDTO role) throws DataNotFoundEx, DuplicateDataEx {
		authService.validatePermissions(ROLES_MANAGER);
		service.updateRole(role);
		return Response.ok();
	}

}
