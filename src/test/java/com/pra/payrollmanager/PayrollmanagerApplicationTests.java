package com.pra.payrollmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pra.payrollmanager.dal.UserDAL;
import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.dto.UserDTO;
import com.pra.payrollmanager.service.UserService;

@SpringBootTest
public class PayrollmanagerApplicationTests {

  @Autowired private UserService userService;

  @MockBean private UserDAL userDal;

  @Test
  public void testBaseMethod() {
    List<UserDAO> list = Collections.singletonList(UserDAO.builder().userName("test").build());
    Mockito.when(userDal.getAll()).thenReturn(list);
    List<UserDTO> users = userService.getAll();
    assertThat(users.stream().anyMatch(i -> i.getUserName().equals("test"))).isTrue();
  }
}
