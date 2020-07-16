package com.pra.payrollmanager.translation;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public final class JacksonCustomMappers {

	public final static class InstantSerializer extends JsonSerializer<Instant> {
		@Override
		public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(DateTimeFormatter.ISO_DATE_TIME.format(value));
		}
		
		@Override
		public Class<Instant> handledType() {
			return Instant.class;
		}
	}

}
