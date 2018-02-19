package com.proquest.ipa.automation.framework.exceptions;

public class RestCallException extends RuntimeException {

    public RestCallException() {
        super();
    }

    public RestCallException(String message) {
        super(message);
    }

    public RestCallException(Exception exception) {
        super(exception);
    }

    public RestCallException(String message, Exception exception) {
        super(message, exception);
    }

}
