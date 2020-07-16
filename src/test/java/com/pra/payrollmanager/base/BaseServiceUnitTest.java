package com.pra.payrollmanager.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pra.payrollmanager.base.dal.BaseDAL;
import com.pra.payrollmanager.base.data.BaseDAO;
import com.pra.payrollmanager.base.services.BaseServiceDTO;
import com.pra.payrollmanager.base.services.ServiceBeans;
import com.pra.payrollmanager.config.AppConfig;
import com.pra.payrollmanager.config.AuthoritiyServiceForUnitTest;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.security.authentication.user.SecurityUserService;

@TestInstance(value = Lifecycle.PER_CLASS)
public abstract class BaseServiceUnitTest<PK,
		DAO extends BaseDAO<PK>,
		DTO,
		DAL extends BaseDAL<PK, DAO>,
		SERVICE extends ServiceBeans<DAL> & BaseServiceDTO<PK, DAO, DTO, DAL>> {

	protected SERVICE entityService;
	
	abstract public SERVICE setEntityService();
	
	protected DAL mockDalService;

	private Class<DAO> daoClazz;
	private Class<DTO> dtoClazz;
	private Class<DAL> dalClazz;

	public BaseServiceUnitTest() {
		// specified in each class in hierarchy because we can access type parameter
		// class in immediate parent only
		Type sooper = getClass().getGenericSuperclass();
		daoClazz = (Class<DAO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[1];
		dtoClazz = (Class<DTO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[2];
		dalClazz = (Class<DAL>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[3];
		mockDalService = Mockito.mock(dalClazz);
		entityService = setEntityService();
	}

	// protected DTO entityDto;
	//
	// protected List<DTO> entityDtoList;
	//
	// public abstract List<DTO> entityDtos();

	protected List<DAO> mockDataStore;

	public abstract List<DAO> initMockDaoStore();

	public void setBaseMockDalMethods() throws DuplicateDataEx, DataNotFoundEx {
		
		Mockito.doAnswer(invocation -> {
			return daoClazz;
		}).when(mockDalService)
		.daoClazz();
		
		Mockito.doAnswer(invocation -> {
			return new AuthoritiyServiceForUnitTest();
		}).when(mockDalService)
		.authorityService();
		
		Mockito.doAnswer(invocation -> {
			Object[] arguments = invocation.getArguments();
			if (arguments != null && arguments.length > 0
					&& arguments[0] != null) {
				DAO entity = (DAO) arguments[0];

				Optional<DAO> item = mockDataStore.stream()
						.filter(i -> i.primaryKeyValue().equals(entity.primaryKeyValue()))
						.findFirst();

				if (item.isPresent()) {
					throw new DuplicateDataEx();
				} else {
					mockDataStore.add(entity);
					return entity;
				}
			}
			return null;
		}).when(mockDalService)
				.create(Mockito.any(daoClazz));

		Mockito.doAnswer(invocation -> {
			Object[] arguments = invocation.getArguments();
			if (arguments != null && arguments.length > 0
					&& arguments[0] != null) {
				DAO entity = (DAO) arguments[0];

				Optional<DAO> item = mockDataStore.stream()
						.filter(i -> i.primaryKeyValue().equals(entity.primaryKeyValue()))
						.findFirst();

				if (item.isPresent()) {
					mockDataStore = mockDataStore.stream()
							.map(i -> i.primaryKeyValue().equals(entity.primaryKeyValue())
									? entity
									: i)
							.collect(Collectors.toList());
					return item.get();
				} else {
					throw new DataNotFoundEx();
				}
			}
			return null;
		}).when(mockDalService)
				.update(Mockito.any(daoClazz));

		Mockito.doAnswer(invocation -> {
			Object[] arguments = invocation.getArguments();
			if (arguments != null && arguments.length > 0
					&& arguments[0] != null) {
				Object key = arguments[0];

				Optional<DAO> item = mockDataStore.stream()
						.filter(i -> i.primaryKeyValue().equals(key))
						.findFirst();

				if (item.isPresent()) {
					mockDataStore = mockDataStore.stream()
							.filter(i -> !i.primaryKeyValue().equals(key))
							.collect(Collectors.toList());
					return item.get();
				} else {
					throw new DataNotFoundEx();
				}
			}
			return null;
		}).when(mockDalService)
				.deleteById(Mockito.any());

		Mockito.doAnswer(invocation -> {
			return mockDataStore;
		}).when(mockDalService)
				.findAll();

		Mockito.doAnswer(invocation -> {
			Object[] arguments = invocation.getArguments();
			if (arguments != null && arguments.length > 0
					&& arguments[0] != null) {
				Object key = arguments[0];

				Optional<DAO> item = mockDataStore.stream()
						.filter(i -> i.primaryKeyValue().equals(key))
						.findFirst();

				if (item.isPresent()) {
					return item.get();
				} else {
					throw new DataNotFoundEx();
				}
			}
			return null;
		}).when(mockDalService)
				.findById(Mockito.any());
	}

	public abstract void initMockDal(DAL mockDalService) throws Exception;

	@BeforeAll
	public void beforeClass() throws Exception {
		mockDataStore = initMockDaoStore();
		mockDataStore = mockDataStore == null ? new ArrayList<>() : mockDataStore;
		setBaseMockDalMethods();
		initMockDal(mockDalService);
		entityService.setDataAccessLayer(mockDalService);
		entityService.setModelMapper(new AppConfig().modelMapper());
	}
}
