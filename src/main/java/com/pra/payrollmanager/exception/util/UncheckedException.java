package com.pra.payrollmanager.exception.util;

import java.text.MessageFormat;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pra.payrollmanager.config.ErrorTemplateConfig;
import com.pra.payrollmanager.constants.EntityName;

@Component
public class UncheckedException {

    private static ErrorTemplateConfig propertiesConfig;

    @Autowired
    public UncheckedException(ErrorTemplateConfig propertiesConfig) {
        UncheckedException.propertiesConfig = propertiesConfig;
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    public static RuntimeException appException(String messageTemplate, String... args) {
        return new RuntimeException(format(messageTemplate, args));
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public static RuntimeException appException(EntityName entityType, ExceptionType exceptionType, String... args) {
        String messageTemplate = getMessageTemplate(entityType, exceptionType);
        return appException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public static RuntimeException appExceptionWithId(EntityName entityType, ExceptionType exceptionType, String id, String... args) {
        String messageTemplate = getMessageTemplate(entityType, exceptionType).concat(".").concat(id);
        return appException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType, messageTemplate and args
     *
     * @param entityType
     * @param exceptionType
     * @param messageTemplate
     * @param args
     * @return
     */
    public static RuntimeException appExceptionWithTemplate(EntityName entityType, ExceptionType exceptionType, String messageTemplate, String... args) {
        return appException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    private static RuntimeException appException(ExceptionType exceptionType, String messageTemplate, String... args) {
        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(format(messageTemplate, args));
        } else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
            return new EntityExistsException(format(messageTemplate, args));
        }
        return new RuntimeException(format(messageTemplate, args));
    }

    private static String getMessageTemplate(EntityName entityType, ExceptionType exceptionType) {
        return entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
    }

    private static String format(String template, String... args) {
        Optional<String> templateContent = Optional.ofNullable(propertiesConfig.getConfigValue(template));
        if (templateContent.isPresent()) {
            return MessageFormat.format(templateContent.get(), args);
        }
        return String.format(template, args);
    }

}
