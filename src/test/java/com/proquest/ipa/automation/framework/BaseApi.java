package com.proquest.ipa.automation.framework;



import com.proquest.ipa.automation.framework.tools.config.TestEnvironment;

public class BaseApi {


    private static BaseApi baseApi = null;
    private String currentSuiteName;


    public BaseApi() {

    }

    public static BaseApi getInstance() {

        if (baseApi == null) {
            baseApi = new BaseApi();
            baseApi.initializeActions();
            System.out.println(String.format("ADP ENV URL: %s", TestEnvironment.ipaUrl));
        }

        return baseApi;
    }

    private void initializeActions() {


    }


    public String getCurrentSuiteName() {
        return currentSuiteName;
    }

    public void setCurrentSuiteName(String currentSuiteName) {
        this.currentSuiteName = currentSuiteName;
    }
}
