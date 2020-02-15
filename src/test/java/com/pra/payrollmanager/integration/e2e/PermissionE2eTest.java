package com.pra.payrollmanager.integration.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.pra.payrollmanager.integration.e2e.base.BaseE2eIntegrationTest;
import com.pra.payrollmanager.security.authorization.rest.SecurityPermissionControl;

public class PermissionE2eTest extends BaseE2eIntegrationTest<SecurityPermissionControl> {

	@Override
	public void init() throws Exception {
	
	}

	@Test
	@WithMockUser(authorities = { "security-permission-manager" })
	public void testPermissionPersistance() throws Exception {
		mockMvc
				.perform(get("/auth/permissions").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Override
	public void cleanUp() throws Exception {

	}

}
