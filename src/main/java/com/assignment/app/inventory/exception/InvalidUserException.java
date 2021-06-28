package com.assignment.app.inventory.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidUserException extends Exception {

    private String message;
    private String errorCode;

    public InvalidUserException(String message) {
        super(message);
        this.message = message;
    }
}
