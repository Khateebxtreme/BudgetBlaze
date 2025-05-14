package com.budgetblaze.UserService.Service.Impl;

import com.budgetblaze.UserService.Model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails{
    private static final long serialVersionUID = 1L;
    /*identifies uniqueness of a serializable class. By setting a serialVersionUID, you're giving Java a way to check that the version of the class being loaded matches the version it was saved with. If they don’t match, Java will throw an exception.*/
    private Long id;
    private String username;
    private String email;
    private String password;

    public UserDetailsImpl(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //build method which will help us build the instance of UserDetailsImpl. The purpose of this method is to convert a User object from our database into a UserDetailsImpl option for spring security.
    public static UserDetailsImpl build(User user){
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
