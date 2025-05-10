package com.budgetblaze.UserService.Repository;

import com.budgetblaze.UserService.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    User findUserByemail(String email);
    User findUserBycontactDetails(String phone);
}
