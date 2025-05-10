package com.budgetblaze.UserService.Model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name="BudgetBlaze_Users_Address")
public class Address extends BaseModel{

    private String hNo;
    private String street;
    private String city;
    private String country;
    private String pinCode;
}
