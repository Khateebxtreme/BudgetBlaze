package com.budgetblaze.BudgetService.Model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String createdBy;
    private String updatedBy;
    @DateTimeFormat
    @CreatedDate
    private Date createdAt;
    @DateTimeFormat
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return id == baseModel.id && Objects.equals(createdBy, baseModel.createdBy) && Objects.equals(updatedBy, baseModel.updatedBy) && Objects.equals(createdAt, baseModel.createdAt) && Objects.equals(updatedAt, baseModel.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy, updatedBy, createdAt, updatedAt);
    }


    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public BaseModel(int id, String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        this.id = id;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BaseModel() {
        super();
    }
}
