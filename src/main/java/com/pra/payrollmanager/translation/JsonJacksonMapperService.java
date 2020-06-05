package com.pra.payrollmanager.translation;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JsonJacksonMapperService {

	public static final String EMPTY_JSON = "{}";

	private ObjectMapper mapper;

	public JsonJacksonMapperService() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializerByType(ObjectId.class, new ToStringSerializer());
		
		mapper = builder.build();
		//		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.registerModule(new JavaTimeModule());
	}

	public ObjectMapper mapper() {
		return this.mapper;
	}

	public <T> String objectToJson(T obj) {
		try {
			return this.mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error(String.format("Error While converting Object to json for Class %s", obj.getClass().toString()),
					e);
			return EMPTY_JSON;
		}
	}

	public <T> T jsonToObject(String json, Class<T> clazz) {
		try {
			return this.mapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			log.error(String.format("Error While converting json to Object for Class %s", clazz.toString()),
					e);
			return null;
		}
	}

	public <T> T jsonToObject(String json, TypeReference<T> typeReference) {
		try {
			return this.mapper.readValue(json, typeReference);
		} catch (JsonProcessingException e) {
			log.error(String.format("Error While converting json to Object for Class %s", typeReference.toString()),
					e);
			return null;
		}
	}

}
