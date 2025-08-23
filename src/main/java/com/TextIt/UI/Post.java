package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;

import java.util.Scanner;

public class Post {
    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdb = db.new UserData();
    private static final DataBase.Post postbd = db.new Post();
    private static String content;
    private static String shareCode;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(CommonMethods.BRIGHT_RED + "Error: User ID not provided!" + CommonMethods.RESET);
            return;
        }

        int userid = Integer.parseInt(args[0]);

        CommonMethods.clearConsole();
        showPostCreationHeader();

        // Get user info
        String username = userdb.getUserName(userid);
        if (username == null || username.isEmpty()) {
            System.out.println(CommonMethods.BRIGHT_RED + " Error: Could not retrieve user information!" + CommonMethods.RESET);
            CommonMethods.pressEnterToContinue();
            return;
        }

        // Show user info section
        showUserInfoSection(username);

        // Get post content with beautiful interface
        content = getPostContent();

        if (content == null || content.trim().isEmpty()) {
            System.out.println(CommonMethods.BRIGHT_YELLOW + " Post creation cancelled." + CommonMethods.RESET);
            CommonMethods.pressEnterToContinue();
            return;
        }

        // Show preview
        showPostPreview(username, content);

        // Confirm and post
        if (confirmPost()) {
            shareCode = username + (int) (Math.random() * 1000000000);
            boolean insert = postbd.insertPost(userid, content, shareCode);

            if (insert) {
                showSuccessMessage(shareCode);
            } else {
                showErrorMessage();
            }
        } else {
            System.out.println(CommonMethods.BRIGHT_YELLOW + "Post creation cancelled." + CommonMethods.RESET);
        }

        CommonMethods.pressEnterToContinue();
    }

    private static void showPostCreationHeader() {
        System.out.println(CommonMethods.BRIGHT_CYAN + "╔══════════════════════════════════════════════════════════════════════════════╗" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + "║" + CommonMethods.RESET + "                         " + CommonMethods.BRIGHT_WHITE + CommonMethods.BOLD + " TEXTIT POST CREATOR " + CommonMethods.RESET + "                                " + CommonMethods.BRIGHT_CYAN + "║" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + "║" + CommonMethods.RESET + "                " + CommonMethods.BRIGHT_YELLOW + "Share your thoughts with the TextIt community!" + CommonMethods.RESET + "                " + CommonMethods.BRIGHT_CYAN + "║" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + "╚══════════════════════════════════════════════════════════════════════════════╝" + CommonMethods.RESET);
        System.out.println();
    }

    private static void showUserInfoSection(String username) {
        System.out.println(CommonMethods.BRIGHT_GREEN + " Author: " + CommonMethods.RESET + CommonMethods.BRIGHT_WHITE + "@" + username + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_GREEN + "Date: " + CommonMethods.RESET + CommonMethods.BRIGHT_WHITE + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm")) + CommonMethods.RESET);
        System.out.println();
    }

    private static String getPostContent() {
        System.out.println(CommonMethods.BRIGHT_BLUE + "Post Content:" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + "┌" + "─".repeat(68) + "┐" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + "│" + CommonMethods.RESET + " " + CommonMethods.BRIGHT_WHITE + "Share your thoughts, ideas, or experiences..." + CommonMethods.RESET + " " + CommonMethods.BRIGHT_CYAN + "                     │" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + "│" + CommonMethods.RESET + " " + CommonMethods.GRAY + "Tip: You can use multiple lines. Press Enter twice to finish." + CommonMethods.RESET + " " + CommonMethods.BRIGHT_CYAN + "     │" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + "└" + "─".repeat(68) + "┘" + CommonMethods.RESET);

        StringBuilder postContent = new StringBuilder();
        String line;
        System.out.print(CommonMethods.BRIGHT_WHITE + "> " + CommonMethods.RESET);

        while (true) {
            line = sc.nextLine();
            if (line.trim().isEmpty() && postContent.length() > 0) {
                break; // Empty line after content means finish
            }
            if (postContent.length() > 0) {
                postContent.append("\n");
            }
            postContent.append(line);

            // Check if content is getting too long
            if (postContent.length() > 500) {
                System.out.println(CommonMethods.BRIGHT_YELLOW + " Content is getting long. Consider keeping it concise!" + CommonMethods.RESET);
            }

            // Show character count
            System.out.println(CommonMethods.GRAY + "Characters: " + postContent.length() + CommonMethods.RESET);
        }

        return postContent.toString().trim();
    }

    private static void showPostPreview(String username, String content) {
        final int WIDTH = 68; // inner width for text between borders

        System.out.println();
        System.out.println(CommonMethods.BRIGHT_PURPLE + "POST PREVIEW:" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + "┌" + "─".repeat(WIDTH) + "┐" + CommonMethods.RESET);

        // Header line with username + date
        String header = "@" + username + " " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm"));

        if (header.length() > WIDTH) {
            header = header.substring(0, WIDTH); // truncate if too long
        }
        System.out.println(CommonMethods.BRIGHT_CYAN + "│" + CommonMethods.RESET + " " + CommonMethods.BRIGHT_WHITE + CommonMethods.BOLD + header + CommonMethods.RESET + " ".repeat(WIDTH - header.length() - 1) // pad rest of line
                + CommonMethods.BRIGHT_CYAN + "│" + CommonMethods.RESET);

        // Separator line
        System.out.println(CommonMethods.BRIGHT_CYAN + "│" + CommonMethods.RESET + "─".repeat(WIDTH) + CommonMethods.BRIGHT_CYAN + "│" + CommonMethods.RESET);

        // Display content
        String[] contentLines = content.split("\n");
        for (String contentLine : contentLines) {
            int start = 0;
            while (start < contentLine.length()) {
                int end = Math.min(start + WIDTH, contentLine.length());
                String displayLine = contentLine.substring(start, end);
                System.out.println(CommonMethods.BRIGHT_CYAN + "│" + CommonMethods.RESET + CommonMethods.BRIGHT_WHITE + displayLine + " ".repeat(WIDTH - displayLine.length()) + CommonMethods.RESET + CommonMethods.BRIGHT_CYAN + "│" + CommonMethods.RESET);
                start = end;
            }
            if (contentLine.isEmpty()) { // handle blank lines
                System.out.println(CommonMethods.BRIGHT_CYAN + "│" + " ".repeat(WIDTH) + "│" + CommonMethods.RESET);
            }
        }

        System.out.println(CommonMethods.BRIGHT_CYAN + "└" + "─".repeat(WIDTH) + "┘" + CommonMethods.RESET);
        System.out.println();
    }


    private static boolean confirmPost() {
        System.out.print(CommonMethods.BRIGHT_YELLOW + "Ready to post? (y/n): " + CommonMethods.RESET);
        String confirm = sc.nextLine().toLowerCase();
        return confirm.equals("y") || confirm.equals("yes");
    }

    private static void showSuccessMessage(String shareCode) {
        System.out.println();
        System.out.println(CommonMethods.BRIGHT_GREEN + "╔══════════════════════════════════════════════════════════════════════════════╗" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_GREEN + "║" + CommonMethods.RESET + "                               " + CommonMethods.BRIGHT_WHITE + CommonMethods.BOLD + " POST SUCCESSFUL! " + CommonMethods.RESET + "                             " + CommonMethods.BRIGHT_GREEN + "║" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_GREEN + "║" + CommonMethods.RESET + "                " + CommonMethods.BRIGHT_YELLOW + "Your post has been shared with the community!" + CommonMethods.RESET + "                 " + CommonMethods.BRIGHT_GREEN + "║" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_GREEN + "║" + CommonMethods.RESET + "                            " + CommonMethods.BRIGHT_CYAN + "Share Code: " + CommonMethods.BRIGHT_WHITE + shareCode + CommonMethods.RESET + "                   " + CommonMethods.BRIGHT_GREEN + "║" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_GREEN + "╚══════════════════════════════════════════════════════════════════════════════╝" + CommonMethods.RESET);
    }

    private static void showErrorMessage() {
        System.out.println();
        System.out.println(CommonMethods.BRIGHT_RED + "╔══════════════════════════════════════════════════════════════════════════════╗" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_RED + "║" + CommonMethods.RESET + "                                " + CommonMethods.BRIGHT_WHITE + CommonMethods.BOLD + " POST FAILED! " + CommonMethods.RESET + "                             " + CommonMethods.BRIGHT_RED + "║" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_RED + "║" + CommonMethods.RESET + "               " + CommonMethods.BRIGHT_YELLOW + "Something went wrong. Please try again later." + CommonMethods.RESET + "                  " + CommonMethods.BRIGHT_RED + "║" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_RED + "╚══════════════════════════════════════════════════════════════════════════════╝" + CommonMethods.RESET);
    }

}