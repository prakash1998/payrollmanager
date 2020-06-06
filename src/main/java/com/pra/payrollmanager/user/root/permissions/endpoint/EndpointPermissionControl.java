package com.pra.payrollmanager.user.root.permissions.endpoint;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.filter.AuthorizationFilter;
import com.pra.payrollmanager.response.dto.Response;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("endpoint-permissions")
public class EndpointPermissionControl extends BaseControl<EndpointPermissionService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<EndpointPermissionDTO>> getAllPermissions() {
		return Response.payload(service.getAll());
	}

	@PostMapping(path = "/multiple", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<EndpointPermissionDTO>> gePermissions(@RequestBody Set<Integer> numericIds) {
		return Response.payload(AuthorizationFilter.universalEndpointsMap.values().stream()
				.filter(p -> numericIds.contains(p.getNumericId()))
				.map(p -> EndpointPermissionDTO.builder()
						.numericId(p.getNumericId())
						.id(p.getId())
						.build())
				.collect(Collectors.toList()));
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<EndpointPermissionDTO> getPermission(@PathVariable String id) throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(id));
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<EndpointPermissionDTO> updatePermission(@Valid @RequestBody EndpointPermissionDTO permission)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(permission));
	}

}
