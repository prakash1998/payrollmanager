package com.pra.payrollmanager.admin.user;

import static com.pra.payrollmanager.security.authorization.SecurityPermissions.USERS__MANAGER;
import static com.pra.payrollmanager.security.authorization.SecurityPermissions.USERS__VIEWER;

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

import com.pra.payrollmanager.base.NewBaseControl;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.exception.util.ValidationException;
import com.pra.payrollmanager.response.dto.Response;

@RestController
@RequestMapping("user")
public class UserControl extends NewBaseControl<UserService> {

	@GetMapping(value = "self", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<UserDTO> getSelf() throws DataNotFoundEx {
		String userName = authService.getUserName();
		return Response.payload(service.getById(userName));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<UserDTO>> getAllUsers() {
		authService.validatePermissions(USERS__MANAGER, USERS__VIEWER);
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<UserDTO> getUser(@PathVariable("id") @NotNull String userName) throws DataNotFoundEx {
		authService.validatePermissions(USERS__MANAGER, USERS__VIEWER);
		return Response.payload(service.getById(userName));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<UserDTO> createUser(@Valid @RequestBody UserDTO user)
			throws DuplicateDataEx, NoSuchMethodException, SecurityException, MethodArgumentNotValidException {
		if (user.getPassword() == null) {
			ValidationException.throwError(UserDTO.class, "getPassword",
					"Password must be provided");
		}
		authService.validatePermissions(USERS__MANAGER);
		return Response.payload(service.create(user));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<UserDTO> updateUser(@Valid @RequestBody UserDTO user) throws DataNotFoundEx, DuplicateDataEx {
		authService.validatePermissions(USERS__MANAGER);
		return Response.payload(service.updateUser(user));
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<UserDTO> deleteUser(@Valid @RequestBody UserDTO user) throws DataNotFoundEx {
		authService.validatePermissions(USERS__MANAGER);
		return Response.payload(service.delete(user));
	}
}
