package com.pra.payrollmanager.repository.base;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pra.payrollmanager.dao.base.BaseDAO;

public interface BaseRepo<T extends BaseDAO<PK>,PK> extends MongoRepository<T, PK> {

}
