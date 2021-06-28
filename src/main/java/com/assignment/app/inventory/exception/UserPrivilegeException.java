package com.assignment.app.inventory.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPrivilegeException extends Exception {

    private String message;
    private String errorCode;

    public UserPrivilegeException(String message) {
        super(message);
        this.message = message;
    }
}
