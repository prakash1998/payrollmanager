package com.pra.payrollmanager.base;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class BaseAuditDTO<DAO extends BaseAuditDAOWithDTO<?,?>> implements BaseDTO<DAO>{

	private String modifier;
	private Instant modifiedDate;

}
