package com.pra.payrollmanager.base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pra.payrollmanager.base.dal.AbstractDAL;
import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;

/**
 * This parent class will do following things for child class
 *
 * <p>
 *
 * <ol>
 * <li>apply @SpringBootTest over the class to make it JUnit Test
 * <li>provide DAL object named dalService
 * <li>create DAO object/list supplied by you in db before test
 * <li>delete object/list from db after tests
 * </ol>
 *
 * @author prakash dudhat
 * @param <DAO>
 *            : dao class for entity
 * @param <DAL>
 *            : dal class for entity
 */
@SpringBootTest
@TestInstance(value = Lifecycle.PER_CLASS)
public abstract class BaseDALIntegrationTest<
		DAO extends BaseDAO<?>,
		DAL extends BaseDAL<?, DAO>> {

	@Autowired
	protected DAL dalService;

	// before test
	public abstract void initDB();

	// after test
	public abstract void cleanupDB();

	@BeforeAll
	public void beforeClass() {
		initDB();
	}

	@AfterAll
	public void afterClass() {
		cleanupDB();
	}
}
