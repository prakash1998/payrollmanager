package com.pra.payrollmanager.integration.admin.permission;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import com.pra.payrollmanager.base.BaseE2eIntegrationTest;
import com.pra.payrollmanager.config.MockUserDetailService;
import com.pra.payrollmanager.security.authorization.SecurityPermissions;
import com.pra.payrollmanager.security.authorization.permission.SecurityPermissionControl;

public class PermissionE2eTest extends BaseE2eIntegrationTest<SecurityPermissionControl> {

	
	@Override
	public void initUserStore(MockUserDetailService authService) {
		authService.addUserInStore(TESTER, SecurityPermissions.SECURITY_PERMISSION__MANAGER);
	}
	
	@Override
	public void init() throws Exception {
	
	}

	@Test
	@WithUserDetails(TESTER)
	public void testPermissionPersistance() throws Exception {
		mockMvc
				.perform(get("/auth/permissions").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Override
	public void cleanUp() throws Exception {

	}

}
