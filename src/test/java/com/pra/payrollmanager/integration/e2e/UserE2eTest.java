package com.pra.payrollmanager.integration.e2e;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.pra.payrollmanager.dto.UserDTO;
import com.pra.payrollmanager.integration.e2e.base.BaseE2eIntegrationTest;
import com.pra.payrollmanager.restcontroller.UserControl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserE2eTest extends BaseE2eIntegrationTest<UserControl> {

	UserDTO temp = UserDTO.builder().userName("test1").password("test2").firstName("prakash").build();

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Test
	@Order(1)
	@WithMockUser(authorities = { "admin" })
	public void testCreateUser() throws Exception {
		mockMvc
				.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(mapToJson(temp)))
				.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	@WithMockUser(authorities = { "admin" })
	public void testGetUser() throws Exception {
		mockMvc
				.perform(get("/user"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.payload[0].userName", is("test1")))
				.andExpect(jsonPath("$.payload[0].firstName", is("prakash")));
	}

	@Test
	@Order(3)
	@WithMockUser(authorities = { "admin" })
	public void testDeleteUser() throws Exception {
		mockMvc
				.perform(delete("/user").contentType(MediaType.APPLICATION_JSON).content(mapToJson(temp)))
				.andExpect(status().isOk());
		// assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Override
	public void cleanUp() throws Exception {
		// TODO Auto-generated method stub

	}

}
