package com.pra.payrollmanager.unit.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pra.payrollmanager.admin.common.user.UserDAL;
import com.pra.payrollmanager.admin.common.user.UserDAO;
import com.pra.payrollmanager.admin.common.user.UserDTO;
import com.pra.payrollmanager.admin.common.user.UserService;
import com.pra.payrollmanager.base.BaseServiceUnitTest;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.message.MessageSendingService;
import com.pra.payrollmanager.security.authentication.user.SecurityUser;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;
import com.pra.payrollmanager.security.authorization.mappings.userrole.UserRoleMapDAL;

public class UserSeviceTest extends BaseServiceUnitTest<String, UserDAO, UserDTO, UserDAL, UserService> {

	private SecurityUserService mockSecurityService;
	@Override
	public UserService setEntityService() {
		mockSecurityService = Mockito.mock(SecurityUserService.class);
		return new UserService(mockSecurityService, Mockito.mock(UserRoleMapDAL.class));
	}

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
					Object[] arguments = invocation.getArguments();
					if (arguments != null && arguments.length > 0 && arguments[0] != null) {
						SecurityUser user = (SecurityUser) arguments[0];
						return user;
					}
					return null;
				})
				.when(mockSecurityService)
				.create(Mockito.any(SecurityUser.class));
		
		entityService.setMessageService(Mockito.mock(MessageSendingService.class));
	}

	@Test
	public void testPasswordWillNotReturnBack() throws DuplicateDataEx, DataNotFoundEx, AnyThrowable {
		UserDTO user = UserDTO.builder().userName("prakash").firstName("prakash").password("dudhat").build();
		entityService.create(user);
		UserDTO recieved = entityService.findByFirstName(user.getFirstName());
		assertNotNull(recieved);
		assertEquals(recieved.getUserName(), "prakash");
		assertThat(recieved.getPassword()).isNull();
	}

}
