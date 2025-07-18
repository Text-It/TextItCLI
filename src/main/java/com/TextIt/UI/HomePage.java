package com.TextIt.UI;

import java.util.Scanner;



public class HomePage {

    static Scanner sc = new Scanner(System.in);


    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    public static void home(String username) {
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
                    profile(username);
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
        Enter();
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
        Enter();
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

    private static void profile(String username) {
        System.out.println(CYAN +BOLD);
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
        System.out.println(color("BIO ->", YELLOW) + "  " + color(bio, BLUE) );
        System.out.println();
        System.out.println(color("Member since:", YELLOW) + " " + color(String.valueOf(member_since), BLUE));
        System.out.println();
        System.out.println(color("Posts:", YELLOW) + " " + color(String.valueOf(posts), BLUE) + "     " + color("following:", YELLOW) + " " + color(String.valueOf(following), BLUE) + "     " +
                color("Followers:", YELLOW) + " " + color(String.valueOf(followers), BLUE));
        System.out.println();
        System.out.println(color("XP:", YELLOW) + " " + color(String.valueOf(xp), BLUE) + "          " +
                color("Level:", YELLOW) + " " + color(level, BLUE));
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
            switch (choice){
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
                    System.out.println(RED+"Invalid choice. Please try again."+RESET);
            }
        }
    }

    private static void update_mood() {
        System.out.println("Enter your mood..."+GREEN);
        String nmood = sc.nextLine();
        System.out.println(CYAN +"Mood updated to :"+nmood+RESET);
        Enter();
    }

    private static void privacy_mode() {
        System.out.println(CYAN +"Viewing posts....."+RESET);
        Enter();
    }

    private static void my_circles() {
    }

    private static void edit_profile() {
    }

    private static void view_posts() {
        System.out.println(CYAN +"Viewing posts....."+RESET);
        Enter();
    }

    private static String color(String text, String color) {
        return color + text + RESET;
    }


    private static void goToNext() {
        System.out.println(CYAN + " Going to the next screen..." + RESET);
        Enter();
    }

    private static void goToPrevious() {
        System.out.println(CYAN + "Going back to the previous screen..." + RESET);
        Enter();
    }

    private static void Enter() {
        System.out.print(PURPLE + "(Press Enter to continue)" + RESET);
        sc.nextLine();
    }



}