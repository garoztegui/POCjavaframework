package com.proquest.ipa.automation.framework.exceptions;


public class PermissionsException extends RestException {


    public PermissionsException(int code, String message) {
        super(code, message);
    }

    public PermissionsException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
