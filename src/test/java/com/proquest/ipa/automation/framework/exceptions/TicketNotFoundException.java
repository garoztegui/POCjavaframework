package com.proquest.ipa.automation.framework.exceptions;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException() {
        super();
    }

    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException(Exception exception) {
        super(exception);
    }

    public TicketNotFoundException(String message, Exception exception) {
        super(message, exception);
    }

}
