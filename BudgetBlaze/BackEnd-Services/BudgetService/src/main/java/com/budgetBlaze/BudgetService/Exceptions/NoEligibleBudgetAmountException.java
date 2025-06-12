package com.budgetblaze.BudgetService.Exceptions;

public class NoEligibleBudgetAmountException extends Throwable{
    public NoEligibleBudgetAmountException(String message) {
        super(message);
    }

    public NoEligibleBudgetAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEligibleBudgetAmountException(Throwable cause) {
        super(cause);
    }
}
