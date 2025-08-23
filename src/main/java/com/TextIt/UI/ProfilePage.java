package com.TextIt.UI;

import com.TextIt.model.utils.CommonMethods;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;


public class ProfilePage {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int userid = Integer.parseInt(args[0]);

        CommonMethods.userProfile(userid);

        int borderLength = 60;
        System.out.println(BRIGHT_CYAN + BOLD + "-".repeat(borderLength) + RESET);
        System.out.println(color("OPTIONS", PURPLE));
        System.out.println(BRIGHT_CYAN + BOLD + "-".repeat(borderLength) + RESET);
        System.out.println(YELLOW + "1." + RESET + " " + BLUE + "View Posts" + RESET + "    " + YELLOW + "2." + RESET + " " + BLUE + "Edit Profile" + RESET + "    " + YELLOW + "3." + RESET + " " + BLUE + "Settings" + RESET);
        System.out.println(YELLOW + "4." + RESET + " " + RED + "Exit" + RESET);
        System.out.println(BRIGHT_CYAN + BOLD + "-".repeat(borderLength) + RESET);
        while (true) {
            System.out.print(GREEN + "Enter your choice: " + RESET);
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    CommonMethods.openInNewCMD("com.TextIt.UI.FeedPage " + userid + " " + "false");
                    break;
                case "2":
                    CommonMethods.editProfile(userid);
                    return;
                case "3":
                    CommonMethods.openInNewCMD("com.TextIt.UI.SettingsPage " + userid);
                    break;
                case "4":
                    return;
                default:
                    System.out.println(RED + "Invalid choice. Please try again." + RESET);
            }
        }
    }
}
