package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.service.pages.LoginAuth;
import com.TextIt.service.session.SessionManger;
import java.util.Scanner;
import static com.TextIt.model.utils.CommonMethods.*;


public class LoginPage {

    private static final LoginAuth oldUser = new LoginAuth();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DataBase connectivity = new DataBase();
    private static final SessionManger sessionManger = new SessionManger();


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
                    sessionManger.manualLogin(connectivity.featchId(userInput.trim()));
                    System.out.println(GREEN + BOLD + "\n LoginAuth successful!" + RESET);
                    openInNewCMD("com.TextIt.service.pages.HomePage " + connectivity.featchId(userInput.trim()));
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