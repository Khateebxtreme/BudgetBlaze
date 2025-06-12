package com.budgetblaze.BudgetService.Repository.Impl;


import com.budgetblaze.BudgetService.Exceptions.NoAllocatedBudgetForMonth;
import com.budgetblaze.BudgetService.Model.Budget;
import com.budgetblaze.BudgetService.Repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BudgetRepositoryImpl  {

    private BudgetRepository budgetRepository;


    public List<Budget> getMonthBudget(String month) throws NoAllocatedBudgetForMonth {
        List<Budget> monthBudgets =new ArrayList<>();
        List<Budget> budgets =budgetRepository.findAll();
        for(Budget budget:budgets){
            if(budget.getMonth().equals(month)){
                monthBudgets.add(budget);
            }
        }

        if(monthBudgets.size() ==0){
            throw new NoAllocatedBudgetForMonth("No Allocated Budget is found for the requested month");
        }

        return monthBudgets;
    }
}
