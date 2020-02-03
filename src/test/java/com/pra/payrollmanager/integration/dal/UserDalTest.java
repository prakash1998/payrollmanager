package com.pra.payrollmanager.integration.dal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.pra.payrollmanager.dal.UserDAL;
import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;
import com.pra.payrollmanager.integration.dal.base.BaseDALIntegrationTest;

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
    try {
		dalService.delete(user);
	} catch (DataNotFoundEx e) {
		e.printStackTrace();
	}
  }
}
