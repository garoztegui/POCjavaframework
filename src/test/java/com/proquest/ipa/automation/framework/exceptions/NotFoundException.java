package com.proquest.ipa.automation.framework.exceptions;

public class NotFoundException extends RestException {

    public NotFoundException(int code, String message) {
        super(code, message);
    }

    public NotFoundException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
