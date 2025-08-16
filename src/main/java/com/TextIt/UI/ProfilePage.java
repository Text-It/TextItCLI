package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import java.util.Scanner;
import static com.TextIt.model.utils.CommonMethods.*;


public class ProfilePage {

    static Scanner sc = new Scanner(System.in);
    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdata =db.new UserData();
    private static final DataBase.Post userpost =db.new Post();
    private static final DataBase.UserFollows userfollows =db.new UserFollows();

    public static void main(String[] args) {
        int userid = Integer.parseInt(args[0]);

        int borderLength = 50;
        String headerTopBottomBorder = "=";
        String bodyLeftRightBorder = "||";
        String header = userdata.getUserName(userid) + "'s Profile";
        int bodyContentLength = borderLength - bodyLeftRightBorder.length() * 2;

        System.out.println(CYAN + BOLD);
        System.out.println(headerTopBottomBorder.repeat(borderLength));
        System.out.println(" ".repeat(borderLength / 2 - header.length() / 2) + header + "".repeat(borderLength / 2 - header.length() / 2));
        System.out.println(headerTopBottomBorder.repeat(borderLength));
        System.out.println(RESET);
        System.out.println();

        System.out.println(bodyLeftRightBorder + userdata.getRealName(userid) + " ".repeat(bodyContentLength - userdata.getRealName(userid).length() - userdata.getUserName(userid).length()) + userdata.getUserName(userid) + bodyLeftRightBorder);
        System.out.println(bodyLeftRightBorder + userdata.getGender(userid) + " ".repeat(bodyContentLength- userdata.getGender(userid).length() - userdata.getLocation(userid).length())+ bodyLeftRightBorder);
        System.out.println(bodyLeftRightBorder + "BIO -->" + " ".repeat(bodyContentLength - 7) + bodyLeftRightBorder);
        CommonMethods.paragraphDisplay(userdata.getBio(userid),bodyLeftRightBorder,borderLength);
        System.out.println(bodyLeftRightBorder + "Member Since: " + userdata.getMemberSince(userid) + " ".repeat(bodyContentLength-userdata.getMemberSince(userid)-14) + bodyLeftRightBorder);
        System.out.println(bodyLeftRightBorder + "Posts: " + userpost.getPostCount(userid) + " ".repeat((bodyContentLength-userpost.getPostCount(userid)-userfollows.getFollowingCount(userid)-userfollows.getFollowersCount(userid)-29)/3) + "Following: " + userfollows.getFollowingCount(userid) + " ".repeat((bodyContentLength-userpost.getPostCount(userid)-userfollows.getFollowingCount(userid)-userfollows.getFollowersCount(userid)-29)/3)  + "Followers: " + userfollows.getFollowersCount(userid) + " ".repeat((bodyContentLength-userpost.getPostCount(userid)-userfollows.getFollowingCount(userid)-userfollows.getFollowersCount(userid)-29)/3)  + bodyLeftRightBorder );
        int repeatCount = Math.max(0,
                (bodyContentLength - userdata.getXP(userid) - userdata.getLevel(userid) - 11) / 3
        );

        System.out.println(
                bodyLeftRightBorder +
                        "XP: " + userdata.getXP(userid) +
                        " ".repeat(repeatCount) +
                        "Level: " + userdata.getLevel(userid) +
                        " ".repeat(repeatCount) +
                        bodyLeftRightBorder
        );
        System.out.println(bodyLeftRightBorder + "Share: " + userdata.getUserShareCode(userid) + " ".repeat(bodyContentLength-userdata.getUserShareCode(userid).length()-6) + bodyLeftRightBorder);

        System.out.println();

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
                    view_posts();
                    break;
                case "2":
                    edit_profile();
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
                    update_mood();
                    break;
                default:
                    System.out.println(RED + "Invalid choice. Please try again." + RESET);
            }
        }
    }

    private static void view_posts() {
        System.out.println(CYAN + "Viewing posts....." + RESET);
        CommonMethods.pressEnterToContinue();
    }

    private static void update_mood() {
        System.out.println("Enter your mood..." + GREEN);
        String nmood = sc.nextLine();
        System.out.println(CYAN + "Mood updated to :" + nmood + RESET);
        CommonMethods.pressEnterToContinue();
    }

    private static void privacy_mode() {
        System.out.println(CYAN + "Viewing posts....." + RESET);
        CommonMethods.pressEnterToContinue();
    }

    private static void my_circles() {
    }

    private static void edit_profile() {
    }

    private static void settings() {
    }

}
