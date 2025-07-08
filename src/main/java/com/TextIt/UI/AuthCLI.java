package com.TextIt.UI;

import com.TextIt.service.pages.Login;
import com.TextIt.service.pages.SignUp;
import java.util.Scanner;

public class AuthCLI {

    private static final Scanner sc = new Scanner(System.in);
    private static final SignUp signUp = new SignUp();
    private static final Login login = new Login();


    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";



    private static void showWelcomeScreen() {
        while (true) {
            System.out.println(CYAN + BOLD);
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║           Welcome to TextIt            ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print(RESET);

            System.out.println(YELLOW + "1. " + GREEN + "Sign Up");
            System.out.println(YELLOW + "2. " + BLUE + "Login");
            System.out.println(YELLOW + "3. " + RED + "Exit" + RESET);
            System.out.print(PURPLE + "\nEnter your choice: " + RESET);

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Please enter a valid number!" + RESET);
                continue;
            }

            switch (choice) {
                case 1:
                    showSignUpScreen();
                    break;
                case 2:
                    showLoginScreen();
                    break;
                case 3:
                    System.out.println(RED + "\nThank you for using TextIt. Goodbye!" + RESET);
                    return;
                default:
                    System.out.println(RED + "\nInvalid choice. Please try again." + RESET);
            }
        }
    }

    private static void showSignUpScreen() {
        System.out.println(GREEN + BOLD);
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║               Sign Up                  ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print(RESET);

        String username;
        String password;
        String email;
        String phone;

        do {
            System.out.print(YELLOW + "Enter username: " + RESET);
            username = sc.nextLine();
        } while (!signUp.verifyUsername(username));

        do {
            System.out.print(YELLOW + "Enter password: " + RESET);
            password = sc.nextLine();
        } while (!signUp.verifyPassword(password));

        do {
            System.out.print(YELLOW + "Enter email: " + RESET);
            email = sc.nextLine();
        } while (!signUp.verifyEmail(email));

        do {
            System.out.print(YELLOW + "Enter phone number: " + RESET);
            phone = sc.nextLine();
        } while (!signUp.verifyPhoneNumber(phone));

        System.out.println(GREEN + BOLD + "\n✅ Sign up successful!" + RESET);
        Enter();
    }

    private static void showLoginScreen() {
        System.out.println(BLUE + BOLD);
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║                Login                   ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print(RESET);

        System.out.print(YELLOW + "Enter username/email/phone: " + RESET);
        String input = sc.nextLine();

        System.out.print(YELLOW + "Enter password: " + RESET);
        String password = sc.nextLine();

        try {
            if (login.verifyUserDetail(input) && login.verifyPassword(password)) {
                System.out.println(GREEN + BOLD + "\n✅ Login successful!" + RESET);
                HomePage.showHomePage(input);
                return;
            } else {
                System.out.println(RED + BOLD + "\n❌ Login failed. Please check your credentials." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + BOLD + "\n❌ Something went wrong: " + e.getMessage() + RESET);
        }

        Enter();
    }

    private static void Enter() {
        System.out.print(PURPLE + "\n(Press Enter to continue)" + RESET);
        sc.nextLine();
    }
}
