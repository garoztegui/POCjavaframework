package com.proquest.ipa.automation.framework.actions;


import com.proquest.ipa.automation.framework.tools.config.TestEnvironment;
import com.proquest.ipa.automation.framework.tools.config.TestRun;
import com.proquest.ipa.automation.framework.tools.utils.HttpUtils;
import java.util.Random;

public abstract class BaseAction {

    private static final String ipa_ep = String.format("si-api/%s", TestRun.apiIpaVersion);
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random rnd = new Random();


    protected static String ipaAPI(String pathTemplate, Object... args) {
        //return endpoint(TestEnvironment.ipaUrl, ADP_EP) + String.format(pathTemplate, args);
        return TestEnvironment.ipaUrl + pathTemplate;
    }


    private static String endpoint(String mainUrl, String base) {
        return mainUrl + base;
    }


    protected static HttpUtils httpUtils() {
        return HttpUtils.getInstance();
    }


}
