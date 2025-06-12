package com.budgetblaze.BudgetService.dto;

import java.util.Objects;

public class CreateCategoryDto {

    private int categoryId;
    private String name;


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
        CreateCategoryDto that = (CreateCategoryDto) o;
        return categoryId == that.categoryId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name);
    }

    @Override
    public String toString() {
        return "CreateCategoryDto{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }

    public CreateCategoryDto(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public CreateCategoryDto() {
        super();
    }
}
