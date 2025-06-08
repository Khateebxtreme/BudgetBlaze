package com.budgetblaze.BudgetService.Exceptions;

public class NoPreviousMonthBudgetUpdateAllowedException extends Throwable{
    public NoPreviousMonthBudgetUpdateAllowedException(String message) {
        super(message);
    }

    public NoPreviousMonthBudgetUpdateAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPreviousMonthBudgetUpdateAllowedException(Throwable cause) {
        super(cause);
    }
}
