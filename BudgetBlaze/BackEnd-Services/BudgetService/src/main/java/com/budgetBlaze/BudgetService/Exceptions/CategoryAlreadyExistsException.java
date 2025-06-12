package com.budgetblaze.BudgetService.Exceptions;

public class CategoryAlreadyExistsException extends Throwable{

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    public CategoryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
