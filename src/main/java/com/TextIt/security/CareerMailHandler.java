package com.TextIt.security;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class CareerMailHandler {

    private static final String SENDER_EMAIL = "noreply.textit@gmail.com";
    private static final String SENDER_PASSWORD = "oocl xmrx huva cpbc"; // Gmail App Password
    private static final String CAREER_EMAIL = "careers.textit@gmail.com";

    public static void sendApplication(String applicantEmail, String applicantName, String role, File resumeFile) throws MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        // ----- HR Email -----
        Message hrMessage = new MimeMessage(session);
        hrMessage.setFrom(new InternetAddress(SENDER_EMAIL, "TextIT Careers"));
        hrMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(CAREER_EMAIL));
        hrMessage.setSubject("Job Application - " + role);

        String hrBody = "<!DOCTYPE html>" + "<html>" + "<head>" + "<style>" + "  body { font-family: Arial, sans-serif; background-color: #f4f4f7; color: #333; margin: 0; padding: 0; }" + "  .container { background-color: #ffffff; max-width: 600px; margin: 20px auto; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" + "  h2 { color: #4CAF50; }" + "  p { line-height: 1.6; }" + "  .info { background-color: #f9f9f9; padding: 10px; border-radius: 5px; margin-bottom: 15px; }" + "</style>" + "</head>" + "<body>" + "<div class='container'>" + "<h2>🚀 New Job Application Received!</h2>" + "<p>Dear HR Team,</p>" + "<p>A new applicant has submitted their application:</p>" + "<div class='info'>" + "<p><b>Name:</b> " + applicantName + "</p>" + "<p><b>Email:</b> " + applicantEmail + "</p>" + "<p><b>Role Applied:</b> " + role + "</p>" + "</div>" + "<p>Please find the resume attached for review.</p>" + "<p>Cheers,<br>TextIT Careers Team</p>" + "</div>" + "</body>" + "</html>";

        MimeBodyPart hrBodyPart = new MimeBodyPart();
        hrBodyPart.setContent(hrBody, "text/html; charset=utf-8");

        Multipart hrMultipart = new MimeMultipart();
        hrMultipart.addBodyPart(hrBodyPart);

        if (resumeFile != null && resumeFile.exists()) {
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(resumeFile);
            hrMultipart.addBodyPart(attachment);
        }

        hrMessage.setContent(hrMultipart);
        Transport.send(hrMessage);
        System.out.println("Application email sent to HR.");

        // ----- Confirmation Email to Applicant -----
        Message applicantMessage = new MimeMessage(session);
        applicantMessage.setFrom(new InternetAddress(SENDER_EMAIL, "TextIT Careers"));
        applicantMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(applicantEmail));
        applicantMessage.setSubject("Your Application at TextIT");

        String applicantBody = "<!DOCTYPE html>" + "<html>" + "<head>" + "<style>" + "  body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #eef2f7; color: #333; margin: 0; padding: 0; }" + "  .container { background-color: #ffffff; max-width: 600px; margin: 20px auto; padding: 25px; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }" + "  h2 { color: #007BFF; }" + "  p { line-height: 1.6; }" + "  .highlight { background-color: #f1f8ff; padding: 8px; border-radius: 5px; display: inline-block; margin-bottom: 15px; }" + "</style>" + "</head>" + "<body>" + "<div class='container'>" + "<h2>🎉 Application Received!</h2>" + "<p>Hi " + applicantName + ",</p>" + "<p>Thank you for applying for the role of <span class='highlight'>" + role + "</span> at TextIT.</p>" + "<p>Our HR team is reviewing your application and will contact you shortly regarding the next steps.</p>" + "<p>We appreciate your interest in joining our team! 🌟</p>" + "<p>Best regards,<br>TextIT Careers Team</p>" + "</div>" + "</body>" + "</html>";

        MimeBodyPart applicantBodyPart = new MimeBodyPart();
        applicantBodyPart.setContent(applicantBody, "text/html; charset=utf-8");

        Multipart applicantMultipart = new MimeMultipart();
        applicantMultipart.addBodyPart(applicantBodyPart);

        applicantMessage.setContent(applicantMultipart);
        Transport.send(applicantMessage);
        System.out.println("Confirmation email sent to applicant.");
    }
}
