package com.budgetblaze.UserService.Security;


import com.budgetblaze.UserService.Security.JWT.JwtAuthenticationFilter;
import com.budgetblaze.UserService.Service.Impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    //defines security rules for handling different types of HTTP requests. (custom)

    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
    //A JWT Authentication Filter is a custom filter in Spring Security that intercepts incoming HTTP requests, checks for a JWT token in the Authorization header, validates the token, and if valid, sets the authentication context in the SecurityContext. This allows the application to recognize authenticated users based on the token and authorize access to certain resources.

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        //This will provide our app with the access to authManager
        return authenticationConfiguration.getAuthenticationManager();
    }
    //In Spring Security, the AuthenticationManager is used to validate and authenticate the credentials provided by the user. It is part of the authentication flow in which Spring Security checks whether the user credentials are correct. If authentication succeeds, Spring Security will establish an authentication token that represents the user, allowing access to protected resources.

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //It uses userDetailsService instance to load up the user details from our database and then our authProvider will match the data submitted with the loaded up user details to authenticate.
        authProvider.setUserDetailsService(userDetailsService);
        //The PasswordEncoder in Spring Security plays a crucial role in securely handling user passwords. Its primary job is to encode (hash) passwords before storing them and to verify (match) the hashed version of a password when a user logs in. It ensures that passwords are never stored in plaintext, protecting sensitive user data in the event of a database breach.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
        // Upon failure, an error is thrown and upon success, UsernamePasswordAuth token is created.
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //csrf is being disabled for the above endpoints, and also we are setting up which specific endpoints paths to authenticate and permit. These are the custom security rules for our HTTP requests as mentioned.
        http.cors(Customizer.withDefaults()).
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(auth ->
                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/profile/**").authenticated()
                        .anyRequest().permitAll()
        ); //currently all requests are permitted
        //Some Extra configs before returning the Security filter chain object.
        //1) We now have to configure an auth provider called DaoAuthProvider -> It authenticates users based on the data stored on our relational database
        http.authenticationProvider(authenticationProvider());
        //2) configure the filter chain to execute at a specific point in the security filter chain. Here, we are telling spring security to execute our custom filter first before username and password auth filter.
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

/*
Enable method Security allows us to use annotation in our program at method level and secure our methods.
*/
