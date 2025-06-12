package com.budgetblaze.BudgetService.Exceptions;

public class BudgetNotFoundException extends Throwable  {

    public BudgetNotFoundException(String message) {
        super(message);
    }

    public BudgetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BudgetNotFoundException(Throwable cause) {
        super(cause);
    }

}
