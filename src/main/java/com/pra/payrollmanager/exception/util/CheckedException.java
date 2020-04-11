package com.pra.payrollmanager.exception.util;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pra.payrollmanager.config.ErrorTemplateConfig;
import com.pra.payrollmanager.constants.EntityName;
import com.pra.payrollmanager.exception.checked.CommonAppEx;
import com.pra.payrollmanager.exception.checked.CredentialNotMatchedEx;
import com.pra.payrollmanager.exception.checked.DataNotFoundEx;
import com.pra.payrollmanager.exception.checked.DuplicateDataEx;

@Component
public class CheckedException {

  private static ErrorTemplateConfig propertiesConfig;

  @Autowired
  public CheckedException(ErrorTemplateConfig propertiesConfig) {
    CheckedException.propertiesConfig = propertiesConfig;
  }

  /**
   * Returns new CommonAppEx based on template and args
   *
   * @param messageTemplate
   * @param args
   * @return
   */
  public static CommonAppEx appException(String messageTemplate, String... args) {
    return new CommonAppEx(format(messageTemplate, args));
  }

  /**
   * Returns new CommonAppEx based on EntityType , Exception Id and args
   *
   * @param entityType
   * @param id of exeption
   * @param args
   * @return
   */
  public static CommonAppEx appExceptionWithId(EntityName entityType, String id, String... args) {
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
  public static CommonAppEx appExceptionWithTemplate(
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
    String messageTemplate = getMessageTemplate(entityType, "not.found");
    return new DataNotFoundEx(format(messageTemplate, args));
  }

  /**
   * Returns new DuplicateDataEx based on EntityType and args
   *
   * @param entityType
   * @param args
   * @return
   */
  public static DuplicateDataEx duplicateEx(EntityName entityType, String... args) {
    String messageTemplate = getMessageTemplate(entityType, "duplicate");
    return new DuplicateDataEx(format(messageTemplate, args));
  }
  
  public static CredentialNotMatchedEx wrongCredentialEx(EntityName entityType, String... args) {
	    String messageTemplate = getMessageTemplate(entityType, "wrong.credential");
	    return new CredentialNotMatchedEx(format(messageTemplate, args));
  }

  private static String getMessageTemplate(EntityName entityType, String messageTemplateId) {
    return entityType.name().concat(".").concat(messageTemplateId).toLowerCase();
  }

  private static String format(String template, String... args) {
    Optional<String> templateContent =
        Optional.ofNullable(propertiesConfig.getConfigValue(template));
    if (templateContent.isPresent()) {
      return MessageFormat.format(templateContent.get(), args);
    }
    return String.format(template, args);
  }
}
