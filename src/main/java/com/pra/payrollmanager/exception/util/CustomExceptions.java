package com.pra.payrollmanager.exception.util;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pra.payrollmanager.config.ErrorTemplateConfig;
import com.pra.payrollmanager.entity.EntityName;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.unchecked.AppException;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.exception.unchecked.UnAuthorizedEx;

@Component
public class CustomExceptions {

	private static ErrorTemplateConfig propertiesConfig;

	@Autowired
	public CustomExceptions(ErrorTemplateConfig propertiesConfig) {
		CustomExceptions.propertiesConfig = propertiesConfig;
	}

	/**
	 * Returns new CommonAppEx based on template and args
	 *
	 * @param messageTemplate
	 * @param args
	 * @return
	 */
	public static AppException appException(String messageTemplate, String... args) {
		return new AppException(format(messageTemplate, args));
	}

	/**
	 * Returns new CommonAppEx based on EntityType , Exception Id and args
	 *
	 * @param entityType
	 * @param id
	 *            of exeption
	 * @param args
	 * @return
	 */
	public static AppException appExceptionWithId(EntityName entityType, String id, String... args) {
		String messageTemplate = getMessageTemplate(entityType, id);
		return appException(messageTemplate, args);
	}

	/**
	 * Returns new CommonAppEx based on EntityType, Exception Id and args
	 *
	 * @param entityType
	 * @param messageTemplate
	 * @param args
	 * @return
	 */
	public static AppException appExceptionWithTemplate(
			EntityName entityType, String id, String... args) {
		String messageTemplate2 = getMessageTemplate(entityType, id);
		return appException(messageTemplate2, args);
	}

	/**
	 * Returns new DataNotFoundEx based on EntityType and args
	 *
	 * @param entityType
	 * @param args
	 * @return
	 */
	public static DataNotFoundEx notFoundEx(EntityName entityType, String... args) {
		String messageTemplate = getMessageTemplate(entityType, ExceptionType.ENTITY_NOT_FOUND.getValue());
		return new DataNotFoundEx(format(messageTemplate, args));
	}
	
	/**
	 * Returns new DataNotFoundEx based on EntityType and args
	 *
	 * @param entityType
	 * @param args
	 * @return
	 */
	public static UnAuthorizedEx notAuthorizedEx(EntityName entityType, String... args) {
		String messageTemplate = getMessageTemplate(entityType, ExceptionType.ACCESS_DENIED.getValue());
		return new UnAuthorizedEx(format(messageTemplate, args));
	}

	/**
	 * Returns new DuplicateDataEx based on EntityType and args
	 *
	 * @param entityType
	 * @param args
	 * @return
	 */
	public static DuplicateDataEx duplicateEx(EntityName entityType, String... args) {
		String messageTemplate = getMessageTemplate(entityType, ExceptionType.DUPLICATE_ENTITY.getValue());
		return new DuplicateDataEx(format(messageTemplate, args));
	}

	public static CredentialNotMatchedEx wrongCredentialEx(EntityName entityType, String... args) {
		String messageTemplate = getMessageTemplate(entityType, ExceptionType.WRONG_CREDENTIAL.getValue());
		return new CredentialNotMatchedEx(format(messageTemplate, args));
	}

	private static String getMessageTemplate(EntityName entityType, String messageTemplateId) {
		return entityType.name().concat(".").concat(messageTemplateId).toLowerCase();
	}

	private static String format(String template, String... args) {
		Optional<String> templateContent = Optional.ofNullable(propertiesConfig.getConfigValue(template));
		if (templateContent.isPresent()) {
			return MessageFormat.format(templateContent.get(), args);
		}
		return String.format(template, args);
	}
}
