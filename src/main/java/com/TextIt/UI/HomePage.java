package com.TextIt.UI;

import com.TextIt.model.utils.CommonMethods;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;


public class HomePage {

    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        //String username = args[0];
        String username = "TextIt";
        while (true) {
            System.out.println(CYAN + BOLD);
            System.out.println("==============================================");
            System.out.println("             Welcome to TextIT                ");
            System.out.println("==============================================");
            System.out.print(RESET);
            System.out.println(color("Hello, " + username + "!", YELLOW));
            System.out.println(color("Select option to explore the world of TextIT", GREEN));
            System.out.println(YELLOW + "1. " + color("Profile", BLUE));
            System.out.println(YELLOW + "2. " + color("Reel", BLUE));
            System.out.println(YELLOW + "3. " + color("Search", BLUE));
            System.out.println(YELLOW + "4. " + color("Inbox", BLUE));
            System.out.println(YELLOW + "5. " + color("Refresh Feed", BLUE));
            System.out.println(YELLOW + "6. " + color("Career at TextIT", BLUE));
            System.out.println(YELLOW + "7. " + color("Logout", BLUE));
            System.out.println(YELLOW + "8. " + color("Setting", BLUE));
            System.out.println(YELLOW + "9. " + color("Help", BLUE));
            System.out.println(YELLOW + "10. " + color("Exit", RED));

            System.out.println(color("Enter your choice: ", GREEN));
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    CommonMethods.openInNewCMD("com.TextIt.UI.ProfilePage " + username);
                    break;
                case 2:
                    reel();
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    inbox();
                    break;
                case 5:
                    refreshFeed();
                    break;
                case 6:
                    career();
                    break;
                case 7:
                    logout();
                    break;
                case 8:
                    settings();
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
        System.out.println(color("TextIT is a social media platform for texting, sharing, and connecting with others.", GREEN));
        System.out.println(color("To use TextIT, follow these steps:", GREEN));
        System.out.println("1. Sign up for an account.");
        System.out.println("2. Connect with friends, family, and colleagues.");
        System.out.println("3. Send and receive messages.");
        System.out.println("4. Share your thoughts, experiences, and ideas.");
        System.out.println("5. Connect with other users based on their interests and skills.");
        CommonMethods.pressEnterToContinue();
    }

    private static void settings() {
    }

    private static void logout() {
        System.out.println(RED + "Logging out..." + RESET);
        System.exit(0);
    }

    private static void career() {
        System.out.println(color("Career Opportunities at TextIT", PURPLE));
        System.out.println("1. Software Developer");
        System.out.println("2. UI/UX Designer");
        System.out.println("3. Product Manager");
        CommonMethods.pressEnterToContinue();
    }

    private static void refreshFeed() {
        System.out.println(CYAN + BOLD);
        System.out.println("Refreshing feed..........");
        System.out.print(RESET);
        System.out.println(color("Feed has been refreshed successfully.", GREEN));
    }

    private static void inbox() {
    }

    private static void search() {
        System.out.println(color("Enter person name to search: ", GREEN));
        String query = sc.nextLine();
        System.out.println(YELLOW + "Search results for " + query + " :");
        goToNext();
    }

    private static void reel() {

    }

    private static String color(String text, String color) {
        return color + text + RESET;
    }


    private static void goToNext() {
        System.out.println(CYAN + " Going to the next screen..." + RESET);
        CommonMethods.pressEnterToContinue();
    }

    private static void goToPrevious() {
        System.out.println(CYAN + "Going back to the previous screen..." + RESET);
        CommonMethods.pressEnterToContinue();
    }

}