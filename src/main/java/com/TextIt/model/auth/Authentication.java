package com.TextIt.model.auth;

public interface Authentication {

     boolean verifyUsername(String username);
     boolean verifyPassword(String password);
     boolean verifyEmail(String email);
     boolean verifyPhoneNumber(String phoneNumber);

}
