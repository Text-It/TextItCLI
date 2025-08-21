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
        System.out.println("[1] View Posts  [2] Edit Profile  [3] My Circles");
        System.out.println("[4] Settings    [5] Privacy Mode  [6] Exit      ");
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
                    my_circles();
                    break;
                case "4":
                    settings();
                    break;
                case "5":
                    privacy_mode();
                    break;
                case "6":
                    return;
                case "mood":
//                    update_mood();
                    break;
                default:
                    System.out.println(RED + "Invalid choice. Please try again." + RESET);
            }
        }
    }

//    private static void update_mood() {
//        System.out.println("Enter your mood..." + GREEN);
//        String nmood = sc.nextLine();
//        System.out.println(CYAN + "Mood updated to :" + nmood + RESET);
//        CommonMethods.pressEnterToContinue();
//    }

    private static void privacy_mode() {
        System.out.println(CYAN + "Viewing posts....." + RESET);
        CommonMethods.pressEnterToContinue();
    }

    private static void my_circles() {
    }



    private static void settings() {
    }

}
