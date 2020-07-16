package com.pra.payrollmanager.e2e.restaurant;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pra.payrollmanager.base.BaseE2eIntegrationTest;
import com.pra.payrollmanager.config.MockUserDetailService;
import com.pra.payrollmanager.restaurant.hotel.tables.HotelTableControl;
import com.pra.payrollmanager.restaurant.hotel.tables.HotelTableDTO;
import com.pra.payrollmanager.security.authentication.company.SecurityCompany;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;

public class HotelTableE2eTest extends BaseE2eIntegrationTest<HotelTableControl> {
	
	HotelTableDTO temp = HotelTableDTO.builder()
			.display("test1")
			.capacity(4d)
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
	public void testHotelTableApis() throws Exception {
		
		MvcResult result = mockMvc
				.perform(post("/hotel/table").contentType(MediaType.APPLICATION_JSON).content(toJson(temp)))
				.andExpect(status().isOk())
				.andReturn();
		
		HotelTableDTO createdTable = fromMvcResultResponse(result, new TypeReference<HotelTableDTO>() {
		});
		assertTrue(createdTable.getAllocated().equals(Boolean.FALSE));

		mockMvc
		.perform(put("/hotel/table/allocate/"+createdTable.getId().toString()))
		.andExpect(status().isOk())
		.andExpect(t -> {
			HotelTableDTO r = fromMvcResultResponse(t, new TypeReference<HotelTableDTO>() {
			});
			assertTrue(r.getAllocated().equals(Boolean.TRUE));
		});
		
		mockMvc
		.perform(put("/hotel/table/clear/"+createdTable.getId().toString()))
		.andExpect(status().isOk())
		.andExpect(t -> {
			HotelTableDTO r = fromMvcResultResponse(t, new TypeReference<HotelTableDTO>() {
			});
			assertTrue(r.getAllocated().equals(Boolean.FALSE));
		});

	}

	@Override
	public void cleanUp() throws Exception {
	}

}
