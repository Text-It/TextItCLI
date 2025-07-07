package com.TextIt.security;

import com.TextIt.database.DataBase;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;

/**
 * The {@code OTPHandler} class handles the generation and delivery of One-Time Passwords (OTPs)
 * to user email addresses using Gmail's SMTP service. It is typically used for account
 * verification during signup or login processes in the TextIT application.
 *
 * <p>Key responsibilities include:</p>
 * <ul>
 *   <li>Generating secure numeric OTPs</li>
 *   <li>Composing email messages with branding</li>
 *   <li>Sending OTPs via Gmail SMTP with proper error handling</li>
 * </ul>
 */
public class OTPHandler {

    // Sender credentials (replace with your Gmail app password)
    private static final String SENDER_EMAIL = "noreply.textit@gmail.com";
    private static final String SENDER_PASSWORD = "oocl xmrx huva cpbc";
    static DataBase db = new DataBase();

    /**
     * Sample usage for testing OTP generation and sending.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        String recipientEmail = "dhruvharani8@gmail.com";
        String otp = generateOTP(6);

        try {
            sendOTP(recipientEmail, otp);
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

    /**
     * Generates a secure numeric One-Time Password (OTP).
     *
     * @param otpLength the number of digits in the OTP (commonly 6)
     * @return a randomly generated numeric OTP as a string
     */
    public static String generateOTP(int otpLength) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    /**
     * Sends a generated OTP to a specified email address using Gmail SMTP.
     *
     * @param email the recipient's email address
     * @param otp   the OTP string to be sent
     * @throws MessagingException            if the email fails to send
     * @throws UnsupportedEncodingException  if the sender name uses unsupported encoding
     */
    public static void sendOTP(String email, String otp) throws MessagingException, UnsupportedEncodingException {
        // Setup Gmail SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        // Compose the email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_EMAIL, "TextIT Corporation"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Your TextIT Verification Code");
        message.setText(emailBody(otp));

        // Send the email
        Transport.send(message);
    }

    /**
     * Builds the email body containing the OTP in a user-friendly format.
     *
     * @param otp the One-Time Password to be sent
     * @return the formatted email message body as a string
     */
    private static String emailBody(String otp) {
        return """
                Hello,

                Your One-Time Password (OTP) for verifying your account on TextIT is:

                👉 OTP: %s

                This code is valid for the next 10 minutes. Please do not share it with anyone.

                If you did not request this code, please ignore this email.

                Thanks,
                Team TextIT
                TextIT Corporation | Secure & Simple Text Networking
                """.formatted(otp);
    }
}
