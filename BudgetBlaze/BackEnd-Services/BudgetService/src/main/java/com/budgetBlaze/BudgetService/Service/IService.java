package com.budgetblaze.BudgetService.Service;


import com.budgetblaze.BudgetService.Exceptions.*;
import com.budgetblaze.BudgetService.Model.Budget;
import com.budgetblaze.BudgetService.Model.Category;
import com.budgetblaze.BudgetService.dto.CreateBudgetDto;
import com.budgetblaze.BudgetService.dto.CreateCategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IService {

    //Budget

    Budget createNewBudget(CreateBudgetDto createBudgetDto) throws NoEligibleBudgetAmountException, RequestMonthGreaterThanCurrentMonthException, BudgetAlreadyExistsException;
    List<Budget> getNewBudget(CreateBudgetDto createBudgetDto);
    List<Budget> getMonthBudget(String month) throws NoAllocatedBudgetForMonth;
    Budget updateNewBudget(int budgetId ,Budget budget) throws BudgetNotFoundException, NoPreviousMonthBudgetUpdateAllowedException;
    Boolean deleteNewBudget(int budgetId) throws BudgetNotFoundException;

    //Category

    Category createNewCategory(CreateCategoryDto createCategoryDto) throws CategoryAlreadyExistsException;
    List<Category> getNewCategory(CreateCategoryDto createCategoryDto);
    Category updateNewCategory(int categoryId, Category category) throws CategoryNotFoundException;
    Boolean deleteNewCategory(int categoryId) throws CategoryNotFoundException;

}
