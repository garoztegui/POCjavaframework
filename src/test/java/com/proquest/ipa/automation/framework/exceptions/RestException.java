package com.proquest.ipa.automation.framework.exceptions;


public class RestException extends Exception {

    private final int code;

    public RestException(int code, String message) {
        super(message);
        this.code = code;
    }

    public RestException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
