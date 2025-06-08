package com.budgetblaze.BudgetService.Model;

import jakarta.persistence.*;


import java.util.Objects;

@Entity
public class Category
{
    @Id
    private int category_id;

    @Column(name = "Category_Name")
    private String name;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return category_id == category.category_id && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", name='" + name + '\'' +
                '}';
    }

    public Category(int category_id, String name) {

        this.category_id = category_id;
        this.name = name;
    }

    public Category() {
        super();
    }
}
