package com.budgetblaze.UserService.Service;

import com.budgetblaze.UserService.Dto.GenerateOTPDto;
import com.budgetblaze.UserService.Dto.UserRegistrationDto;
import com.budgetblaze.UserService.Exceptions.InvalidOTPException;
import com.budgetblaze.UserService.Exceptions.OtpNotGeneratedException;
import com.budgetblaze.UserService.Exceptions.UserAlreadyExistsException;
import com.budgetblaze.UserService.Exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto) throws UserAlreadyExistsException;
    public String generateOTP(GenerateOTPDto generateOTPDto) throws OtpNotGeneratedException, UserNotFoundException;
    public String validateOtp(GenerateOTPDto generateOTPDto) throws InvalidOTPException, UserNotFoundException;
}
