package com.proquest.ipa.automation.framework.authentication;




/**
 * Needed info to call the authAPI
 */
public class AuthAPILoginInfo {

    private String ipAddress = "127.0.0.0.1";
    private String userAgent = "SOA_AUTOMATION";
    private int inactivityTimeoutMs = 1200000;



    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public int getInactivityTimeoutMs() {
        return inactivityTimeoutMs;
    }

    public void setInactivityTimeoutMs(int inactivityTimeoutMs) {
        this.inactivityTimeoutMs = inactivityTimeoutMs;
    }

}
