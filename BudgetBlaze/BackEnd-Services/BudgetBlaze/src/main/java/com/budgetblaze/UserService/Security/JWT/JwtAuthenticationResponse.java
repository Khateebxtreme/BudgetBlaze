package com.budgetblaze.UserService.Security.JWT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token; //provides JWT Auth token to Front-end
}
