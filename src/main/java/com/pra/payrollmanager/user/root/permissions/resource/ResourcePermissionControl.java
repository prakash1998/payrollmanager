package com.pra.payrollmanager.user.root.permissions.resource;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.response.dto.Response;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("secure-resources")
public class ResourcePermissionControl extends BaseControl<ResourcePermissionService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<ResourceDAO>> getAllResources() {
		return Response
				.payload(service.getAll());
	}

}
