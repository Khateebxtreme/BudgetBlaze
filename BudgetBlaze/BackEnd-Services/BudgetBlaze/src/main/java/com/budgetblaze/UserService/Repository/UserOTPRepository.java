package com.budgetblaze.UserService.Repository;

import com.budgetblaze.UserService.Model.UserOTPMST;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOTPRepository extends JpaRepository<UserOTPMST, Integer> {

    @Query("select o from User_OTP_MST o where o.email=?1")
    List<UserOTPMST> findOtpValidateByEmail(String email);

}
