package com.budgetblaze.BudgetService.Repository;


import com.budgetblaze.BudgetService.Model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Integer> {




}
