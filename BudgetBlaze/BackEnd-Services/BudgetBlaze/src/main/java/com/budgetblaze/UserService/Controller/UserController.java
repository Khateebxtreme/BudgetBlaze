package com.budgetblaze.UserService.Controller;

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
import com.budgetblaze.UserService.Service.EmailSenderService;
import com.budgetblaze.UserService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/UserService")

public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailSenderService emailSenderService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Controllers to Register and Verify a user into the system.

    @PostMapping("/signUpUser")
    ResponseEntity<Map<String,Object>> signUpUser (@RequestBody UserRegistrationDto userRegDto ){
        //controller to register the user into the system.

        Map<String,Object> userRegistrationRespMap = new HashMap<>(); //stores response that would be provided to the user.

        if(userRegDto == null){
            //if there is no data coming from front-end, below is the response returned
            userRegistrationRespMap.put("Status","FAIL");
            userRegistrationRespMap.put("Message","NUll Input");
            return new ResponseEntity<Map<String, Object>>(userRegistrationRespMap,HttpStatus.BAD_REQUEST);

        }
        try{
            //We try to register the user when appropriate data is coming from the front-end.
            userRegDto = userService.registerUser(userRegDto);

        } catch (UserAlreadyExistsException | UserNotFoundException e) {
            //Checks if the register request is for new users or not, if there is a duplicate user or if the user is already available in the system, this exception is thrown.
            e.printStackTrace();
            userRegistrationRespMap.put("Status","FAIL");
            userRegistrationRespMap.put("Message","User AlreadyExists!");
            userRegistrationRespMap.put("User",null);
            return new ResponseEntity<Map<String, Object>>(userRegistrationRespMap,HttpStatus.FOUND);

        }
        //marks for situation when the user is successfully registered and is now a part of the system.
        userRegistrationRespMap.put("Status","SUCCESS");
        userRegistrationRespMap.put("Message","User Partial Registration Successfully");
        userRegistrationRespMap.put("User",userRegDto);

        return new ResponseEntity<Map<String, Object>>(userRegistrationRespMap,HttpStatus.OK);
    }


    @PostMapping("/requestOTP")
    ResponseEntity<Map<String,Object>> generateOTP (@RequestBody GenerateOTPDto generateOTPDto ) throws OtpNotGeneratedException, UserNotFoundException {
        //controller to generate the OTP when user clicks the Generate OTP button, The generated OTP is now sent the user's email address which can be used for his or her verification into the system.

        Map<String,Object> otpGenerateRespMap =new HashMap<>();
        String otpGenerated;

        if(generateOTPDto.getFunctionType() ==null || generateOTPDto.getFunctionType().isEmpty()){
            //If the function type is not coming from the front-end request, This response is generated and an error is thrown to the user.
            otpGenerateRespMap.put("Status","FAIL");
            otpGenerateRespMap.put("Message","No function found for Otp Generation");
            return new ResponseEntity<Map<String,Object>>(otpGenerateRespMap,HttpStatus.BAD_REQUEST);
        }

        try{
            //OTP is generated upon validating the structure of request coming from Front-end.
            otpGenerated = userService.generateOTP(generateOTPDto);
        }
        catch (OtpNotGeneratedException op){
            //Catches the error for situations where OTP generation process fails and appropriate response is provided to the user.
            otpGenerateRespMap.put("Status","FAIL");
            otpGenerateRespMap.put("Message","Generate OTP Fail");
            return new ResponseEntity<Map<String,Object>>(otpGenerateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        catch (UserNotFoundException uf){
            //Catches the error for situations where the User data doesn't exist for whom the OTP is being generated for and appropriate response is provided to the user.
            otpGenerateRespMap.put("Status","FAIL");
            otpGenerateRespMap.put("Message","No User Found ! Generate OTP Fail");
            return new ResponseEntity<Map<String,Object>>(otpGenerateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);

        }
        if(!otpGenerated.isEmpty()){
            //Marks for the situation where OTP generation process succeeds.
            otpGenerateRespMap.put("Status","SUCCESS");
            otpGenerateRespMap.put("Message","Otp Generation Success");
            otpGenerateRespMap.put("OTP",otpGenerated);
            return new ResponseEntity<Map<String,Object>>(otpGenerateRespMap,HttpStatus.OK);
        }

        //Marks for any other kinds of failure that might arise in the OTP generation process.
        otpGenerateRespMap.put("Status","FAIL");
        otpGenerateRespMap.put("Message","No User Found ! Generate OTP Fail");
        return new ResponseEntity<Map<String,Object>>(otpGenerateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/validateOTP")
    ResponseEntity<Map<String,Object>> validateOTP (@RequestBody GenerateOTPDto generateOTPDto) throws UserNotFoundException, InvalidOTPException {
        //controller to validate the OTP entered by the user and matches it to check if the OTP generated matches with what the user entered. if the match is successful, user is entered into the system and provided a username otherwise an error is thrown.

        String uniqueId=""; //stores the unique username generated for the user upon successful verification.

        Map<String,Object> otpValidateRespMap =new HashMap<>();

        if(generateOTPDto.getOtp() == null || generateOTPDto.getOtp().isEmpty()){
            //if no OTP is coming from the registration request.
            otpValidateRespMap.put("Status","FAIL");
            otpValidateRespMap.put("Message","No OTP found in the request.");
            return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.BAD_REQUEST);
        }

        try{
            //initiates the process for OTP validation of the user.
            uniqueId = userService.validateOtp(generateOTPDto);
        }
        catch(InvalidOTPException ex){
            //situation for when the OTP coming from the request is not matching with what was generated.
            otpValidateRespMap.put("Status","FAIL");
            otpValidateRespMap.put("Message","Invalid otp ");
            otpValidateRespMap.put("UniqueId",null);
            return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (UserNotFoundException ux){
            //marks for the situation if user is not part of the system and is trying to generate OTP.
            otpValidateRespMap.put("Status","FAIL");
            otpValidateRespMap.put("Message","No Relevant User found");
            otpValidateRespMap.put("UniqueId",null);
            return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(uniqueId.isEmpty()){
            //marks for the situation if username fails to generate, which is required for user login.
            otpValidateRespMap.put("Status","FAIL");
            otpValidateRespMap.put("Message","Unique Id Generation Exception");
            otpValidateRespMap.put("UniqueId",null);
            return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //upon successful validation, this response is returned.
        otpValidateRespMap.put("Status","SUCCESS");
        otpValidateRespMap.put("Message","Unique Id Generation Success");
        otpValidateRespMap.put("UniqueId",uniqueId);
        return new ResponseEntity<Map<String,Object>>(otpValidateRespMap,HttpStatus.OK);
    }

    //Controllers to log in a user into the system

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> loginUser(@RequestBody LoginRequest loginRequest){
        //login functionality will do the job of authenticating our users against the provided credentials which we have stored in our database, once user is validated, we would be getting back to the user along with a success status and token.

        //To authenticate our users, we will make use of a service class UserService's method - authenticate user
        //once the token is returned, users can use the provided token for subsequent requests or accessing authenticated apis.
        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    // User Profile -> endpoints
    @GetMapping("/profile/{username}")
    ResponseEntity<Map<String,Object>> getCustomerProfile(@PathVariable ("username") String username) throws UserNotFoundException {

        Map<String,Object> customerProfileResponseMap =new HashMap<>();
        if(username == null && username.equals("")){

            customerProfileResponseMap.put("status","false");
            customerProfileResponseMap.put("message","No Inputs Found");
            return new ResponseEntity<Map<String,Object>>(customerProfileResponseMap,HttpStatus.BAD_REQUEST);
        }

        User userDetails = userService.fetchProfile(username);

        if(userDetails !=null){
            customerProfileResponseMap.put("status","true");
            customerProfileResponseMap.put("message","Successfully Details Fetched");
            customerProfileResponseMap.put("User",userDetails);
            return new ResponseEntity<Map<String,Object>>(customerProfileResponseMap,HttpStatus.OK);

        }
        else{
            customerProfileResponseMap.put("status","false");
            customerProfileResponseMap.put("message","Some Exception occurred Fetching User Details");
            return new ResponseEntity<Map<String,Object>>(customerProfileResponseMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/updateProfile/{username}")
    ResponseEntity<Map<String,Object>> updateCustomerProfile(@PathVariable("username") String username, @RequestBody UpdateCustomerProfileDto updateCustomerProfileDto) throws UserNotFoundException {

        Map<String,Object> customerProfileResponseMap =new HashMap<>();
        if(updateCustomerProfileDto == null && updateCustomerProfileDto.equals("")){

            customerProfileResponseMap.put("status","false");
            customerProfileResponseMap.put("message","No Inputs Found");
            return new ResponseEntity<Map<String,Object>>(customerProfileResponseMap,HttpStatus.BAD_REQUEST);
        }
        User user =userService.updateProfile(updateCustomerProfileDto,username);

        if(user!=null){
            customerProfileResponseMap.put("status","true");
            customerProfileResponseMap.put("message","Successfully Details Updated");
            customerProfileResponseMap.put("user",user);
            return new ResponseEntity<Map<String,Object>>(customerProfileResponseMap,HttpStatus.OK);

        }
        else{
            customerProfileResponseMap.put("status","false");
            customerProfileResponseMap.put("message","Some Exception occurred Updating User Details");
            return new ResponseEntity<Map<String,Object>>(customerProfileResponseMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
