package com.TextIt.UI;

import com.TextIt.security.OTPHandler;



public class Main {
   static OTPHandler a = new OTPHandler();
    public static void main(String[] args) throws Exception {
        AuthCLI start = new AuthCLI();
        start.showWelcomeScreen();

    }


}