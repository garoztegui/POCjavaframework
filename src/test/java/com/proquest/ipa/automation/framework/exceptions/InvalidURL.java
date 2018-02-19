package com.proquest.ipa.automation.framework.exceptions;

public class InvalidURL extends RestException {

    public InvalidURL(int code, String message) {
        super(code, message);
    }

    public InvalidURL(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
