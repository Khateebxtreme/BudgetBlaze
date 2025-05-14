package com.budgetblaze.UserService.Security.JWT;

import com.budgetblaze.UserService.Service.Impl.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    //values coming from application props or environment variables
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMS;


    //method to extract JWT token from the Authorization header. Authorization -> Bearer <TOKEN> is the format in which all requests that are authenticated are done. Spring security can use this token to validate id the request is from a trusted source or not. This is for endpoints that requires authenticated users to process requests.
    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken!=null && bearerToken.startsWith(("Bearer "))) {
            return bearerToken.substring(7); //only taking the substring after Bearer_space_, This extracts the token from the Bearer <Token> (JWT Header) if the token exists.
        }
        return null;
    }

    public String generateToken(UserDetailsImpl userDetails){
        //method to generate a token with the custom provided params or attributes
        String username = userDetails. getUsername();

        //generates a custom JWT token, we provide start and exp date for token generated (here, it is 2 days converted to milliseconds)
        return Jwts.builder().subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtExpirationMS)))
                .signWith(key())
                .compact();
    }

    private Key key(){
        //Key decoder for our JWT Secret -> To validate our signature as a user to the server.
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token){
        //we are verifying our request with help of key and the parsing our request to get username from our token
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean validateToken(String authToken){
        try {
            //if the below line is successful in execution, the token is valid and true state is returned to the user.
            Jwts.parser().verifyWith((SecretKey) key())
                    .build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
