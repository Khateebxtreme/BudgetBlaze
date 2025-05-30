package com.budgetblaze.UserService.Service;

import com.budgetblaze.UserService.Dto.GenerateOTPDto;
import com.budgetblaze.UserService.Dto.LoginRequest;
import com.budgetblaze.UserService.Dto.UserRegistrationDto;
import com.budgetblaze.UserService.Exceptions.InvalidOTPException;
import com.budgetblaze.UserService.Exceptions.OtpNotGeneratedException;
import com.budgetblaze.UserService.Exceptions.UserAlreadyExistsException;
import com.budgetblaze.UserService.Exceptions.UserNotFoundException;
import com.budgetblaze.UserService.Dto.UpdateCustomerProfileDto;
import com.budgetblaze.UserService.Model.User;
import com.budgetblaze.UserService.Security.JWT.JwtAuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto) throws UserAlreadyExistsException, UserNotFoundException;
    public String generateOTP(GenerateOTPDto generateOTPDto) throws OtpNotGeneratedException, UserNotFoundException;
    public String validateOtp(GenerateOTPDto generateOTPDto) throws InvalidOTPException, UserNotFoundException;
    public User updateProfile(UpdateCustomerProfileDto updateCustomerProfileDto, String userId) throws UserNotFoundException;
    public User fetchProfile(String userId) throws UserNotFoundException;
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
}
