package com.budgetblaze.UserService.Service.Impl;


import com.budgetblaze.UserService.Model.User;
import com.budgetblaze.UserService.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*it will load the user from our database, and it will convert that user into object type that the spring security understands. This data will now be sent to authentication filter for validation.*/
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found with the username" + username));
        return UserDetailsImpl.build(user);
    }
}
