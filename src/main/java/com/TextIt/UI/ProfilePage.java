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
                    view_posts();
                    break;
                case "2":
                    editProfile(userid);
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

    private static void view_posts() {
        System.out.println(CYAN + "Viewing posts....." + RESET);
        CommonMethods.pressEnterToContinue();
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

    private static void editProfile(int userId) {
        boolean editing = true;

        while (editing) {
            // Fetch latest values from DB
            String firstName = userdata.getFirstName(userId);
            String lastName = userdata.getLastName(userId);
            String username = userdata.getUserName(userId);
            String gender = userdata.getGender(userId);
            String location = userdata.getLocation(userId);
            String bio = userdata.getBio(userId);

            int boxLength = 70;
            String border = "||";
            int spaceLeftForContent = boxLength - border.length() * 2;

            String pageHeader = "Edit Profile";
            int headerLength = pageHeader.length();

            // Render Profile Edit Menu
            System.out.println("=".repeat(boxLength));
            System.out.println(" ".repeat((boxLength - headerLength) / 2) + pageHeader + " ".repeat((boxLength - headerLength) / 2));
            System.out.println("=".repeat(boxLength));

            System.out.println(border + " 1) First Name : " + firstName + " ".repeat(spaceLeftForContent - 15 - firstName.length()) + border);
            System.out.println(border + " 2) Last Name  : " + lastName + " ".repeat(spaceLeftForContent - 15 - lastName.length()) + border);
            System.out.println(border + " 3) Username   : " + username + " ".repeat(spaceLeftForContent - 15 - username.length()) + border);
            System.out.println(border + " 4) Gender     : " + gender + " ".repeat(spaceLeftForContent - 15 - gender.length()) + border);
            System.out.println(border + " 5) Location   : " + location + " ".repeat(spaceLeftForContent - 15 - location.length()) + border);
            System.out.println(border + " 6) Bio        : " + bio + " ".repeat(spaceLeftForContent - 15 - bio.length()) + border);
            System.out.println("-".repeat(boxLength));
            System.out.println(border + " 7) Exit " + " ".repeat(spaceLeftForContent - 7) + border);
            System.out.println("=".repeat(boxLength));

            // User choice
            System.out.print("Enter the number of the field you want to edit: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new First Name: ");
                    String newFirst = sc.nextLine();
                    userdata.updateFirstName(userId, newFirst);
                    System.out.println("✅ First name updated!");
                }
                case 2 -> {
                    System.out.print("Enter new Last Name: ");
                    String newLast = sc.nextLine();
                    userdata.updateLastName(userId, newLast);
                    System.out.println("✅ Last name updated!");
                }
                case 3 -> {
                    System.out.print("Enter new Username: ");
                    String newUsername = sc.nextLine();
                    userdata.updateUserName(userId, newUsername);
                    System.out.println("✅ Username updated!");
                }
                case 4 -> {
                    System.out.print("Enter new Gender: ");
                    String newGender = sc.nextLine();
                    userdata.updateGender(userId, newGender);
                    System.out.println("✅ Gender updated!");
                }
                case 5 -> {
                    System.out.print("Enter new Location: ");
                    String newLocation = sc.nextLine();
                    userdata.updateLocation(userId, newLocation);
                    System.out.println("✅ Location updated!");
                }
                case 6 -> {
                    System.out.print("Enter new Bio: ");
                    String newBio = sc.nextLine();
                    userdata.updateBio(userId, newBio);
                    System.out.println("✅ Bio updated!");
                }
                case 7 -> {
                    System.out.println("👋 Exiting Edit Profile.");
                    editing = false;
                }
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }


    private static void settings() {
    }

}
