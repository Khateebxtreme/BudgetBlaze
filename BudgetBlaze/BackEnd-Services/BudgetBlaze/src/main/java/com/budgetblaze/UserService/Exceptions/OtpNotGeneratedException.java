package com.budgetblaze.UserService.Exceptions;

public class OtpNotGeneratedException extends Throwable{

    public OtpNotGeneratedException(String message) {
        super(message);
    }

    public OtpNotGeneratedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtpNotGeneratedException(Throwable cause) {
        super(cause);
    }
}
