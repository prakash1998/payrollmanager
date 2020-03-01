package com.pra.payrollmanager.base;

public interface BaseDAOWithDTO<PK,DTO extends BaseDTO<?>> extends BaseDAO<PK>,WithDTO<DTO>{

}
