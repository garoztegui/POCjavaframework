package com.proquest.ipa.automation.framework.tools.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public final class TestEnvironment {


    public static String ipaUrl;
    //Email variables
    public static String emailUser;
    public static String emailPassword;
    public static String emailHost;

    public static String testEnvironment = StringUtils.isEmpty(System.getenv("ENVIRONMENT"))
            ? "LOCAL"
            : System.getenv("ENVIRONMENT");
    private static ResourceBundle envProperties;


    static {
        envProperties = ResourceBundle.getBundle("environments");
        setEnvUrls();
    }

    private TestEnvironment() {
    }

    private static String setEnvUrls() {
        switch (testEnvironment.toUpperCase()) {
            case "REVIEW":
                ipaUrl = envProperties.getString("review.ipaapi.app");
                break;
            default:
                System.out.println("No environment given");
                break;
        }
        return ipaUrl;
    }

    public static String getEnvProperty(String propertyName) {
        switch (testEnvironment.toUpperCase()) {
            case "REVIEW":
                return envProperties.getString("review." + propertyName);
            default:
                System.out.println("No environment given");
                break;
        }
        return null;
    }

    public static Map<String, String> listPropertiesKeys() {

        final String prefix = testEnvironment.toLowerCase() + ".";
        final Map<String, String> result = new HashMap();
        Enumeration<String> keys = envProperties.getKeys();
        while (keys.hasMoreElements()) {
            String currentKey = keys.nextElement();
            if (StringUtils.startsWith(currentKey, prefix)) {
                result.put(StringUtils.difference(prefix, currentKey),
                        envProperties.getString(currentKey));
            }
        }
        return result;
    }

    public static boolean isMustAuthenticate() {
        return StringUtils.isBlank(getEnvProperty("auth.dummyToken"));
    }
}
