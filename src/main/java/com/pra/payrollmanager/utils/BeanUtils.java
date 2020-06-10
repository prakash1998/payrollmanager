package com.pra.payrollmanager.utils;

import java.lang.reflect.Field;
import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanUtils {

	public static <T extends Object> T copyFromWhereNullOrEmptyTo(T from, T to) {

		Class<?> clazz = to.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(to);

				// If it is a null or empty value copy to destination
				if (value == null ||
						(value instanceof Collection<?> && ((Collection<?>) value).isEmpty())) {
					field.set(to, field.get(from));
				}
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				log.error("got error when trying to copy where null or empty .", e);
			}
		}
		return to;
	}

	public static <T extends Object> T copyFromWhereNullTo(T from, T to) {
		Class<?> clazz = to.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(to);
				// If it is a null value copy to destination
				if (value == null) {
					field.set(to, field.get(from));
				}
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				log.error("got error when trying to copy where nulls.", e);
			}
		}
		return to;
	}
}
