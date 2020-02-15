package com.pra.payrollmanager.restcontroller;

import static com.pra.payrollmanager.security.authorization.permissions.SecurityPermissions.USERS_MANAGER;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.dto.UserDTO;
import com.pra.payrollmanager.dto.response.Response;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.restcontroller.base.BaseControl;
import com.pra.payrollmanager.service.UserService;

@RestController
@RequestMapping("user")
public class UserControl extends BaseControl<UserService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<UserDTO>> getUsers() {
		authService.validatePermissions(USERS_MANAGER);
		return Response.payload(service.getAll());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> createUser(@Valid @RequestBody UserDTO user) throws DuplicateDataEx {
		authService.validatePermissions(USERS_MANAGER);
		service.create(user);
		return Response.ok();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updateUser(@Valid @RequestBody UserDTO user) throws DataNotFoundEx {
		authService.validatePermissions(USERS_MANAGER);
		service.update(user);
		return Response.ok();
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> deleteUser(@Valid @RequestBody UserDTO user) throws DataNotFoundEx {
		authService.validatePermissions(USERS_MANAGER);
		service.delete(user);
		return Response.ok();
	}
}
