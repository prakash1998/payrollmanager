package com.pra.payrollmanager.base;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseRepo<T extends BaseDAO<PK>,PK> extends MongoRepository<T, PK> {

}
