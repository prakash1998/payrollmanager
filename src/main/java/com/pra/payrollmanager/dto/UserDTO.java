package com.pra.payrollmanager.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.dto.base.BaseDTO;
import com.pra.payrollmanager.security.authentication.dao.SecurityUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@ToString
public class UserDTO extends BaseDTO<UserDAO> {

  @NotNull
  @Size(min = 5, message = "userName should have atleast 2 characters")
  private String userName;

  @NotNull private String password;
  private String firstName;
  private String lastName;

  @Override
  public UserDAO toDAO() {
    return UserDAO.builder()
        .userName(this.userName)
        .firstName(this.firstName)
        .lastName(this.lastName)
        .build();
  }

  public SecurityUser toSecurityUser() {
    return SecurityUser.builder()
    		.username(userName)
    		.password(password)
    		.build();
  }
}
