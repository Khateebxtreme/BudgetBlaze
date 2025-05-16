package com.budgetblaze.UserService.Service.Impl;

import com.budgetblaze.UserService.Dto.GenerateOTPDto;
import com.budgetblaze.UserService.Dto.LoginRequest;
import com.budgetblaze.UserService.Dto.UserRegistrationDto;
import com.budgetblaze.UserService.Exceptions.InvalidOTPException;
import com.budgetblaze.UserService.Exceptions.OtpNotGeneratedException;
import com.budgetblaze.UserService.Exceptions.UserAlreadyExistsException;
import com.budgetblaze.UserService.Exceptions.UserNotFoundException;
import com.budgetblaze.UserService.Model.Address;
import com.budgetblaze.UserService.Model.UpdateCustomerProfileDto;
import com.budgetblaze.UserService.Model.User;
import com.budgetblaze.UserService.Model.UserOTPMST;
import com.budgetblaze.UserService.Repository.UserOTPRepository;
import com.budgetblaze.UserService.Repository.UserRepository;
import com.budgetblaze.UserService.Security.JWT.JwtAuthenticationResponse;
import com.budgetblaze.UserService.Security.JWT.JwtUtils;
import com.budgetblaze.UserService.Service.EmailSenderService;
import com.budgetblaze.UserService.Service.UserService;
import com.budgetblaze.UserService.Utils.Compunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager; //used to process an auth request passed to it and returns a fully populated auth object if successful

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserOTPRepository userOTPRepository;
    @Autowired
    EmailSenderService emailSenderService;

    public UserServiceImpl(PasswordEncoder passwordEncoder,JwtUtils jwtUtils,AuthenticationManager authenticationManager,UserRepository userRepository, UserOTPRepository userOTPRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.userOTPRepository = userOTPRepository;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    //Service class methods to Register and verify the user into the system.

    @Override
    public UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto) throws UserAlreadyExistsException {
        //Service class method to register a user into the system.

        User user=null; //default state for user object.

        //checks if the incoming request has user email in it or not.
        if(userRegistrationDto.getEmail()!=null && !userRegistrationDto.getEmail().isEmpty() ){
            //checks if the user is already available in the system, if their email is available from the request.
            user =userRepository.findUserByemail(userRegistrationDto.getEmail());
        }
        if(user == null){
            //situation for when the user is not found in the system, a new user is created with required details.
            User user1 =new User();
            user1.setCreatedBy("SYSTEM");
            user1.setPassword(new BCryptPasswordEncoder().encode(userRegistrationDto.getPassword())); //encoding the password coming from request using BCrypt
            user1.setIsVerified("false"); //user verification status
            user1.setEmail(userRegistrationDto.getEmail()); //setting up their email coming from request.
            //user1.setAddress(new Address("19","Wikens Street","jersey","USA","2013023"));
            userRepository.save(user1); //user data is now persisted in the repo.
        }
        else{
            throw new UserAlreadyExistsException("User Already Exists");
        }
        return userRegistrationDto;
    }

    @Override
    public String generateOTP(GenerateOTPDto generateOTPDto) throws OtpNotGeneratedException, UserNotFoundException {
        //Service class method for a user to generate OTP and send it in their provided email

        //default states for user and otp values
        String otp="";
        User user= null;

        //checks if the request has user's email or not.
        if(generateOTPDto.getEmail()!= null && !generateOTPDto.getEmail().isEmpty()){
            //if email is found, we check if he is persisted into the system or not.
            user =userRepository.findUserByemail(generateOTPDto.getEmail());
        }

        if(user!=null){
            //situation for when the user is available in the system.

            otp = String.valueOf(Compunctions.generateRandomNo(6)); //random six digit combination generated to be sent as OTP.

            // persisting the OTP into the system to keep track of it and sending it in a mail to the user.
            try {
                UserOTPMST userOTPMST = new UserOTPMST();
                userOTPMST.setUserName(user.getUsername());
                userOTPMST.setEmail(user.getEmail());
                userOTPMST.setContact(user.getContactDetails());
                userOTPMST.setOtp(otp);
                userOTPMST.setGeneratedBy("SYSTEM");
                userOTPMST.setFunctionType(generateOTPDto.getFunctionType());
                userOTPMST.setExpiryDate(userOTPMST.getGeneratedDate());
                userOTPRepository.save(userOTPMST); //OTP is persisted into the system with required details.
                // Send Email Service
                emailSenderService.EmailSender(generateOTPDto.getEmail(),
                        "BudgetBlaze : Customer Registration (User Verification Mail)",
                        "Hello, Please find Your Otp for completing the registration process.\n Note : This will be valid only for next 3 minutes."+ "\n" +"OTP : "+ otp);
            }
            catch (Exception e){
                //situation for general exception occurring during OTP generation process
                throw new OtpNotGeneratedException("OTP can't be generated due to some exceptions");
            }
        }
        else {
            //failure of OTP generation if the user is unavailable in the system.
            throw new UserNotFoundException("No Relevant user is found, can't generate OTP");
        }
        return otp;
    }


    @Override
    public String validateOtp(GenerateOTPDto generateOTPDto) throws InvalidOTPException, UserNotFoundException {
        //Service class method to validate the OTP coming from the request.

        String UniqueId=""; //stores the username generated upon successful validation.

        if(generateOTPDto.getEmail() !=null && !generateOTPDto.getEmail().isEmpty()){
            //checks if the email coming from the request has generated an OTP into the system or not.
            List<UserOTPMST> users =userOTPRepository.findOtpValidateByEmail(generateOTPDto.getEmail(), Sort.by("email"));

            //List<UserOTPMST> users =userOTPRepository.findAll();
            for(UserOTPMST userOTPMST: users){
                if (userOTPMST != null) {
                    if(userOTPMST.getOtp().equals(generateOTPDto.getOtp())) {
                        //situation to deal with upon successful matching of OTP

                        User user = null;

                        user = userRepository.findUserByemail(generateOTPDto.getEmail()); //fetches user from the system.
                        if (user != null) {
                            user.setIsVerified("true"); //updating user's verification status
                            String email = generateOTPDto.getEmail();
                            UniqueId = email.substring(0,email.indexOf("@")).replaceAll("[^a-zA-Z0-9]", "_");
                            user.setUsername(UniqueId);
                            userRepository.save(user); //persisting the user into the system with updated changes.
                        }

                    }
                else{
                    //situation for when there is a mismatch in OTP
                    throw new InvalidOTPException("The Otp is invalid");
                }
                }
            }

        }
        else{
            //situation for when the user is not found in the system.
            throw new UserNotFoundException("Not Otp is found for the user");
        }
        return UniqueId;
    }

    //Service Class Methods to log in the user into the system.

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest){
        //if the provided username and password are valid then we will have an authentication object over here. We are creating an instance of usernamePasswordAuthenticationToken, which is class provided to us by Spring Security which is designed to be a simple presentation of username and password (i.e. User Credentials) and we are getting the credentials through our Login request.The instance is now provided to authenticate method of auth manager. validity is checked, through security configurations in security package. - refer how validation is done
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        //We are now setting up security context. Spring Security will hold the authentication data for current request/session.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //As generate token method requires a userDetailsImpl instance, we can do it through the getPrincipal() method. Through this method, we are giving access to an instance of the mentioned object.
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //We now generate a token to be sent as a response to the user.
        String jwt = jwtUtils.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with the username -> "+ username)
        );
    }

    public boolean isUserNamePresent(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Boolean updateProfile(UpdateCustomerProfileDto updateCustomerProfileDto, String userId) throws UserNotFoundException {
        boolean isUpdated =false;User user =null;

        user = userRepository.findUserByUserId(userId);
        if(user!=null){
            user.setUsername(updateCustomerProfileDto.getName());
            user.setEmail(updateCustomerProfileDto.getEmail());
            user.setContactDetails(updateCustomerProfileDto.getContactDetails());
            user.setAddress(updateCustomerProfileDto.getAddress());

            user =userRepository.save(user);
            if(user !=null){
                isUpdated=true;
            }
        }
        else{
            throw new UserNotFoundException("No Valid User Found");
        }


        return isUpdated;

    }

}
