package com.proquest.ipa.automation.framework;


import com.proquest.ipa.automation.framework.tools.config.TestEnvironment;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Map;
import java.util.Properties;

/**
 * This class is used to load the system properties depending on the current environment
 * so they can be injected.
 */
public class ContextPropertyConfigurer extends PropertyPlaceholderConfigurer {

    public ContextPropertyConfigurer() {
        super();

        Map<String, String> environmentProperties = TestEnvironment.listPropertiesKeys();
        final Properties properties = new Properties();
        for (String key : environmentProperties.keySet()) {
            properties.put(key, environmentProperties.get(key));
        }

        setProperties(properties);

    }

}