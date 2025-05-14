package com.budgetblaze.UserService.Dto;


import lombok.*;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenerateOTPDto {

    private String functionType;
    private String email;
    private String otp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenerateOTPDto that = (GenerateOTPDto) o;
        return Objects.equals(functionType, that.functionType) && Objects.equals(email , that.email )  && Objects.equals(otp, that.otp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionType, email , otp);
    }
}
