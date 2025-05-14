package com.budgetblaze.UserService.Security.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //Purpose of the filter -> will make sure that every request has JWT token in it. Here, we will add operations that will basically help us to authenticate our requests. It will also load the user information.
    //Spring security identifies the class as a filter,so it will basically execute once per request.

    @Autowired
    private JwtUtils jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService; //interface to load user-specific data to the server

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //1) We first get JWT from the header part (i.e extract the token from our request)
            String jwt = jwtTokenProvider.getJwtFromHeader(request);

            //2) We then validate the token
            if(jwt!=null && jwtTokenProvider.validateToken(jwt)){
                //3) if the token is valid then we get User details, we get the username -> load User and set the auth context for the authenticated user
                String username = jwtTokenProvider.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(userDetails!=null){
                    //setting up auth context with the information from loaded user
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request,response); // by default, Spring Security doesn't automatically include our custom filters in filter chain unless we explicitly mention it like this. if this statement isn't mentioned, it will break the chain.
    }
}
