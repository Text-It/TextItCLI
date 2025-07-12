package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.security.OTPHandler;
import com.TextIt.service.pages.Login;
import com.TextIt.service.pages.SignUp;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AuthCLI {

    //Objects Of Different Classes
    private final Scanner scanner = new Scanner(System.in);
    private final SignUp newUser = new SignUp();
    private final Login oldUser = new Login();
    private final DataBase connectivity = new DataBase();

    // ANSI color codes
    private final String RESET = "\u001B[0m";
    private final String RED = "\u001B[31m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";
    private final String PURPLE = "\u001B[35m";
    private final String CYAN = "\u001B[36m";
    private final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        AuthCLI start = new AuthCLI();
        start.showWelcomeScreen();
    }


    private void showWelcomeScreen() {

        while (true) {
            System.out.println(CYAN + BOLD + """
                    ╔════════════════════════════════════════╗
                    ║           Welcome to TextIt            ║
                    ╚════════════════════════════════════════╝
                    """ + RESET);
            System.out.println(YELLOW + "1. " + GREEN + "Sign Up");
            System.out.println(YELLOW + "2. " + BLUE + "Login");
            System.out.println(YELLOW + "3. " + RED + "Exit");
            System.out.print("\n" + PURPLE + "Enter your choice: " + RESET);


            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException _) {
            }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showSignUpScreen();
                    break;
                case 2:
                    showLoginScreen();
                    break;
                case 3: {
                    System.out.println(RED + "\nThank you for using TextIt. Goodbye!" + RESET);
                    System.exit(0);
                }
                default: {
                    System.out.println(RED + "\nInvalid choice. Please try again." + RESET);
                    pressEnterToContinue();
                    showWelcomeScreen();
                }
            }
        }
    }

    private void showSignUpScreen() {
        System.out.println(GREEN + BOLD + """
                ╔════════════════════════════════════════╗
                ║               Sign Up                  ║
                ╚════════════════════════════════════════╝
                """ + RESET);

        String username, password, email, phoneNumber, generatedOtp , firstName, lastName;

        while (true) {
            if (!connectivity.isServerReachable()){
                pressEnterToContinue();
                return;
            }
            do {
                System.out.print(YELLOW + "Enter email: " + RESET);
                email = scanner.nextLine();
            } while (!newUser.verifyEmail(email));

            System.out.println("🔐 To proceed, we need to verify your email address.");
            System.out.println("📧 A one-time verification code will be sent to your email.");
            System.out.println("✅ You have 3 attempts to enter the correct OTP.");
            System.out.println("---------------------------------------------------\n");

            generatedOtp = OTPHandler.generateOTP(6);

            // Show progress feedback while sending OTP
            System.out.print("📤 Sending OTP");
            for (int dots = 0; dots < 3; dots++) {
                try {
                    Thread.sleep(800); // Simulate progress indicator (800ms delay for each dot)
                    System.out.print(".");
                } catch (InterruptedException ignored) {
                }
            }
            System.out.println(); // move to next line

            try {
                OTPHandler.sendOTP(email, generatedOtp);
                System.out.println("✅ OTP sent successfully to " + email);
                break;
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
            for (int i = 1; i <= 3; i++) {

                System.out.print("Enter OTP (" + i + "/3): ");
                String userInputOtp = scanner.nextLine();

                if (userInputOtp.equals(generatedOtp)) {
                    System.out.println("✅ Email verification successful.");
                    break;
                } else {
                    System.out.println("❌ Incorrect OTP. Please try again.");
                    if (i==3){
                        return;
                    }
                    if (i < 3) {
                        System.out.println("Remaining attempts: " + (3 - i));
                    }
                }
            }


        do {
            System.out.print(YELLOW + "Enter username: " + RESET);
            username = scanner.nextLine();
        } while (!newUser.verifyUsername(username));

        do {
            System.out.print(YELLOW + "Enter password: " + RESET);
            password = scanner.nextLine();
        } while (!newUser.verifyPassword(password));


        do {
            System.out.print(YELLOW + "Enter phone number: " + RESET);
            phoneNumber = scanner.nextLine();
        } while (!newUser.verifyPhoneNumber(phoneNumber));

        do {
            System.out.print(YELLOW + "Enter First Name: " + RESET);
            firstName = scanner.nextLine();
        } while (!newUser.verifyRealName(firstName));

        do {
            System.out.print(YELLOW + "Enter Last Name: " + RESET);
            lastName = scanner.nextLine();
        } while (!newUser.verifyRealName(lastName));

        do {

            System.out.println("\n📄 Terms & Conditions");
            System.out.println("--------------------------------------------------");
            System.out.println("By using TextIT, you agree to the following:");
            System.out.println("1. You are responsible for the content you share.");
            System.out.println("2. Misuse or abuse of the platform is strictly prohibited.");
            System.out.println("3. You will not share offensive, hateful, or illegal content.");
            System.out.println("4. Your data may be stored securely for service enhancement.");
            System.out.println("5. OTP verification ensures account authenticity and security.");
            System.out.println("6. You consent to receive transactional emails related to security.");
            System.out.println("7. TextIT is a student project and does not guarantee data backups.");
            System.out.println("8. You agree not to reverse-engineer or distribute the source code.");
            System.out.println("9. Violations may result in temporary or permanent account suspension.");
            System.out.println("10. All rights reserved by TextIT Corporation © 2025");
            System.out.println("--------------------------------------------------");

            System.out.print("🔒 Do you accept the Terms & Conditions? (Y/N): ");
            char agreement = scanner.nextLine().toLowerCase().charAt(0);

            if (!(agreement == 'y')) {
                System.out.println("❌ Registration aborted. You must accept the Terms & Conditions to proceed.");
                pressEnterToContinue();
            } else {
                System.out.println("✅ Terms accepted. Proceeding with account creation...");
                break;
            }

        } while (true);
        {
            DataBase db = new DataBase();
            DataBase.Profile profile = db.new Profile();
            if (profile.registerUser(firstName,lastName,username,password,phoneNumber,email))
                System.out.println(GREEN + BOLD + "\nSign up successful!" + RESET);
            else {
                System.out.println(RED + BOLD + "\nSign up failed. Please try again." + RESET);
                System.out.println("If you have already registered, please login.");
            }
            pressEnterToContinue();
            showWelcomeScreen();
        }
    }

    private void showLoginScreen() {
        System.out.println(BLUE + BOLD + """
                ╔════════════════════════════════════════╗
                ║                Login                   ║
                ╚════════════════════════════════════════╝
                """ + RESET);

        System.out.print(YELLOW + "Enter username/email/phone: " + RESET);
        String userInput = scanner.nextLine();

        System.out.print(YELLOW + "Enter password: " + RESET);
        String password = scanner.nextLine();

        try {
            if (oldUser.verifyUserDetail(userInput) && oldUser.verifyPassword(password)) {
                System.out.println(GREEN + BOLD + "\n Login successful!" + RESET);
            } else {
                System.out.println(RED + BOLD + "\n Login failed. Please check your credentials." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + BOLD + "\n An error occurred: " + e.getMessage() + RESET);
        }
        pressEnterToContinue();
        showWelcomeScreen();
    }


    private void pressEnterToContinue() {
        System.out.println(PURPLE + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }
}

