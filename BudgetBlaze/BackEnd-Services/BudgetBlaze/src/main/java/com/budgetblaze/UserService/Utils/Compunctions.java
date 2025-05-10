package com.budgetblaze.UserService.Utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Compunctions {

    //Gnerate Random Otp
    public static char[] generateRandomNo (int len)
    {

        String numbers = "0123456789";

        // Using random method
        Random rndm_method = new Random();
        char[] otp = new char[len];
        for (int i = 0; i < len; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }
}
