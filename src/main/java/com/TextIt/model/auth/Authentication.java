package com.TextIt.model.auth;

public interface Authentication {

    public boolean verifyUsername(String username);
    public boolean verifyPassword(String password);
    public boolean verifyEmail(String email);
    public boolean verifyPhoneNumber(String phoneNumber);

}
