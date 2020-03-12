package com.pra.payrollmanager.security.authentication.user;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.base.BaseRepo;

@Repository
public interface SecurityUserRepo extends BaseRepo<SecurityUser, String> {

}