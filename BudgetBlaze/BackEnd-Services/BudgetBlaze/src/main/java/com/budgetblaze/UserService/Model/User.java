package com.budgetblaze.UserService.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="BudgetBlaze_Users")
public class User extends BaseModel{

    private String username;
    private String email;
    private String contactDetails;
    private String password;
    private String isVerified;
    private String fullname;
    private String currency;
    @Getter
    @Setter
    private Integer monthly_income;
    private boolean rec_rem;


    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;

    @PrePersist
    private void setDefaults() {
        if (this.username == null) {
            this.username = "Guest";
        }
        if (this.currency == null) {
            this.currency = "INR";
        }
        if (this.monthly_income == null) {
            this.monthly_income = 0;
        }
    }
}
