package com.budgetblaze.BudgetService.Exceptions;

public class RequestMonthGreaterThanCurrentMonthException extends Throwable{
    public RequestMonthGreaterThanCurrentMonthException(String message) {
        super(message);
    }

    public RequestMonthGreaterThanCurrentMonthException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestMonthGreaterThanCurrentMonthException(Throwable cause) {
        super(cause);
    }
}
