package com.pra.payrollmanager.base;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pra.payrollmanager.base.data.BaseDAO;

public interface BaseRepo<T extends BaseDAO<PK>,PK> extends MongoRepository<T, PK> {

}
