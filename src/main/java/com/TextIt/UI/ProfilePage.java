package com.TextIt.UI;

import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.user.UserData;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;


public class ProfilePage {


    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        dynamicDesign();
        //String username = String.valueOf(args[0]);
        String username = "TextIt";
        System.out.println(CYAN + BOLD);
        System.out.println("===============================================");
        System.out.println("                  YOUR PROFILE                 ");
        System.out.println("===============================================");
        System.out.println(RESET);
        System.out.println();
        String real_name = "";
        String gender = "";
        String location = "";
        String bio = "";
        int member_since = 0;
        int posts = 0;
        int following = 0;
        int followers = 0;
        int xp = 0;
        String level = "";
        String mood = "";
        System.out.println(color("real_name", YELLOW) + "               " + color(real_name, BLUE));
        System.out.println(color("gender", YELLOW) + "     " + color(gender, BLUE) + "     " + color("location", YELLOW) + "  " + color(location, BLUE));
        System.out.println(color("BIO ->", YELLOW) + "  " + color(bio, BLUE));
        System.out.println();
        System.out.println(color("Member since:", YELLOW) + " " + color(String.valueOf(member_since), BLUE));
        System.out.println();
        System.out.println(color("Posts:", YELLOW) + " " + color(String.valueOf(posts), BLUE) + "     " + color("following:", YELLOW) + " " + color(String.valueOf(following), BLUE) + "     " + color("Followers:", YELLOW) + " " + color(String.valueOf(followers), BLUE));
        System.out.println();
        System.out.println(color("XP:", YELLOW) + " " + color(String.valueOf(xp), BLUE) + "          " + color("Level:", YELLOW) + " " + color(level, BLUE));
        System.out.println();
        System.out.println(color("Mood:", YELLOW) + " " + color(mood, BLUE) + " (current mood)");
        System.out.println();

        System.out.println(color("OPTIONS", PURPLE));
        System.out.println("-----------------------------------------------");
        System.out.println("[1] View Posts  [2] Edit Profile  [3] My Circles");
        System.out.println("[4] Settings    [5] Privacy Mode  [6] Exit      ");
        System.out.println("-----------------------------------------------");

        System.out.println(color("Tip: Type 'mood' to update your feeling!", GREEN));
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

    private static void dynamicDesign() {

        UserData userData = new UserData();

        int borderLength = 50;
        String headerTopBottomBorder = "=";
        String bodyLeftRightBorder = "||";
        String header = userData.getUserName() + "'s Profile";
        int bodyContentLength = borderLength - bodyLeftRightBorder.length() * 2;

        System.out.println(CYAN + BOLD);
        System.out.println(headerTopBottomBorder.repeat(borderLength));
        System.out.println(" ".repeat(borderLength / 2 - header.length() / 2) + header + "".repeat(borderLength / 2 - header.length() / 2));
        System.out.println(headerTopBottomBorder.repeat(borderLength));
        System.out.println(RESET);
        System.out.println();

        System.out.println(bodyLeftRightBorder + userData.getRealName() + " ".repeat(bodyContentLength - userData.getRealName().length() - userData.getUserName().length()) + userData.getUserName() + bodyLeftRightBorder);
        System.out.println(bodyLeftRightBorder + userData.getGender() + " ".repeat(bodyContentLength- userData.getGender().length() - userData.getLocation().length())+ bodyLeftRightBorder);
        System.out.println(bodyLeftRightBorder + "BIO -->" + " ".repeat(bodyContentLength - 7) + bodyLeftRightBorder);
        CommonMethods.paragraphDisplay(userData.getBio(),bodyLeftRightBorder,borderLength);
        System.out.println(bodyLeftRightBorder + "Member Since: " + userData.getMemberSince() + " ".repeat(bodyContentLength-userData.getMemberSince()-14) + bodyLeftRightBorder);
        System.out.println(bodyLeftRightBorder + "Posts: " + userData.getPostCount() + " ".repeat((bodyContentLength-userData.getPostCount()-userData.getFollowingCount()-userData.getFollowersCount()-29)/3) + "Following: " + userData.getFollowingCount() + " ".repeat((bodyContentLength-userData.getPostCount()-userData.getFollowingCount()-userData.getFollowersCount()-29)/3)  + "Followers: " + userData.getFollowersCount() + " ".repeat((bodyContentLength-userData.getPostCount()-userData.getFollowingCount()-userData.getFollowersCount()-29)/3)  + bodyLeftRightBorder );
        System.out.println(bodyLeftRightBorder + "XP: " + userData.getXP()  + " ".repeat((bodyContentLength-userData.getXP() - userData.getLevel() -11)/3) + "Level: " + userData.getLevel() + " ".repeat((bodyContentLength-userData.getXP() - userData.getLevel() -11)/3) + bodyLeftRightBorder);
        System.out.println(bodyLeftRightBorder + "Share: " + userData.getUserShareCode() + " ".repeat(bodyContentLength-userData.getUserShareCode().length()-6) + bodyLeftRightBorder);

        System.out.println();

        System.out.println(color("OPTIONS", PURPLE));
        System.out.println("-".repeat(borderLength));
        System.out.println("[1] View Posts  [2] Edit Profile  [3] My Circles");
        System.out.println("[4] Settings    [5] Privacy Mode  [6] Exit      ");
        System.out.println("-".repeat(borderLength));
    }
}
