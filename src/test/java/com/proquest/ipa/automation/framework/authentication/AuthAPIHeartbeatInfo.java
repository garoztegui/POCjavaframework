package com.proquest.ipa.automation.framework.authentication;


import com.proquest.ipa.automation.framework.tools.config.TestEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Needed info to call the authAPI
 */
public class AuthAPIHeartbeatInfo {

    private String apiVersion = TestEnvironment.getEnvProperty("auth.apiversion"); //"0.1";
    private List<AuthActivity> lastActivity = new ArrayList<AuthActivity>();

    public static AuthAPIHeartbeatInfo with(AuthActivity lastActivity) {
        AuthAPIHeartbeatInfo result = new AuthAPIHeartbeatInfo();
        result.getLastActivity()
                .add(lastActivity);
        return result;
    }

    public List<AuthActivity> getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(List<AuthActivity> lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
}
