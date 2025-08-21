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
            if(sessionManger.autoLogin()){
                // homePage
                String username = userDb.getUserName(SessionManger.getUserid());
                NotificationListener listener =  new NotificationListener( username);
                Thread t = new Thread(listener);
                t.setDaemon(true);
                t.start();
                HomePage.main(new String[]{String.valueOf(SessionManger.getUserid())});
                break;
            }
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
                    openInNewCMD("com.TextIt.UI.SignupPage");
                    break;
                case 2:
                    openInNewCMD("com.TextIt.UI.LoginPage");
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