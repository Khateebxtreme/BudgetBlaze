package com.budgetblaze.UserService.Service.Impl;

import com.budgetblaze.UserService.Dto.GenerateOTPDto;
import com.budgetblaze.UserService.Dto.UserRegistrationDto;
import com.budgetblaze.UserService.Exceptions.InvalidOTPException;
import com.budgetblaze.UserService.Exceptions.OtpNotGeneratedException;
import com.budgetblaze.UserService.Exceptions.UserAlreadyExistsException;
import com.budgetblaze.UserService.Exceptions.UserNotFoundException;
import com.budgetblaze.UserService.Model.User;
import com.budgetblaze.UserService.Model.UserOTPMST;
import com.budgetblaze.UserService.Repository.UserOTPRepository;
import com.budgetblaze.UserService.Repository.UserRepository;
import com.budgetblaze.UserService.Service.UserService;
import com.budgetblaze.UserService.Utils.Compunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserOTPRepository userOTPRepository;


    public UserServiceImpl(UserRepository userRepository,UserOTPRepository userOTPRepository) {
        this.userRepository = userRepository;
        this.userOTPRepository =userOTPRepository;

    }

    @Override
    public UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto) throws UserAlreadyExistsException {

        User user=null;
        if(userRegistrationDto.getEmail() !=null && !userRegistrationDto.getEmail().isEmpty() ){
            user =userRepository.findUserByemail(userRegistrationDto.getEmail());
        }
        if(userRegistrationDto.getPhone() !=null && !userRegistrationDto.getPhone().isEmpty()){
            user =userRepository.findUserBycontactDetails(userRegistrationDto.getPhone());

        }

        if(user == null){
            User user1 =new User();
            user1.setCreatedBy("SYSTEM");
            user1.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDto.getPassword()));
            user1.setIsVerified("false");
            user1.setContactDetails(userRegistrationDto.getPhone());
            user1.setEmail(userRegistrationDto.getEmail());
            //user1.setAddress(new Address("19","Wikens Street","jersey","USA","2013023"));
            userRepository.save(user1);

        }
        else{
            throw new UserAlreadyExistsException("User Already Exists");
        }

        return userRegistrationDto;
    }

    @Override
    public String generateOTP(GenerateOTPDto generateOTPDto) throws OtpNotGeneratedException, UserNotFoundException {
        String otp="";
        User user= null;
        //validate user in system before generating OTP
        if(generateOTPDto.getEmail() != null && !generateOTPDto.getEmail().isEmpty()){
            user =userRepository.findUserByemail(generateOTPDto.getEmail());
        }
        if(generateOTPDto.getPhone() != null && !generateOTPDto.getPhone().isEmpty()){
            user =userRepository.findUserBycontactDetails(generateOTPDto.getPhone());
        }

        if(user!=null){
            otp = String.valueOf(Compunctions.generateRandomNo(6));
            try {
                UserOTPMST userOTPMST = new UserOTPMST();
                userOTPMST.setUserName(user.getName());
                userOTPMST.setEmail(user.getEmail());
                userOTPMST.setContact(user.getContactDetails());
                userOTPMST.setOtp(otp);
                userOTPMST.setGeneratedBy("SYSTEM");
                userOTPMST.setFunctionType(generateOTPDto.getFunctionType());
                userOTPMST.setExpiryDate(userOTPMST.getGeneratedDate());
                userOTPRepository.save(userOTPMST);
            }
            catch (Exception e){
                throw new OtpNotGeneratedException("OTP can't be generated due to some exceptions");
            }
        }
        else {
            throw new UserNotFoundException("No Relevant user is found, can't generate OTP");
        }

        return otp;
    }


    @Override
    public String validateOtp(GenerateOTPDto generateOTPDto) throws InvalidOTPException, UserNotFoundException {

        String UniqueId="";

        if(generateOTPDto.getEmail() !=null && !generateOTPDto.getEmail().isEmpty()){
            List<UserOTPMST> users =userOTPRepository.findAll();
            for(UserOTPMST userOTPMST: users){
                //
                    if(userOTPMST.getOtp().equals(generateOTPDto.getOtp())) {
                        User user = null;
                        // fetch user in the system

                            user = userRepository.findUserByemail(generateOTPDto.getEmail());
                            if (user != null) {
                                user.setIsVerified("true");
                                UniqueId = "BB_Blaze_Usr" + user.getId();
                                user.setName(UniqueId);
                                userRepository.save(user);
                            }

                    }
                    else{
                        throw new InvalidOTPException("The Otp is invalid");
                    }

            }

        }
        else if(generateOTPDto.getPhone()!=null && !generateOTPDto.getPhone().isEmpty()){
            List<UserOTPMST> users =userOTPRepository.findAll();
            for(UserOTPMST userOTPMST: users){

                    if(userOTPMST.getOtp().equals(generateOTPDto.getOtp())){
                        User user=null;
                        // fetch user in the system
                        user =userRepository.findUserBycontactDetails(generateOTPDto.getPhone());

                        if (user != null) {
                            user.setIsVerified("true");
                            UniqueId="BB_Blaze_Usr"+user.getId();
                            user.setName(UniqueId);
                            userRepository.save(user);
                        }

                    }
                    else{
                        throw new InvalidOTPException("The Otp is invalid");
                    }

            }
        }

        else{
            throw new UserNotFoundException("Not Otp is found for the user");
        }

        return UniqueId;

    }
}
