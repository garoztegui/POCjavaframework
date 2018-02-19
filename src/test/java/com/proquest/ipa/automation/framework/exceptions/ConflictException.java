package com.proquest.ipa.automation.framework.exceptions;


public class ConflictException extends RestException {


    public ConflictException(int code, String message) {
        super(code, message);
    }

    public ConflictException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
