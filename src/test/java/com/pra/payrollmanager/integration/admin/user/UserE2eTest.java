package com.pra.payrollmanager.integration.admin.user;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pra.payrollmanager.admin.common.user.UserControl;
import com.pra.payrollmanager.admin.common.user.UserDTO;
import com.pra.payrollmanager.base.BaseE2eIntegrationTest;
import com.pra.payrollmanager.config.MockUserDetailService;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserDAL;

public class UserE2eTest extends BaseE2eIntegrationTest<UserControl> {
	
	@Autowired
	SecurityUserDAL securityUserDAL;

	UserDTO temp = UserDTO.builder().userName("test1")
			.password("test2")
			.firstName("prakash")
			.email("test@test.com")
			.build();

	@Override
	public void initUserStore(MockUserDetailService authService) {
		
		SecurityUser user = SecurityUser.builder()
				.username(TESTER)
				.company(SecurityCompany.builder()
						.id(TESTER)
						.build())
				.build();
		
		authService.addUsersInStore(user);
	}

	@Override
	public void init() throws Exception {

	}

	@Test
	@WithUserDetails(TESTER)
	public void testUserApis() throws Exception {
		mockMvc
				.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(toJson(temp)))
				.andExpect(status().isOk());

		mockMvc
				.perform(get("/user"))
				.andExpect(status().isOk())
				.andExpect(t -> {
					List<UserDTO> users = fromMvcResultResponse(t, new TypeReference<List<UserDTO>>() {
					});
					assertTrue(users.stream()
							.anyMatch(u -> u.getUserName().equals("test1") && u.getFirstName().equals("prakash")));
				});

		mockMvc
				.perform(delete("/user").contentType(MediaType.APPLICATION_JSON).content(toJson(temp)))
				.andExpect(status().isOk());
	}

	@Override
	public void cleanUp() throws Exception {
	}

}
