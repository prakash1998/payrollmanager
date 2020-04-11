package com.pra.payrollmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:errtemplate.properties")
public class ErrorTemplateConfig {
    @Autowired
    private Environment env;

    public String getConfigValue(String configKey) {
        return env.getProperty(configKey);
    }
}
