package com.proquest.ipa.automation.framework.authentication;

/**
 * Activity information for the authenticated user.
 */
public class AuthActivity {

    private String id;
    private Long ts = null;

    public static AuthActivity with(String token) {
        return new AuthActivity().setId(token);
    }

    public String getId() {
        return id;
    }

    public AuthActivity setId(String id) {
        this.id = id;
        return this;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
