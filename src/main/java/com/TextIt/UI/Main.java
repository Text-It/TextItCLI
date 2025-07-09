package com.TextIt.UI;

import com.TextIt.security.OTPHandler;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.io.UnsupportedEncodingException;

public class Main {
   static OTPHandler a = new OTPHandler();
    public static void main(String[] args) {
        String recipientEmail = "dhruvharani8@gmail.com";
        String otp = a.generateOTP(6);

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

    }
}