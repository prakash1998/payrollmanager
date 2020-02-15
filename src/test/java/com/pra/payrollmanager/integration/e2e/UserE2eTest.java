package com.pra.payrollmanager.integration.e2e;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pra.payrollmanager.dal.UserDAL;
import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.dto.UserDTO;
import com.pra.payrollmanager.integration.e2e.base.BaseE2eIntegrationTest;
import com.pra.payrollmanager.restcontroller.UserControl;
import com.pra.payrollmanager.security.authentication.repo.SecurityUserRepo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserE2eTest extends BaseE2eIntegrationTest<UserControl> {
	
	@Autowired
	SecurityUserRepo securityUserRepo;

	UserDTO temp = UserDTO.builder().userName("test1").password("test2").firstName("prakash").build();

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Test
	@Order(1)
	@WithMockUser(authorities = { "users-manager" })
	public void testCreateUser() throws Exception {
		mockMvc
				.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(toJson(temp)))
				.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	@WithMockUser(authorities = { "users-manager" })
	public void testGetUser() throws Exception {
		mockMvc
				.perform(get("/user"))
				.andExpect(status().isOk())
				.andExpect(t -> {
					List<UserDTO> users = fromMvcResultResponse(t, new TypeReference<List<UserDTO>>() {
					});
					assertTrue(users.stream()
							.anyMatch(u -> u.getUserName().equals("test1") && u.getFirstName().equals("prakash")));
				});
	}

	@Test
	@Order(3)
	@WithMockUser(authorities = { "users-manager" })
	public void testDeleteUser() throws Exception {
		mockMvc
				.perform(delete("/user").contentType(MediaType.APPLICATION_JSON).content(toJson(temp)))
				.andExpect(status().isOk());
	}

	@Override
	public void cleanUp() throws Exception {
		securityUserRepo.deleteById("test1");
		super.cleanUpTableForTestUser(UserDAO.class, UserDAL.USERS_TABLE);
	}

}
