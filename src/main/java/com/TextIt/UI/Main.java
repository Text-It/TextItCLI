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
        String recipientEmail = "dhruvharani8@gmail.com";
        String otp = a.generateOTP(6);
        AuthCLI o = new AuthCLI();
        Feed f =new Feed();

        try {
            a.sendOTP(recipientEmail, otp);
            System.out.println("✅ OTP sent successfully to " + recipientEmail);
        } catch (AuthenticationFailedException e) {
            System.err.println("❌ Authentication failed: Invalid email/password. Make sure to use Gmail App Password.");
        } catch (SendFailedException e) {
            System.err.println("❌ Email sending failed: Invalid recipient address or network error.");
        } catch (MessagingException e) {
            System.err.println("❌ Messaging error: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("❌ Encoding error while setting sender name.");
        } catch (Exception e) {
            System.err.println("❌ Unexpected error occurred: " + e.getMessage());
        }

        o.showWelcomeScreen();


    }
}