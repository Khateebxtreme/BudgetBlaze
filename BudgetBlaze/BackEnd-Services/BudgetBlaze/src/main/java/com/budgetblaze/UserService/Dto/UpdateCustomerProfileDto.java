package com.budgetblaze.UserService.Dto;

import com.budgetblaze.UserService.Model.Address;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerProfileDto {

    private String email;
    private String contactDetails;
    private String fullname;
    private String currency;
    private Integer monthly_income;
    @Setter
    @Getter
    private boolean rec_rem;

}
