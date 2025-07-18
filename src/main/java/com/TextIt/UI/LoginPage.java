package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.service.pages.LoginAuth;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;
import static com.TextIt.model.utils.CommonMethods.BOLD;
import static com.TextIt.model.utils.CommonMethods.GREEN;
import static com.TextIt.model.utils.CommonMethods.RED;
import static com.TextIt.model.utils.CommonMethods.RESET;
import static com.TextIt.model.utils.CommonMethods.YELLOW;
import static com.TextIt.model.utils.CommonMethods.pressEnterToContinue;

public class LoginPage {

    private static final LoginAuth oldUser = new LoginAuth();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DataBase connectivity = new DataBase();


    public static void main(String[] args) {
        System.out.println(BLUE + BOLD + """
                ╔════════════════════════════════════════╗
                ║                LoginAuth               ║
                ╚════════════════════════════════════════╝
                """ + RESET);

        if (!connectivity.isServerReachable()) {        //check if server is reachable
            pressEnterToContinue();
            return;
        }

        System.out.print(YELLOW + "Enter username/email/phone: " + RESET);
        String userInput = scanner.nextLine();

        System.out.print(YELLOW + "Enter password (or type 'forgot' to reset): " + RESET);
        String password = scanner.nextLine();

        if (password.equalsIgnoreCase("forgot")) {
            oldUser.handleForgotPassword(scanner);
        } else {
            try {
                if (oldUser.verifyUserDetail(userInput) && oldUser.verifyPassword(password)) {
                    System.out.println(GREEN + BOLD + "\n LoginAuth successful!" + RESET);
                } else {
                    System.out.println(RED + BOLD + "\n LoginAuth failed. Please check your credentials." + RESET);
                }
            } catch (Exception e) {
                System.out.println(RED + BOLD + "\n An error occurred: " + e.getMessage() + RESET);
            }
            pressEnterToContinue();
        }
    }
}