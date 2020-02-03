package com.pra.payrollmanager.dao;

import org.springframework.data.annotation.Id;

import com.pra.payrollmanager.dao.base.BaseAuditDAO;
import com.pra.payrollmanager.dao.base.BaseDAOWithDTO;
import com.pra.payrollmanager.dao.base.WithDTO;
import com.pra.payrollmanager.dto.UserDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDAO extends BaseAuditDAO<String> implements BaseDAOWithDTO<String,UserDTO> {

  @Id private String userName;
  private String firstName;
  private String lastName;

  @Override
  public String primaryKeyValue() {
    return this.userName;
  }

  @Override
  public UserDTO toDTO() {
    return UserDTO.builder()
        .userName(this.userName)
        .password(null)
        .firstName(this.firstName)
        .lastName(this.lastName)
        .build();
  }
}
