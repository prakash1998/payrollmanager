package com.pra.payrollmanager.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pra.payrollmanager.annotations.EnableEmbeddedMongo;
import com.pra.payrollmanager.base.data.AuditInfo;
import com.pra.payrollmanager.config.MockUserDetailService;
import com.pra.payrollmanager.config.UnitTestingConfig;
import com.pra.payrollmanager.response.dto.Response;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = UnitTestingConfig.class)
@TestInstance(value = Lifecycle.PER_CLASS)
@EnableEmbeddedMongo
abstract public class BaseE2eIntegrationTest<CONTROL> {

	protected final static String TESTER = "test-user-pra";

	@Autowired
	protected MockUserDetailService authService;

	protected MockMvc mockMvc;

	@Autowired
	protected CONTROL entityRestControl;

	@Autowired
	protected MongoTemplate mongoTemplate;

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

	protected <T> T fromMvcResultResponse(MvcResult result, TypeReference<T> typeReference)
			throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		String responseJson = result.getResponse().getContentAsString();
		Response<T> responseObj = jsonMapper.readValue(responseJson, new TypeReference<Response<T>>() {
		});
		String payloadJson = jsonMapper.writeValueAsString(responseObj.getPayload());
		return jsonMapper.readValue(payloadJson, typeReference);
	}

	public abstract void init() throws Exception;

	public abstract void initUserStore(MockUserDetailService authService);

	@BeforeAll
	public void beforeClass() throws Exception {
		mockMvc = MockMvcBuilders
				.standaloneSetup(entityRestControl)
				.build();
		init();
		initUserStore(authService);
	}

	@AfterAll
	public void afterClass() throws Exception {
		cleanUp();
		authService.clearStore();
	}

	public abstract void cleanUp() throws Exception;

	public <T> void cleanUpTableForTESTER(Class<T> clazz, String tableName) {
		mongoTemplate.remove(Query.query(Criteria.where(AuditInfo.MODIFIER_FIELD).is(TESTER)), clazz, tableName);
	}
}
