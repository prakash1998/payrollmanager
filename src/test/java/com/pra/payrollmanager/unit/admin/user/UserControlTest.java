package com.pra.payrollmanager.unit.admin.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import com.pra.payrollmanager.admin.user.UserControl;
import com.pra.payrollmanager.admin.user.UserDTO;
import com.pra.payrollmanager.admin.user.UserService;
import com.pra.payrollmanager.base.BaseControlUnitTest;
import com.pra.payrollmanager.config.MockUserDetailService;
import com.pra.payrollmanager.security.authorization.SecurityPermissions;

public class UserControlTest extends BaseControlUnitTest<UserControl, UserService, UserDTO> {

	UserDTO user = UserDTO.builder()
			.userName("appu123")
			.firstName("prakash")
			.email("testemail@test.com")
			.password("test")
			.build();

	@Override
	public void initUserStore(MockUserDetailService authService) {
		authService.addUserInStore(TESTER, SecurityPermissions.USERS_MANAGER);
	}

	@Override
	public List<UserDTO> initMockDtoStore() {
		return Collections.singletonList(user);
	}

	@Override
	public void initMockService(UserService mockService) throws Exception {

	}

	@Test
	@WithUserDetails(TESTER)
	public void testSaveApi() throws Exception {
		mockMvc
				.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(toJson(user)))
				.andExpect(status().isOk());
	}

	@Test
	@WithUserDetails(TESTER)
	public void testGetApi() throws Exception {
		mockMvc
				.perform(get("/user"))
				.andExpect(status().isOk());
	}

}
