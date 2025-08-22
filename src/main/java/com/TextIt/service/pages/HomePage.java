package com.TextIt.service.pages;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.session.SessionManger;
import java.io.File;
import java.util.InputMismatchException;
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
            System.out.println("│  " + YELLOW + "7. " + color("Setting", BLUE) + createSpacing("8. Setting", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "8. " + color("Chat", BLUE) + createSpacing("9. Chat", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "9. " + color("Help", BLUE) + createSpacing("10. Help", 78) + CYAN + BOLD + "│");
            System.out.println("│  " + YELLOW + "10. " + color("Exit", RED) + createSpacing("11. Exit", 78) + CYAN + BOLD + "│");
            System.out.println("└────────────────────────────────────────────────────────────────────────────────┘" + RESET);

            CommonMethods.printDivider();

            System.out.println(color("Enter your choice: ", GREEN));
            int choice;
            try {
                choice = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println(RED + "Invalid choice. Please try again." + RESET);
                continue;
            }finally {
                sc.nextLine();
            }

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
                    CommonMethods.openInNewCMD("com.TextIt.UI.SettingsPage " + userID);
                    break;
                case 8:
                    CommonMethods.openInNewCMD("com.TextIt.UI.ChatUI " + userID);
                    break;
                case 9:
                    help();
                    break;
                case 10:
                    System.out.println(RED + "Exiting TextIT..." + RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED + "Invalid choice. Try again." + RESET);
            }
        }
    }

    private static void help() {
        System.out.println(color("TextIT Help", GREEN));
        System.out.println(color("TextIT is a social network for sharing and discussing ideas, opinions, and creative solutions. ", BLUE));
        System.out.println(color("TextIT is designed to help people connect, collaborate, and share ideas.", BLUE));
        System.out.println(color("Here are some tips to get started:", BLUE));
        System.out.println(color("\n1. Profile (Option 1)", YELLOW));
        System.out.println(color("   - View and edit your profile information", BLUE));
        System.out.println(color("   - Check your followers and following", BLUE));
        System.out.println(color("\n2. Posts (Option 2)", YELLOW));
        System.out.println(color("   - Browse through posts from other users", BLUE));
        System.out.println(color("   - Like, comment, and share posts", BLUE));
        System.out.println(color("\n3. Search (Option 3)", YELLOW));
        System.out.println(color("   - Find other users and posts", BLUE));
        System.out.println(color("   - Search by username or keywords", BLUE));
        System.out.println(color("\n4. Inbox (Option 4)", YELLOW));
        System.out.println(color("   - View your messages and notifications", BLUE));
        System.out.println(color("\n5. Create Post (Option 5)", YELLOW));
        System.out.println(color("   - Share your thoughts and ideas", BLUE));
        System.out.println(color("   - Add text, links, and formatting", BLUE));
        System.out.println(color("\n6. Career (Option 6)", YELLOW));
        System.out.println(color("   - Explore job opportunities at TextIT", BLUE));
        System.out.println(color("\n7. Settings (Option 8)", YELLOW));
        System.out.println(color("   - Customize your account preferences", BLUE));
        System.out.println(color("   - Manage privacy settings", BLUE));
        System.out.println(color("\n8. Chat (Option 9)", YELLOW));
        System.out.println(color("   - Start real-time conversations", BLUE));
        System.out.println(color("   - Connect with other users instantly", BLUE));
        System.out.println(color("\n9. Help (Option 10)", YELLOW));
        System.out.println(color("   - Learn more about TextIT and its features", BLUE));
        System.out.println(color("\n10. Exit (Option 11)", YELLOW));
        System.out.println(color("   - Close TextIT and return to the previous screen", BLUE));
        System.out.println(color("\nFor more information, visit our website: https://www.textit.com", BLUE));
        System.out.println(color("contact us at <support.textit@gmail.com>", BLUE));
        System.out.println(color("for further assistance visit Settings > About & Legal", BLUE));

        CommonMethods.pressEnterToContinue();
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
