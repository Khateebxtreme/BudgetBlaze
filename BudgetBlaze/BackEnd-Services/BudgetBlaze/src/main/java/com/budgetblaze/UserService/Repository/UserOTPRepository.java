package com.budgetblaze.UserService.Repository;

import com.budgetblaze.UserService.Model.UserOTPMST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOTPRepository extends JpaRepository<UserOTPMST, Integer> {


}
