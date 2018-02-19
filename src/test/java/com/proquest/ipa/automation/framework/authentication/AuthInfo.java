package com.proquest.ipa.automation.framework.authentication;


/**
 * Class to maintain the auth info returned by the auth API
 */
public class AuthInfo {

    private boolean authenticated;
    private AuthActivity lastActivity;


    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public AuthActivity getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(AuthActivity lastActivity) {
        this.lastActivity = lastActivity;
    }


}
