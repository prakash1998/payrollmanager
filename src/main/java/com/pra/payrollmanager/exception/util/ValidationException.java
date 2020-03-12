package com.pra.payrollmanager.exception.util;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ValidationException {

	public static void throwError(Class<?> clazz, String methodName, String errorMessage)
			throws MethodArgumentNotValidException, NoSuchMethodException, SecurityException {
		
		String getRemoved = methodName.replace("get", "");
		String fieldName =  getRemoved.substring(0,1).toLowerCase() + getRemoved.substring(1);
		

		BindingResult bindingResult = new MapBindingResult(new HashMap<>(), clazz.getSimpleName());
		bindingResult.addError(new FieldError(clazz.getSimpleName(),fieldName, errorMessage));

		Method method = clazz.getMethod(methodName, (Class<?>[]) null);
		MethodParameter parameter = new MethodParameter(method, -1);

		throw new MethodArgumentNotValidException(parameter, bindingResult);
	}

}
