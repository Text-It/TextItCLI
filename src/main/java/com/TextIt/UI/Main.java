package com.TextIt.UI;

import com.TextIt.security.OTPHandler;
import com.TextIt.service.pages.Feed;


import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;


public class Main {
   static OTPHandler a = new OTPHandler();
    public static void main(String[] args) throws SQLException {
        AuthCLI start = new AuthCLI();
//        Feed f =new Feed();

        start.showWelcomeScreen();

    }
}