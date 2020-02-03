package com.pra.payrollmanager.repository;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.dao.UserDAO;
import com.pra.payrollmanager.repository.base.BaseRepo;

@Repository
public interface UserRepo extends BaseRepo<UserDAO, String> {
	
	UserDAO findByFirstName(String name);

}