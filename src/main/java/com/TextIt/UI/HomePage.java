package com.TextIt.UI;

import java.util.Scanner;

public class HomePage {

    private static final Scanner sc = new Scanner(System.in);


    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    public static void showHomePage(String username) {
        while (true) {
            System.out.println(CYAN + BOLD);
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║               TextIt Home              ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print(RESET);

            System.out.println(YELLOW + "Welcome, " + username + "!" + RESET);
            System.out.println(YELLOW + "1. " + GREEN + "View Feed");
            System.out.println(YELLOW + "2. " + BLUE + "View Profile");
            System.out.println(YELLOW + "3. " + PURPLE + "Search Users");
            System.out.println(YELLOW + "4. " + CYAN + "Create Post");
            System.out.println(YELLOW + "5. " + RED + "Logout" + RESET);
            System.out.print(PURPLE + "\nEnter your choice: " + RESET);

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "Please enter a valid number!" + RESET);
                continue;
            }

            switch (choice) {
                case 1:
                    viewFeed();
                    break;
                case 2:
                    viewProfile(username);
                    break;
                case 3:
                    searchUsers();
                    break;
                case 4:
                    createPost();
                    break;
                case 5:
                    System.out.println(RED + "Logging out..." + RESET);
                    return;
                default:
                    System.out.println(RED + "Invalid choice. Try again." + RESET);
            }
        }
    }

    private static void viewFeed() {
        System.out.println(GREEN + BOLD + "Viewing Feed..." + RESET);
        waitForEnter();
    }

    private static void viewProfile(String username) {
        System.out.println(BLUE + BOLD + "Profile of " + username + RESET);
        waitForEnter();
    }

    private static void searchUsers() {
        System.out.println(PURPLE + BOLD + "User Search" + RESET);
        waitForEnter();
    }

    private static void createPost() {
        System.out.println(CYAN + BOLD + "New Post" + RESET);
        waitForEnter();
    }

    private static void waitForEnter() {
        System.out.print(PURPLE + "\n(Press Enter to continue)" + RESET);
        sc.nextLine();
    }
}




