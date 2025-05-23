package com.budgetblaze.UserService.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name="BudgetBlaze_Users")
public class User extends BaseModel{

    private String username;
    private String email;
    private String contactDetails;
    private String password;
    private String isVerified;
    private String userId;


    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;


}
