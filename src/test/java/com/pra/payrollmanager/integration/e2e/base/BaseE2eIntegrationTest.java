package com.pra.payrollmanager.integration.e2e.base;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pra.payrollmanager.restcontroller.base.BaseControl;

@SpringBootTest
@TestInstance(value = Lifecycle.PER_CLASS)
abstract public class BaseE2eIntegrationTest<CONTROL extends BaseControl<?>>{

	protected MockMvc mockMvc;

	@Autowired
	protected CONTROL entityRestControl;

	@Autowired
	protected ObjectMapper jsonMapper;

	protected String mapToJson(Object obj) throws JsonProcessingException {
		return jsonMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		return jsonMapper.readValue(json, clazz);
	}

	public abstract void init() throws Exception;

	@BeforeAll
	public void beforeClass() throws Exception {
		mockMvc = MockMvcBuilders
				.standaloneSetup(entityRestControl)
				.build();
		init();
	}
	
	@AfterAll
	public abstract void cleanUp() throws Exception;
}
