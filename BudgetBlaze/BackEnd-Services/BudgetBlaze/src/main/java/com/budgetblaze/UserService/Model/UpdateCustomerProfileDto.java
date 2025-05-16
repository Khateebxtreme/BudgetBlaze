package com.budgetblaze.UserService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerProfileDto {

    private String name;
    private String email;
    private String contactDetails;
    private Address address;

}
