package com.budgetblaze.UserService.Dto;

import lombok.Data;

import java.util.Objects;

@Data
public class UserRegistrationDto {

    private String email;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationDto that = (UserRegistrationDto) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "emailId='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public UserRegistrationDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserRegistrationDto() {
        super();
    }
}
