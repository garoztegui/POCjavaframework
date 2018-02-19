package com.proquest.ipa.automation.framework.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Maps to AuthAPI json response objects.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthAPIResponse {

    private String apiVersion;

    private AuthAPIstatus status;

    private AuthToken token;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public AuthAPIstatus getStatus() {
        return status;
    }

    public void setStatus(AuthAPIstatus status) {
        this.status = status;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class AuthAPIstatus {
        private boolean success;
        private String type;
        private String data;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class AuthToken {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
