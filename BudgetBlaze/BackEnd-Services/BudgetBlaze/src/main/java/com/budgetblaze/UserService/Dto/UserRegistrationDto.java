package com.budgetblaze.UserService.Dto;

import java.util.Objects;

public class UserRegistrationDto {

    private String email;
    private String phone;
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationDto that = (UserRegistrationDto) o;
        return Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phone, password);
    }

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "emailId='" + email + '\'' +
                ", userPhone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserRegistrationDto(String email, String phone, String password) {
        this.email = email;
        this.phone = phone;
        this.password = password;
    }


    public UserRegistrationDto() {
        super();
    }
}
