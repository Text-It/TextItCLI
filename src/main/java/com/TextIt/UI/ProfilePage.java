package com.TextIt.UI;

import com.TextIt.model.utils.CommonMethods;
import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;


public class ProfilePage {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
       int userid = Integer.parseInt(args[0]);

        CommonMethods.userProfile(userid);

        int borderLength = 50;
        System.out.println(color("OPTIONS", PURPLE));
        System.out.println("-".repeat(borderLength));
        System.out.println("[1] View Posts  [2] Edit Profile  [3] Settings");
        System.out.println("[4] Exit          ");
        System.out.println("-".repeat(borderLength));
        while (true) {
            System.out.println("Enter your choices :");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    FeedPage fp = new FeedPage();
                    fp.onlyUserPosts(userid);
                    break;
                case "2":
                    CommonMethods.editProfile(userid);
                    break;
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
