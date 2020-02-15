package com.pra.payrollmanager.security.authorization.dao;

import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.pra.payrollmanager.security.authentication.dao.SecurityUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "COMPANY_DETIALS")
public class CompanyDetails {

	@Id
	@Size(min = 5, message = "Company ID must be {min} character long.")
	private String id;
	private String name;
	private String address;

	@DBRef
	private SecurityUser superUser;

}
