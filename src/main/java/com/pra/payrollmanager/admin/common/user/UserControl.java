package com.pra.payrollmanager.admin.common.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;
import com.pra.payrollmanager.validation.FieldValidator;

@RestController
@RequestMapping("user")
public class UserControl extends BaseControl<UserService> {

	@GetMapping(value = "self")
	public Response<UserDTO> getSelf() throws DataNotFoundEx, AnyThrowable {
		String userName = authService.getUserName();
		return Response.payload(service.getById(userName));
	}

	@GetMapping
	public Response<List<UserDTO>> getAllUsers() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}")
	public Response<UserDTO> getUser(@PathVariable("id") @NotNull String userName) throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(userName));
	}

	@PostMapping
	public Response<UserDTO> createUser(@Valid @RequestBody UserDTO user)
			throws DuplicateDataEx, NoSuchMethodException, MethodArgumentNotValidException,
			AnyThrowable {
		FieldValidator.validateNotNull(user, "getPassword",
				"Password must be provided");
		return Response.payload(service.create(user));
	}

	@PutMapping
	public Response<UserDTO> updateUser(@Valid @RequestBody UserDTO user)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.update(user));
	}

	@DeleteMapping
	public Response<UserDTO> deleteUser(@Valid @RequestBody UserDTO user) throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.delete(user));
	}
}
