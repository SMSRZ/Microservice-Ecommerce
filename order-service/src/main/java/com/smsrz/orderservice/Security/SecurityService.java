package com.smsrz.orderservice.Security;


import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public String getLoginUserName(){
        return "User";
    }
}
