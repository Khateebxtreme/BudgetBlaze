package com.budgetblaze.UserService.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name="User_OTP_MST")
public class UserOTPMST {

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private int id;

    @Column(name="Function")
    private String functionType;

    @Column(name="Otp_Generated")
    private String otp;
    @Column(name="User_Email")
    private String email;

    @Column
    private String contact;

    @Column
    private String userName;

    @Column
    @CreatedBy
    private String generatedBy;

    @DateTimeFormat
    @CreatedDate
    private LocalDateTime generatedDate;

    @DateTimeFormat
    @CreatedDate
    private LocalDateTime expiryDate;

}
