package com.pra.payrollmanager.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldValidator {

	public static <T> void validateNotNull(T obj, String methodName, String errorMessage)
			throws MethodArgumentNotValidException, NoSuchMethodException {

		Class<?> clazz = obj.getClass();
		Method method = clazz.getMethod(methodName, (Class<?>[]) null);

		Object result;
		try {
			result = method.invoke(obj, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.debug("Error when invoking method ", e);
			result = null;
		}

		if (result == null) {
			String getRemoved = methodName.replace("get", "");
			String fieldName = getRemoved.substring(0, 1).toLowerCase() + getRemoved.substring(1);

			BindingResult bindingResult = new MapBindingResult(new HashMap<>(), clazz.getSimpleName());
			bindingResult.addError(new FieldError(clazz.getSimpleName(), fieldName, errorMessage));

			MethodParameter parameter = new MethodParameter(method, -1);
			throw new MethodArgumentNotValidException(parameter, bindingResult);
		}
	}

}
