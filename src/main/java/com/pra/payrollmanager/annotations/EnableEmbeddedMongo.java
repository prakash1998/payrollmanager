package com.pra.payrollmanager.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Import;

@Retention(RUNTIME)
@Target(TYPE)
@Import(EmbeddedMongoAutoConfiguration.class)
public @interface EnableEmbeddedMongo {
}
