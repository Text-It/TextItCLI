package com.TextIt.UI;


import com.TextIt.database.DataBase;
import com.TextIt.inbox.NotificationListener;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.pages.HomePage;
import com.TextIt.service.session.SessionManger;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;

public class AuthCLI {

    //Objects Of Different Classes
    private final Scanner scanner = new Scanner(System.in);
    private final SessionManger sessionManger = new SessionManger();
    private final DataBase dataBase = new DataBase();
    private final DataBase.UserData userDb = dataBase.new UserData();

    public void showWelcomeScreen() throws Exception {
        while (true) {
            if (sessionManger.autoLogin()) {
                // homePage
                String username = userDb.getUserName(SessionManger.getUserid());
                NotificationListener listener = new NotificationListener(username);
                Thread t = new Thread(listener);
                t.setDaemon(true);
                t.start();
                HomePage.main(new String[]{String.valueOf(SessionManger.getUserid())});
                break;
            }
            clearConsole();

            displayWelcome();

            displayMainMenu();

            // Get user choice
            int choice = getUserChoice();

            // Handle user choice
            handleUserChoice(choice);
        }
    }

    private void displayWelcome() {
        System.out.println(BRIGHT_CYAN + BOLD + """
                +==============================================================================+
                |                                                                              |
                |     ████████╗███████╗██╗  ██╗ ████████╗ ██╗  ████████╗                       |
                |     ╚══██╔══╝██╔════╝╚██╗██╔╝ ╚══██╔══╝ ██║  ╚══██╔══╝                       |
                |        ██║   █████╗   ╚███╔╝     ██║    ██║     ██║                          |
                |        ██║   ██╔══╝   ██╔██╗     ██║    ██║     ██║                          |
                |        ██║   ███████╗██╔╝ ██╗    ██║    ██║     ██║                          |
                |        ╚═╝   ╚══════╝╚═╝  ╚═╝    ╚═╝    ╚═╝     ╚═╝                          |
                |                                                                              |
                |                       Welcome to TEXTIT - Chat Smarter                       |
                |                                                                              |
                +==============================================================================+
                """ + RESET);


        System.out.println(BRIGHT_WHITE + BOLD + "\n" + " ".repeat(20) + "Connect   Share   Inspire" + RESET);
        System.out.println(BRIGHT_YELLOW + " ".repeat(18) + "Your Digital Story Starts Here" + RESET);

        CommonMethods.printDivider();
    }

    private void displayMainMenu() {
        System.out.println(BRIGHT_BLUE + BOLD + "\n  Main Menu - Choose Your Path:" + RESET);
        System.out.println();

        CommonMethods.printChoice(1, " Create New Account", BRIGHT_GREEN);
        CommonMethods.printChoice(2, " Login to Your Account", BRIGHT_BLUE);
        CommonMethods.printChoice(3, " Exit Application", BRIGHT_RED);

        CommonMethods.printDivider();
    }

    private int getUserChoice() {
        System.out.print(BRIGHT_PURPLE + BOLD + "\n Enter your choice (1-3): " + RESET);

        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException _) {
            // Handle invalid input
        }
        scanner.nextLine(); // consume newline

        return choice;
    }

    private void handleUserChoice(int choice) throws Exception {
        switch (choice) {
            case 1:
                openInNewCMD("com.TextIt.UI.SignupPage");
                break;
            case 2:
                openInNewCMD("com.TextIt.UI.LoginPage");
                break;
            case 3:
                displayExitMessage();
                System.exit(0);
                break;
            default:
                System.out.println(RED + "\nInvalid choice. Please try again." + RESET);
                pressEnterToContinue();
                showWelcomeScreen();
        }
    }

    private void displayExitMessage() {
        clearConsole();
        System.out.println(BRIGHT_CYAN + BOLD + """
                +==============================================================================+ 
                |                                                                              |
                |                         Thank You for Using TextIt!                          |                                                                                |
                |                                                                              |                                                                                |
                |                    We hope you enjoyed your experience!                      |
                |                                                                              |
                |                    Come back soon for more connections!                      |
                |                                                                              |
                +==============================================================================+
                """ + RESET);

        System.out.println(BRIGHT_GREEN + "\n" + " ".repeat(30) + "  Have a great day!  " + RESET);

    }
}
