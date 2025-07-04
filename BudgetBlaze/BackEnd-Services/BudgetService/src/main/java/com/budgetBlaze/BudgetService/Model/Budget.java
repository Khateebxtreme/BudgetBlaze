package com.budgetblaze.BudgetService.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


import java.util.Date;
import java.util.Objects;

@Entity(name="Budget_Allocated")

public class Budget extends BaseModel {

    private int user_id;
    private int category_id;
    private float budgetAmount;
    private float budgetExhausted;
    @Enumerated(EnumType.STRING)
    private C_urrency currency;
    private String month;

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

    public C_urrency getCurrency() {
        return currency;
    }

    public void setCurrency(C_urrency currency) {
        this.currency = currency;
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
        if (!super.equals(o)) return false;
        Budget budget = (Budget) o;
        return user_id == budget.user_id && category_id == budget.category_id && Float.compare(budget.budgetAmount, budgetAmount) == 0 && currency == budget.currency && Objects.equals(month, budget.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user_id, category_id, budgetAmount, currency, month);
    }


    @Override
    public String toString() {
        return "Budget{" +
                "user_id=" + user_id +
                ", category_id=" + category_id +
                ", budgetAmount=" + budgetAmount +
                ", currency=" + currency +
                ", month='" + month + '\'' +
                '}';
    }



    public Budget(int user_id, int category_id, float budgetAmount, C_urrency currency, String month) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.budgetAmount = budgetAmount;
        this.currency = currency;
        this.month = month;
    }


    public Budget() {
        super();
    }
}
