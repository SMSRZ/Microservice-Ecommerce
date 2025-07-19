package com.smsrz.orderservice.Security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public String getLoginUserName(){
//        return "User";

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt)  jwtAuthenticationToken.getPrincipal();
        System.out.println(jwt.getClaimAsString(jwtAuthenticationToken.getName()));
        return jwt.getClaimAsString("preferred_username");

    }
}
