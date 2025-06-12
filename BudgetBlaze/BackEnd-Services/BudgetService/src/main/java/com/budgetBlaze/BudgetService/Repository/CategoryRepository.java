package com.budgetblaze.BudgetService.Repository;


import com.budgetblaze.BudgetService.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
