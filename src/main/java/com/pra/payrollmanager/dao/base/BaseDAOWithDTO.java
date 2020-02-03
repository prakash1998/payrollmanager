package com.pra.payrollmanager.dao.base;

import com.pra.payrollmanager.dto.base.BaseDTO;

public interface BaseDAOWithDTO<PK,DTO extends BaseDTO<?>> extends BaseDAO<PK>,WithDTO<DTO>{

}
