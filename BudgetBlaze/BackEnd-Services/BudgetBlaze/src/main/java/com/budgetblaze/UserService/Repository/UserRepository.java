package com.budgetblaze.UserService.Repository;

import com.budgetblaze.UserService.Exceptions.UserNotFoundException;
import com.budgetblaze.UserService.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value="select u from BudgetBlaze_Users u where u.email=?1")
    User findUserByemail(String email) throws UserNotFoundException;

    Optional<User> findByUsername(String username);
    @Query("select u from BudgetBlaze_Users u where u.name=?1")
    User findUserByUserId(String UserId)throws UserNotFoundException;
}
