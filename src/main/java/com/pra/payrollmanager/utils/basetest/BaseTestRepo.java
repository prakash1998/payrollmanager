package com.pra.payrollmanager.utils.basetest;

import org.springframework.stereotype.Repository;

import com.pra.payrollmanager.repository.base.BaseRepo;

@Repository
public interface BaseTestRepo extends BaseRepo<BaseTestDAO, Integer>{

}
