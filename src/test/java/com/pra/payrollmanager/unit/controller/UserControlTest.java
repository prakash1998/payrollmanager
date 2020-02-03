package com.pra.payrollmanager.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import com.pra.payrollmanager.dto.UserDTO;
import com.pra.payrollmanager.restcontroller.UserControl;
import com.pra.payrollmanager.service.UserService;
import com.pra.payrollmanager.unit.controller.base.BaseControlUnitTest;

public class UserControlTest extends BaseControlUnitTest<UserControl, UserService, UserDTO> {

	UserDTO user = UserDTO.builder()
			.userName("appu")
			.firstName("prakash")
			.password("test")
			.build();

	@Override
	public List<UserDTO> initMockDtoStore() {
		return Collections.singletonList(user);
	}

	@Override
	public void initMockService(UserService mockService) throws Exception {

	}

	@WithMockUser(authorities = { "admin" })
	@Test
	public void testSaveApi() throws Exception {
		MvcResult result = mockMvc
				.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
						.content(jsonMapper.writeValueAsString(user)))
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@WithMockUser(authorities = { "user" })
	@Test
	public void testGetApi() throws Exception {

		MvcResult result = mockMvc
				.perform(get("/user"))
				.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);

	}

}
