package com.proquest.ipa.automation.framework.tools.config;

import java.util.ResourceBundle;

public final class TestRun {


    public static String apiIpaVersion;
    private static ResourceBundle testRunProperties;

    static {
        testRunProperties = ResourceBundle.getBundle("testrun");
        setTestRunProperties();
    }

    private TestRun() {
    }

    private static void setTestRunProperties() {
      //  apiIpaVersion = testRunProperties.getString("ipa.api.version");

    }
}
