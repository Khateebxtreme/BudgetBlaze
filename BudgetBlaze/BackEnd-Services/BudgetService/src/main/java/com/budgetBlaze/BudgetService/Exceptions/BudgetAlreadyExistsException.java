package com.budgetblaze.BudgetService.Exceptions;

public class BudgetAlreadyExistsException extends Throwable{
    public BudgetAlreadyExistsException(String message) {
        super(message);
    }

    public BudgetAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BudgetAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
