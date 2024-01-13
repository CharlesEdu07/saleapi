package com.charlesedu.saleapi.services.exceptions;

public class InactiveModelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InactiveModelException(Object id) {
        super("This model is inactive. Id " + id);
    }
}
