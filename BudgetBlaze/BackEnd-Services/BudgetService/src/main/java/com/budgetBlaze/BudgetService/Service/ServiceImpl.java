package com.budgetblaze.BudgetService.Service;


import com.budgetblaze.BudgetService.Exceptions.*;
import com.budgetblaze.BudgetService.Model.Budget;
import com.budgetblaze.BudgetService.Model.Category;
import com.budgetblaze.BudgetService.Repository.BudgetRepository;
import com.budgetblaze.BudgetService.Repository.CategoryRepository;
import com.budgetblaze.BudgetService.Repository.Impl.BudgetRepositoryImpl;
import com.budgetblaze.BudgetService.dto.CreateBudgetDto;
import com.budgetblaze.BudgetService.dto.CreateCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpl implements IService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BudgetRepositoryImpl budgetRepositoryImpl;

    public ServiceImpl(BudgetRepository budgetRepository, CategoryRepository categoryRepository, BudgetRepositoryImpl budgetRepositoryImpl) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.budgetRepositoryImpl = budgetRepositoryImpl;
    }


    @Override
    public Budget createNewBudget(CreateBudgetDto createBudgetDto) throws NoEligibleBudgetAmountException, RequestMonthGreaterThanCurrentMonthException, BudgetAlreadyExistsException {

        Budget budget = budgetRepository.findById(createBudgetDto.getBudget_id()).orElse(null);

        if(budget == null){
            Month requestMonth =convertMonth(createBudgetDto.getMonth());
            Month currentMonth = LocalDateTime.now().getMonth();
            //cat
            if(currentMonth.compareTo(requestMonth) > 1){

                if(createBudgetDto.getBudgetAmount() >1000){
                    budget =new Budget();
                    budget.setBudgetAmount(createBudgetDto.getBudgetAmount());
                    budget.setCategory_id(createBudgetDto.getCategory_id());
                    budget.setMonth(createBudgetDto.getMonth());
                    budget.setUser_id(createBudgetDto.getUser_id());
                    budgetRepository.save(budget);

                }
                else{
                    throw new NoEligibleBudgetAmountException("Minimum Amount for Budget Allocation should be 1000");
                }

            }
            else{
                throw new RequestMonthGreaterThanCurrentMonthException("The Requested month should be less than or equal to current Month");
            }

        }
        else{
            throw new BudgetAlreadyExistsException("Budget Already Exists,Please check");
        }

        
        return budget;
    }

    @Override
    public List<Budget> getNewBudget(CreateBudgetDto createBudgetDto) {

        return budgetRepository.findAll();

    }


    @Override
    public List<Budget> getMonthBudget(String month) throws NoAllocatedBudgetForMonth {

        return budgetRepositoryImpl.getMonthBudget(month);
    }

    @Override
    public Budget updateNewBudget(int budgetId,Budget budget) throws BudgetNotFoundException, NoPreviousMonthBudgetUpdateAllowedException {

        budget =budgetRepository.findById(budgetId).orElse(null);

        if(budget != null){
            Month requestedMonth =convertMonth(budget.getMonth());
            Month currentMonth =LocalDateTime.now().getMonth();

            if(currentMonth.compareTo(requestedMonth) < 1){
                budget.setBudgetAmount(budget.getBudgetAmount());
                budget.setCategory_id(budget.getCategory_id());
                budget.setMonth(budget.getMonth());
                budget.setUser_id(budget.getUser_id());
                budget.setUpdatedBy("ADMIN");
                budgetRepository.save(budget);
            }
            else{
                throw new NoPreviousMonthBudgetUpdateAllowedException("Previous Month couldn't be updated");
            }
        }
        else{
            throw new BudgetNotFoundException("The Requested Budget is not found in the system.");
        }

        return budget;

    }

    @Override
    public Boolean deleteNewBudget(int budgetId) throws BudgetNotFoundException {

        boolean isDeleted=false;
        Optional<Budget> budget =budgetRepository.findById(budgetId);
        if(budget != null){

            budgetRepository.delete(budget.get());
            isDeleted=true;

        }
        else{
            throw new BudgetNotFoundException("The Requested Budget is not found in the system.");
        }

        return isDeleted;

    }


    // Category


    @Override
    public Category createNewCategory(CreateCategoryDto createCategoryDto) throws CategoryAlreadyExistsException {

        Category category =categoryRepository.findById(createCategoryDto.getCategoryId()).orElse(null);
        if(category == null){
            category =new Category();
            category.setCategory_id(createCategoryDto.getCategoryId());
            category.setName(createCategoryDto.getName());
            categoryRepository.save(category);

        }
        else{
            throw new CategoryAlreadyExistsException("The Category already exists");
        }

        return category;
    }

    @Override
    public List<Category> getNewCategory(CreateCategoryDto createCategoryDto) {

        return categoryRepository.findAll();


    }


    @Override
    public Category updateNewCategory(int categoryId,Category category) throws CategoryNotFoundException {

        category = categoryRepository.findById(categoryId).orElse(null);
        if(category!=null){
            category.setName(category.getName());
            categoryRepository.save(category);

        }
        else{
            throw new CategoryNotFoundException("The Requested Category is not found in the system.");
        }

        return category;

    }

    @Override
    public Boolean deleteNewCategory(int catgoryId) throws CategoryNotFoundException {

        boolean isDeleted=false;
        Optional<Category> category =categoryRepository.findById(catgoryId);
        if(category != null){

            categoryRepository.delete(category.get());
            isDeleted=true;

        }
        else{
            throw new CategoryNotFoundException("The Requested Category is not found in the system.");
        }

        return isDeleted;

    }

    //Convert the MonthName to java.Month for comparison
    public Month convertMonth(String month){
        Month  m = null;
        switch (month){

            case "JAN":
                m =Month.JANUARY;
                break;
            case "FEB":
                m =Month.FEBRUARY;
                break;
            case "MAR":
                m =Month.MARCH;
                break;
            case "APR":
                m = Month.APRIL;
                break;
            case "MAY":
                m =Month.MAY;
                break;
            case "JUN":
                m =Month.JUNE;
                break;
            case "JUL":
                m =Month.JULY;
                break;
            case "AUG":
                m =Month.AUGUST;
                break;
            case "SEP":
                m =Month.SEPTEMBER;
                break;
            case "OCT":
                m =Month.OCTOBER;
                break;
            case "NOV":
                m =Month.NOVEMBER;
                break;
            case "DEC":
                m =Month.DECEMBER;
                break;

            default:
        }
        return m;
    }
}
