package com.pra.payrollmanager.user.root.permissions.endpoint;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.response.dto.Response;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("endpoint-permissions")
public class EndpointPermissionControl extends BaseControl<EndpointPermissionService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<EndpointPermission>> getAllPermissions() {
		return Response.payload(service.getAll());
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<EndpointPermission> updatePermission(@Valid @RequestBody EndpointPermission permission)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(permission));
	}

}
