package com.pra.payrollmanager.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pra.payrollmanager.dal.UserDAL;
import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.dto.UserDTO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.security.authentication.dao.SecurityUser;
import com.pra.payrollmanager.security.authentication.service.SecurityUserService;
import com.pra.payrollmanager.service.UserService;
import com.pra.payrollmanager.unit.service.base.BaseServiceUnitTest;

public class UserSeviceTest extends BaseServiceUnitTest<UserDAO, UserDTO, UserDAL, UserService> {

	@MockBean
	SecurityUserService mockSecurityService;
	// @MockBean UserDAL mockUserDal;

	@Override
	public List<UserDAO> initMockDaoStore() {
		return new ArrayList<>();
	}

	@Override
	public void initMockDal(UserDAL mockDalService) throws DataNotFoundEx {

		Mockito.doAnswer(
				invocation -> {
					Object[] arguments = invocation.getArguments();
					if (arguments != null && arguments.length > 0 && arguments[0] != null) {
						String userName = (String) arguments[0];
						return mockDataStore
								.stream()
								.filter(i -> i.getFirstName().equals(userName))
								.findFirst()
								.orElse(null);
					}
					return null;
				})
				.when(mockDalService)
				.getByFirstName(Mockito.anyString());

		Mockito.doAnswer(
				invocation -> {
					return null;
				})
				.when(mockSecurityService)
				.createUser(Mockito.any(SecurityUser.class));
	}

	@Test
	public void testPasswordWillNotReturnBack() throws DuplicateDataEx, DataNotFoundEx {
		UserDTO user = UserDTO.builder().userName("prakash").firstName("prakash").password("dudhat").build();
		entityService.create(user);
		UserDTO recieved = entityService.findByFirstName(user.getFirstName());
		if (recieved != null)
			assertThat(recieved.getPassword()).isNull();
	}
}
