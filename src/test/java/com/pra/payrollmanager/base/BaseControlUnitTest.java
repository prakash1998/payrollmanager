package com.pra.payrollmanager.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pra.payrollmanager.base.data.BaseDTO;
import com.pra.payrollmanager.base.services.ServiceDTO;
import com.pra.payrollmanager.config.MockUserDetailService;
import com.pra.payrollmanager.config.TestingConfig;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = TestingConfig.class)
@TestInstance(value = Lifecycle.PER_CLASS)
abstract public class BaseControlUnitTest<CONTROL extends BaseControl<SERVICE>,
		SERVICE extends ServiceDTO<?, ?, ?, ?>,
		DTO extends BaseDTO<?>> {

	protected final static String TESTER = "test-user-pra";

	@Autowired
	protected MockUserDetailService authService;

	protected MockMvc mockMvc;

	@Autowired
	protected CONTROL entityRestControl;

	@Autowired
	protected ObjectMapper jsonMapper;

	protected String toJson(Object obj) throws JsonProcessingException {
		return jsonMapper.writeValueAsString(obj);
	}

	protected <T> T fromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		return jsonMapper.readValue(json, clazz);
	}

	protected <T> T fromJson(String json, TypeReference<T> typeReference)
			throws JsonParseException, JsonMappingException, IOException {
		return jsonMapper.readValue(json, typeReference);
	}

	protected <T> T fromMvcResult(MvcResult result, TypeReference<T> typeReference)
			throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		String json = result.getResponse().getContentAsString();
		return jsonMapper.readValue(json, typeReference);
	}

	protected SERVICE mockEntityService;

	private Class<CONTROL> controlClazz;
	private Class<SERVICE> serviceClazz;
	private Class<DTO> dtoClazz;

	@SuppressWarnings("unchecked")
	public BaseControlUnitTest() {
		 // specified in each class in hierarchy because we can access type parameter class in immediate parent only 
    Type sooper = getClass().getGenericSuperclass();
		controlClazz = (Class<CONTROL>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[0];
		serviceClazz = (Class<SERVICE>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[1];
		dtoClazz = (Class<DTO>) ((ParameterizedType) sooper)
				.getActualTypeArguments()[2];

		mockEntityService = Mockito.mock(serviceClazz);
	}

	private List<DTO> mockDataStore;

	public abstract List<DTO> initMockDtoStore();

	public void setBaseMockServiceMethods() throws DuplicateDataEx, DataNotFoundEx, AnyThrowable {
		Mockito.doAnswer(invocation -> {
			return mockDataStore;
		}).when(mockEntityService)
				.getAll();

		Mockito.doAnswer(invocation -> {
			Object[] arguments = invocation.getArguments();
			if (arguments != null && arguments.length > 0
					&& arguments[0] != null) {
				Object key = arguments[0];

				Optional<DTO> item = mockDataStore.stream()
						.filter(i -> i.toDAO().primaryKeyValue().equals(key))
						.findFirst();

				if (item.isPresent()) {
					return item.get();
				} else {
					throw new DataNotFoundEx();
				}
			}
			return null;
		}).when(mockEntityService)
				.getById(Mockito.any());
	}

	public abstract void initMockService(SERVICE mockService) throws Exception;

	public abstract void initUserStore(MockUserDetailService authService);

	@BeforeAll
	public void beforeClass() throws Throwable {
		mockDataStore = initMockDtoStore();
		mockDataStore = mockDataStore == null ? new ArrayList<>() : mockDataStore;
		mockMvc = MockMvcBuilders
				.standaloneSetup(entityRestControl)
				.build();
		setBaseMockServiceMethods();
		initMockService(mockEntityService);
		entityRestControl.setService(mockEntityService);
		initUserStore(authService);
	}

	@AfterAll
	public void afterClass() throws Exception {
		authService.clearStore();
	}
}
