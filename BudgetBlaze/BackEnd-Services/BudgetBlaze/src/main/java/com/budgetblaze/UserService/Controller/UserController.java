package com.budgetblaze.UserService.Controller;

import com.budgetblaze.UserService.Dto.GenerateOTPDto;
import com.budgetblaze.UserService.Dto.UserRegistrationDto;
import com.budgetblaze.UserService.Exceptions.InvalidOTPException;
import com.budgetblaze.UserService.Exceptions.OtpNotGeneratedException;
import com.budgetblaze.UserService.Exceptions.UserAlreadyExistsException;
import com.budgetblaze.UserService.Exceptions.UserNotFoundException;
import com.budgetblaze.UserService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/UserService")

public class UserController {

    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Login code

    @PostMapping("/signUpUser")
    ResponseEntity<Map<String,Object>> signUpUser (@RequestBody UserRegistrationDto userRegDto ){

        Map<String,Object> userRegistrationRespMap = new HashMap<>();
        if(userRegDto == null){
            userRegistrationRespMap.put("Status","FAIL");
            userRegistrationRespMap.put("Message","NUll Input");
            return new ResponseEntity<Map<String, Object>>(userRegistrationRespMap,HttpStatus.BAD_REQUEST);

        }
        try{
            userRegDto = userService.registerUser(userRegDto);

        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            userRegistrationRespMap.put("Status","FAIL");
            userRegistrationRespMap.put("Message","User AlreadyExists!");
            userRegistrationRespMap.put("User",null);
            return new ResponseEntity<Map<String, Object>>(userRegistrationRespMap,HttpStatus.FOUND);

        }

        userRegistrationRespMap.put("Status","SUCCESS");
        userRegistrationRespMap.put("Message","User Partial Registration Successfully");
        userRegistrationRespMap.put("User",userRegDto);

        return new ResponseEntity<Map<String, Object>>(userRegistrationRespMap,HttpStatus.OK);

    }


    @PostMapping("/requestOTP")
    ResponseEntity<Map<String,Object>> generateOTP (@RequestBody GenerateOTPDto generateOTPDto ) throws OtpNotGeneratedException, UserNotFoundException {

        Map<String,Object> otpGenerateRespMap =new HashMap<>();
        if(generateOTPDto.getFunctionType() ==null || generateOTPDto.getFunctionType().isEmpty()){
            otpGenerateRespMap.put("Status","FAIL");
            otpGenerateRespMap.put("Message","No function found for Otp Generation");
            return new ResponseEntity<Map<String,Object>>(otpGenerateRespMap,HttpStatus.BAD_REQUEST);
        }

        String otpGenerated= userService.generateOTP(generateOTPDto);
        if(!otpGenerated.isEmpty()){
            otpGenerateRespMap.put("Status","SUCCESS");
            otpGenerateRespMap.put("Message","Otp Generation Success");
            otpGenerateRespMap.put("OTP",otpGenerated);
            return new ResponseEntity<Map<String,Object>>(otpGenerateRespMap,HttpStatus.OK);
        }
        otpGenerateRespMap.put("Status","FAIL");
        otpGenerateRespMap.put("Message","No User Found ! Generate OTP Fail");
        return new ResponseEntity<Map<String,Object>>(otpGenerateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @PostMapping("/validateOTP")
    ResponseEntity<Map<String,Object>> validateOTP (@RequestBody GenerateOTPDto generateOTPDto) throws UserNotFoundException, InvalidOTPException {
        String uniqueId="";
        Map<String,Object> otpValidateRespMap =new HashMap<>();
        if(generateOTPDto.getOtp() == null || generateOTPDto.getOtp().isEmpty()){
                otpValidateRespMap.put("Status","FAIL");
                otpValidateRespMap.put("Message","No inputs found for Otp validate");
                return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.BAD_REQUEST);

        }

        try{
            uniqueId = userService.validateOtp(generateOTPDto);

        }
        catch(InvalidOTPException ex){
            otpValidateRespMap.put("Status","FAIL");
            otpValidateRespMap.put("Message","Invalid otp ");
            otpValidateRespMap.put("UniqueId",null);
            return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UserNotFoundException ux){
            otpValidateRespMap.put("Status","FAIL");
            otpValidateRespMap.put("Message","No Relevant User found");
            otpValidateRespMap.put("UniqueId",null);
            return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(uniqueId.isEmpty()){
            otpValidateRespMap.put("Status","FAIL");
            otpValidateRespMap.put("Message","Unique Id Generation Exception");
            otpValidateRespMap.put("UniqueId",null);
            return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        otpValidateRespMap.put("Status","SUCCESS");
        otpValidateRespMap.put("Message","Unique Id Generation Success");
        otpValidateRespMap.put("UniqueId",uniqueId);
        return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.OK);


    }



}
