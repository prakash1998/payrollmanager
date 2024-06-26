package com.pra.payrollmanager.integration.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.pra.payrollmanager.admin.common.user.UserDAL;
import com.pra.payrollmanager.admin.common.user.UserDAO;
import com.pra.payrollmanager.base.BaseDALIntegrationTest;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;

public class UserDalTest extends BaseDALIntegrationTest<UserDAO, UserDAL> {

	UserDAO user = UserDAO.builder().userName("test").firstName("prakash").build();

	@Test
	public void testFindByFirstName() {
		try {
			UserDAO resultUser = dalService.getByFirstName("prakash");
			assertThat(resultUser.getUserName()).isEqualTo(user.getUserName());
			assertThat(resultUser.getFirstName()).isEqualTo(user.getFirstName());
		} catch (DataNotFoundEx e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initDB() {
		try {
			dalService.create(user);
		} catch (DuplicateDataEx e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cleanupDB() {

	}
}
