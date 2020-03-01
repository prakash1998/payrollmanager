package com.pra.payrollmanager.admin.user;

import static com.pra.payrollmanager.security.authorization.SecurityPermissions.USERS_MANAGER;
import static com.pra.payrollmanager.security.authorization.SecurityPermissions.USERS_VIEWER;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.pra.payrollmanager.exception.util.ValidationException;
import com.pra.payrollmanager.response.dto.Response;

@RestController
@RequestMapping("user")
public class UserControl extends BaseControl<UserService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<UserDTO>> getAllUsers() {
		authService.validatePermissions(USERS_MANAGER, USERS_VIEWER);
		return Response.payload(service.getAllDtos());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<UserDTO> getUser(@PathVariable("id") @NotNull String userName) throws DataNotFoundEx {
		authService.validatePermissions(USERS_MANAGER, USERS_VIEWER);
		return Response.payload(service.getDtoById(userName));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> createUser(@Valid @RequestBody UserDTO user)
			throws DuplicateDataEx, NoSuchMethodException, SecurityException, MethodArgumentNotValidException {
		if (user.getPassword() == null) {
			ValidationException.throwError(UserDTO.class, "getPassword",
					"Password must be provided");
		}
		authService.validatePermissions(USERS_MANAGER);
		service.create(user);
		return Response.ok();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> updateUser(@Valid @RequestBody UserDTO user) throws DataNotFoundEx, DuplicateDataEx {
		authService.validatePermissions(USERS_MANAGER);
		service.updateUser(user);
		return Response.ok();
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<Void> deleteUser(@Valid @RequestBody UserDTO user) throws DataNotFoundEx {
		authService.validatePermissions(USERS_MANAGER);
		service.delete(user);
		return Response.ok();
	}
}
