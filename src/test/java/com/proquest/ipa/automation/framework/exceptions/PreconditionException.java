package com.proquest.ipa.automation.framework.exceptions;

public class PreconditionException extends RestException {

    public PreconditionException(int code, String message) {
        super(code, message);
    }

    public PreconditionException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
