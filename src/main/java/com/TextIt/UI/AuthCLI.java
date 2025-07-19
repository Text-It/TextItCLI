package com.TextIt.UI;

import com.TextIt.model.utils.CommonMethods;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;

public class AuthCLI {

    //Objects Of Different Classes
    private final Scanner scanner = new Scanner(System.in);


    public void showWelcomeScreen() throws SQLException {

        while (true) {
            System.out.println(CommonMethods.CYAN + CommonMethods.BOLD + """
                    ╔════════════════════════════════════════╗
                    ║           Welcome to TextIt            ║
                    ╚════════════════════════════════════════╝
                    """ + RESET);
            System.out.println(CommonMethods.YELLOW + "1. " + GREEN + "Sign Up");
            System.out.println(CommonMethods.YELLOW + "2. " + CommonMethods.BLUE + "LoginAuth");
            System.out.println(CommonMethods.YELLOW + "3. " + RED + "Exit");
            System.out.print("\n" + CommonMethods.PURPLE + "Enter your choice: " + RESET);


            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException _) {
            }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    CommonMethods.openInNewCMD("com.TextIt.UI.SignupPage");
                    break;
                case 2:
                    CommonMethods.openInNewCMD("com.TextIt.UI.LoginPage");
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
}