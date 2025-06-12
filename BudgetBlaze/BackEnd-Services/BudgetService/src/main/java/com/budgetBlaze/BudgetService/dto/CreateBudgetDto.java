package com.budgetblaze.BudgetService.dto;

import java.util.Objects;


public class CreateBudgetDto {

    private int budget_id;
    private int user_id;
    private int category_id;
    private float budgetAmount;
    private String month;


    public int getBudget_id() {
        return budget_id;
    }

    public void setBudget_id(int budget_id) {
        this.budget_id = budget_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public float getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(float budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateBudgetDto that = (CreateBudgetDto) o;
        return budget_id == that.budget_id && user_id == that.user_id && category_id == that.category_id && Float.compare(that.budgetAmount, budgetAmount) == 0 && Objects.equals(month, that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budget_id, user_id, category_id, budgetAmount, month);
    }


    @Override
    public String toString() {
        return "CreateBudgetDto{" +
                "budget_id=" + budget_id +
                ", user_id=" + user_id +
                ", category_id=" + category_id +
                ", budgetAmount=" + budgetAmount +
                ", month='" + month + '\'' +
                '}';
    }


    public CreateBudgetDto(int budget_id, int user_id, int category_id, float budgetAmount, String month) {
        this.budget_id = budget_id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.budgetAmount = budgetAmount;
        this.month = month;
    }

    public CreateBudgetDto() {
        super();
    }


}
