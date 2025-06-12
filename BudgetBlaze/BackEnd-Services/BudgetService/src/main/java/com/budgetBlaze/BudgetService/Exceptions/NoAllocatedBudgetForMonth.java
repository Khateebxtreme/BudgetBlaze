package com.budgetblaze.BudgetService.Exceptions;

public class NoAllocatedBudgetForMonth extends Throwable{
    public NoAllocatedBudgetForMonth(String message) {
        super(message);
    }

    public NoAllocatedBudgetForMonth(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAllocatedBudgetForMonth(Throwable cause) {
        super(cause);
    }
}
