package com.budgetblaze.UserService.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(name="Otp Generated")
    private String otp;
    @Column(name="User Email")
    private String email;

    @Column
    private String contact;

    @Column
    private String userName;

    @Column
    @CreatedBy
    private String generatedBy;

    @Temporal(value =TemporalType.TIMESTAMP)
    @CreatedDate
    private String generatedDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private String expiryDate;

}
