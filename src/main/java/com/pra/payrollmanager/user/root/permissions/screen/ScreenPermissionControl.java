package com.pra.payrollmanager.user.root.permissions.screen;

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
@RequestMapping("screen-permissions")
public class ScreenPermissionControl extends BaseControl<ScreenPermissionService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<ScreenPermission>> getAllPermissions() {
		return Response.payload(service.getAll());
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<ScreenPermission> updatePermission(@Valid @RequestBody ScreenPermission permission)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(permission));
	}

}
