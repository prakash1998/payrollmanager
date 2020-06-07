package com.pra.payrollmanager.admin.common.roles;

import java.util.List;
import java.util.Set;

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
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;

@RestController
@RequestMapping("roles")
public class RoleControl extends BaseControl<RoleService> {

	@GetMapping(value = "just-info", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<RoleDTO>> getRole() {
		return Response.payload(service.getAll());
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<RoleDTO>> getRolesWithPermission() {
		return Response.payload(service.getAll());
	}

	@PostMapping(value = "/multiple", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<RoleDTO>> getRolesWithPermission(@RequestBody Set<String> roleIds) {
		return Response.payload(service.getByIds(roleIds));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<RoleDTO> getRole(@PathVariable("id") @NotNull String roleId) throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(roleId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<RoleDTO> createRole(@Valid @RequestBody RoleDTO role) throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(role));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<RoleDTO> updateRole(@Valid @RequestBody RoleDTO role)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(role));
	}

}
