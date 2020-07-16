package com.pra.payrollmanager.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.rits.cloning.Cloner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeanUtils {

	private static Cloner cloner = new Cloner();

	public static <T extends Object> T copyInNullOrEmpty(T from, T to) {

		Class<?> clazz = to.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(to);

				// If it is a null or empty value copy to destination
				if (value == null ||
						(value instanceof Collection<?> && ((Collection<?>) value).isEmpty()) ||
						(value instanceof Map<?, ?> && ((Map<?, ?>) value).isEmpty())) {
					field.set(to, field.get(from));
				}
			} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
				log.error("got error when trying to copy where null or empty .", e);
			}
		}
		return to;
	}

	public static <T extends Object> T copyInNull(T from, T to) {
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

	public static <T extends Serializable> T copyOf(T input) {
		return cloner.deepClone(input);
		// ByteArrayOutputStream baos = null;
		// ObjectOutputStream oos = null;
		// ByteArrayInputStream bis = null;
		// ObjectInputStream ois = null;
		// try {
		// baos = new ByteArrayOutputStream();
		// oos = new ObjectOutputStream(baos);
		// oos.writeObject(input);
		// oos.flush();
		//
		// byte[] bytes = baos.toByteArray();
		// bis = new ByteArrayInputStream(bytes);
		// ois = new ObjectInputStream(bis);
		// Object result = ois.readObject();
		// return (T) result;
		// } catch (IOException e) {
		// throw new IllegalArgumentException("Object can't be copied", e);
		// } catch (ClassNotFoundException e) {
		// throw new IllegalArgumentException("Unable to reconstruct serialized object
		// due to invalid class definition", e);
		// } finally {
		// try {
		// Closeables.close(oos,true);
		// Closeables.close(baos,true);
		// Closeables.close(bis,true);
		// Closeables.close(ois,true);
		// } catch (IOException e) {
		// log.error("Error when tried to close stream",e);
		// }
		// }
	}
}
