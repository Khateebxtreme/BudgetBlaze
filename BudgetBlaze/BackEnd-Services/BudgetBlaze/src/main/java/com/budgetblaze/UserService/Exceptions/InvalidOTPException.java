package com.budgetblaze.UserService.Exceptions;

public class InvalidOTPException extends Throwable{
    public InvalidOTPException(String message) {
        super(message);
    }

    public InvalidOTPException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOTPException(Throwable cause) {
        super(cause);
    }
}
