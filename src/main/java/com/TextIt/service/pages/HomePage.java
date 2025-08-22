package com.TextIt.service.pages;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.session.SessionManger;
import java.io.File;
import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;


public class HomePage {

    static Scanner sc = new Scanner(System.in);
    private static  int userID = SessionManger.getUserid();
    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdata =db.new UserData();


    public static void main(String[] args) {

        while (true) {
            if(args[0]!=null){
                userID= Integer.parseInt(args[0]);
            }
            System.out.println(CYAN + BOLD);
            System.out.println("=================================================================================");
            System.out.println("                             Welcome to TextIT                              ");
            System.out.println(color("                             Hello, " + userdata.getUserName(userID) + "!", YELLOW));
            System.out.println(color("               Select option to explore the world of TextIT               ", BRIGHT_PURPLE));
            System.out.println(CYAN+BOLD+"=================================================================================");
            System.out.print(RESET);

            CommonMethods.printDivider();

            System.out.println(CYAN + BOLD + "┌────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│" + color("                              MENU OPTIONS                                ", WHITE) + CYAN + BOLD + "      │");
            System.out.println("├────────────────────────────────────────────────────────────────────────────────┤");
            System.out.println("│  " + YELLOW + "1. " + color("Profile", BLUE) + createSpacing("1. Profile", 71) + CYAN + BOLD + "       │");
            System.out.println("│  " + YELLOW + "2. " + color("Post", BLUE) + createSpacing("2. Post", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "3. " + color("Search", BLUE) + createSpacing("3. Search", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "4. " + color("Inbox", BLUE) + createSpacing("4. Inbox", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "5. " + color("Create Post", BLUE) + createSpacing("5. Create Post", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "6. " + color("Career at TextIT", BLUE) + createSpacing("6. Career at TextIT",78 ) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "7. " + color("Logout", BLUE) + createSpacing("7. Logout", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "8. " + color("Setting", BLUE) + createSpacing("8. Setting", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "9. " + color("Chat", BLUE) + createSpacing("9. Chat", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "10. " + color("Help", BLUE) + createSpacing("10. Help", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "11. " + color("Exit", RED) + createSpacing("11. Exit", 78) + CYAN + BOLD + "│");
            System.out.println("└────────────────────────────────────────────────────────────────────────────────┘" + RESET);

            CommonMethods.printDivider();

            System.out.println(color("Enter your choice: ", GREEN));
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    CommonMethods.openInNewCMD("com.TextIt.UI.ProfilePage " , String.valueOf(userID));
                    break;
                case 2:
                    CommonMethods.openInNewCMD("com.TextIt.UI.FeedPage " , String.valueOf(userID) , "true");
                    break;
                case 3:
                    CommonMethods.openInNewCMD("com.TextIt.UI.SearchPage w" , String.valueOf(userID));
                    break;
                case 4:
                    CommonMethods.openInNewCMD("com.TextIt.UI.InboxPage " , String.valueOf(userID));
                    break;
                case 5:
                    CommonMethods.openInNewCMD("com.TextIt.UI.Post " + userID);
                    break;
                case 6:
                    CommonMethods.openInNewCMD("com.TextIt.UI.CareerPage " + userID);
                    break;
                case 7:
                    logout();
                    break;
                case 8:
                    CommonMethods.openInNewCMD("com.TextIt.UI.SettingsPage " + userID);
                    break;
                case 9:
                    CommonMethods.openInNewCMD("com.TextIt.UI.ChatUI " + userID);
                    break;
                case 10:
                    help();
                    break;
                case 11:
                    System.out.println(RED + "Exiting TextIT..." + RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED + "Invalid choice. Try again." + RESET);
            }
        }
    }

    private static void help() {
        System.out.println(color("TextIT is a social media platform for texting, sharing, and connecting with others.", GREEN));
        System.out.println(color("To use TextIT, follow these steps:", GREEN));
        System.out.println("1. Sign up for an account.");
        System.out.println("2. Connect with friends, family, and colleagues.");
        System.out.println("3. Send and receive messages.");
        System.out.println("4. Share your thoughts, experiences, and ideas.");
        System.out.println("5. Connect with other users based on their interests and skills.");
        CommonMethods.pressEnterToContinue();
    }


    private static void logout() {
        System.out.println(RED + "Logging out..." + RESET);
        File file = new File("last_session.txt");
        if(file.delete()){
            System.out.println(RED + "Last session has been deleted" + RESET);
        }else {
            System.out.println(RED + "Last session has not been deleted" + RESET);
        }
        System.exit(0);
    }


    private static String color(String text, String color) {
        return color + text + RESET;
    }

    private static String createSpacing(String text, int targetLength) {
        int spaces = targetLength - text.length();
        if (spaces <= 0) return " ";
        return " ".repeat(spaces);
    }


}
