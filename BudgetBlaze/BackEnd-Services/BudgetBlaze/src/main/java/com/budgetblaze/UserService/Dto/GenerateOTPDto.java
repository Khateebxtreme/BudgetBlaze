package com.budgetblaze.UserService.Dto;


import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class GenerateOTPDto {

    private String functionType;
    private String email;
    private String phone;
    private String otp;

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email ;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String userPhone) {
        this.phone = userPhone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenerateOTPDto that = (GenerateOTPDto) o;
        return Objects.equals(functionType, that.functionType) && Objects.equals(email , that.email ) && Objects.equals(phone, that.phone) && Objects.equals(otp, that.otp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionType, email , phone, otp);
    }


}
