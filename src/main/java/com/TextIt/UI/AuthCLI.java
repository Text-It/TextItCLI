package com.TextIt.UI;

import com.TextIt.service.pages.Login;
import com.TextIt.service.pages.SignUp;
import java.util.Scanner;

public class AuthCLI {
    private  final Scanner scanner = new Scanner(System.in);
    private  final SignUp signUp = new SignUp();
    private  final Login login = new Login();

    // ANSI color codes
    private  final String RESET = "\u001B[0m";
    private  final String RED = "\u001B[31m";
    private  final String GREEN = "\u001B[32m";
    private  final String YELLOW = "\u001B[33m";
    private  final String BLUE = "\u001B[34m";
    private  final String PURPLE = "\u001B[35m";
    private  final String CYAN = "\u001B[36m";
    private  final String BOLD = "\u001B[1m";



    public   void showWelcomeScreen() {
        System.out.println(CYAN + BOLD + """
            ╔════════════════════════════════════════╗
            ║           Welcome to TextIt            ║
            ╚════════════════════════════════════════╝
            """ + RESET);
        System.out.println(YELLOW + "1. " + GREEN + "Sign Up");
        System.out.println(YELLOW + "2. " + BLUE + "Login");
        System.out.println(YELLOW + "3. " + RED + "Exit");
        System.out.print("\n" + PURPLE + "Enter your choice: " + RESET);


        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 : showSignUpScreen();
            case 2 : showLoginScreen();
            case 3 : {
                System.out.println(RED + "\nThank you for using TextIt. Goodbye!" + RESET);
                System.exit(0);
            }
            default : {
                System.out.println(RED + "\nInvalid choice. Please try again." + RESET);
                pressEnterToContinue();
                showWelcomeScreen();
            }
        }
    }

    public   void showSignUpScreen() {
        System.out.println(GREEN + BOLD + """
            ╔════════════════════════════════════════╗
            ║               Sign Up                  ║
            ╚════════════════════════════════════════╝
            """ + RESET);

        String username, password, email, phoneNumber;

        do {
            System.out.print(YELLOW + "Enter username: " + RESET);
            username = scanner.nextLine();
        } while (!signUp.verifyUsername(username));

        do {
            System.out.print(YELLOW + "Enter password: " + RESET);
            password = scanner.nextLine();
        } while (!signUp.verifyPassword(password));

        do {
            System.out.print(YELLOW + "Enter email: " + RESET);
            email = scanner.nextLine();
        } while (!signUp.verifyEmail(email));

        do {
            System.out.print(YELLOW + "Enter phone number: " + RESET);
            phoneNumber = scanner.nextLine();
        } while (!signUp.verifyPhoneNumber(phoneNumber));

        System.out.println(GREEN + BOLD + "\n✅ Sign up successful!" + RESET);
        pressEnterToContinue();
        showWelcomeScreen();
    }

    public   void showLoginScreen() {
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
            if (login.verifyUserDetail(userInput) && login.verifyPassword(password)) {
                System.out.println(GREEN + BOLD + "\n✅ Login successful!" + RESET);
            } else {
                System.out.println(RED + BOLD + "\n❌ Login failed. Please check your credentials." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + BOLD + "\n❌ An error occurred: " + e.getMessage() + RESET);
        }

        pressEnterToContinue();
        showWelcomeScreen();
    }


    public   void pressEnterToContinue() {
        System.out.println(PURPLE + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }
}

