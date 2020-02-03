package com.pra.payrollmanager.security.authentication.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.security.authentication.dao.SecurityUser;

@Repository
public interface SecurityUserRepo extends MongoRepository<SecurityUser, String> {

}