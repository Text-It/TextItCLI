package com.TextIt.model.utils;

import com.TextIt.database.DataBase;

import java.io.File;
import java.util.Scanner;

public class CommonMethods {

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";
    //Objects
    static Scanner scanner = new Scanner(System.in);
    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdata =db.new UserData();
    private static final DataBase.Post userpost =db.new Post();
    private static final DataBase.UserFollows userfollows =db.new UserFollows();

    public static void pressEnterToContinue() {
        System.out.println(PURPLE + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    public static String color(String text, String color) {
        return color + text + RESET;
    }

    public static void openInNewCMD(String className, String... args) {
        try {
            // Get java executable and working directory
            String javaBin = System.getProperty("java.home") + "\\bin\\java";
            String workingDir = System.getProperty("user.dir");

            // Build classpath: include compiled classes and all jars in target/dependency
            String classpath = "\"" + workingDir + "\\target\\classes\"";

            File depDir = new File(workingDir + "\\target\\dependency");
            if (depDir.exists()) {
                File[] jars = depDir.listFiles((dir, name) -> name.endsWith(".jar"));
                if (jars != null) {
                    for (File jar : jars) {
                        classpath += ";" + "\"" + jar.getAbsolutePath() + "\"";
                    }
                }
            }

            // Prepare argument string
            String argString = String.join(" ", args);

            // Final Java command
            String command = String.format("\"%s\" -cp %s %s %s", javaBin, classpath, className, argString);

            // Open in new CMD window
            new ProcessBuilder("cmd", "/c", "start", "cmd", "/k", "title " + className + " && " + command).directory(new File(workingDir)).inheritIO().start();

        } catch (Exception e) {
            System.err.println("❌ Failed to launch " + className);
            e.printStackTrace();
        }
    }

    public static void paragraphDisplay(String text, String borderDesign, int boxWidth) {

        String[] words = text.split(" ");
        StringBuffer line = new StringBuffer(borderDesign);

        for (int i = 0; i < words.length; i++) {
            if (line.length() + words[i].length() + borderDesign.length() > boxWidth) {
                while (line.length() <= boxWidth) {
                    line.append(" ");
                }
                System.out.println(line + borderDesign);
                line = new StringBuffer(borderDesign + " " + words[i]);
            } else {
                line.append(" ");
                line.append(words[i]);
            }
        }
        // Fill the remaining spaces in the last line
        while (line.length() <= boxWidth) {
            line.append(" ");
        }
        System.out.println(line + borderDesign);
    }
    public static void userProfile(int userId) {
        int boxLength = 70;
        String border = "||";
        int spaceLeftForContent = boxLength - border.length() * 2;

        // ===== Page Headers =====
        String pageHeader = userdata.getUserName(userId) + "'s Profile";
        String pageDescription = "Welcome to " + userdata.getUserName(userId) + "'s space";

        // ===== Section Labels =====
        String realNameLabel = "Name: ";
        String usernameLabel = "Username: ";
        String genderLabel = "Gender: ";
        String locationLabel = "Location: ";
        String bioLabel = "BIO: ";
        String memberSinceLabel = "Member Since: ";
        String postsLabel = "Posts: ";
        String followingLabel = "Following: ";
        String followersLabel = "Followers: ";
        String xpLabel = "XP: ";
        String levelLabel = "Level: ";
        String shareLabel = "Share: ";

        // ===== Lengths =====
        int headerLength = pageHeader.length();
        int descriptionLength = pageDescription.length();
        int realNameLength = realNameLabel.length();
        int usernameLength = usernameLabel.length();
        int genderLength = genderLabel.length();
        int locationLength = locationLabel.length();
        int bioLabelLength = bioLabel.length();
        int memberSinceLength = memberSinceLabel.length();
        int postsLength = postsLabel.length();
        int followingLength = followingLabel.length();
        int followersLength = followersLabel.length();
        int xpLength = xpLabel.length();
        int levelLength = levelLabel.length();
        int shareLength = shareLabel.length();

        // ===== User Data =====
        String realName = userdata.getRealName(userId);
        String username = "@" + userdata.getUserName(userId);
        String gender = userdata.getGender(userId);
        String location = userdata.getLocation(userId);
        String bio = userdata.getBio(userId);
        String memberSince = userdata.getMemberSince(userId); // int → string
        String posts = String.valueOf(userpost.getPostCount(userId));
        String following = String.valueOf(userfollows.getFollowingCount(userId));
        String followers = String.valueOf(userfollows.getFollowersCount(userId));
        String xp = String.valueOf(userdata.getXP(userId));
        String level = String.valueOf(userdata.getLevel(userId));
        String shareCode = userdata.getUserShareCode(userId);

        // ===== Rendering =====
        CommonMethods.clearConsole();
        System.out.println("=".repeat(boxLength));
        System.out.println(" ".repeat((boxLength - headerLength) / 2) + pageHeader);
        System.out.println(" ".repeat((boxLength - descriptionLength) / 2) + pageDescription);
        System.out.println("=".repeat(boxLength));
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Real Name + Username
        String separator = " ".repeat((spaceLeftForContent - realName.length() - username.length() - realNameLength - usernameLength) / 3);
        System.out.println(border + separator + realNameLabel + realName + separator + usernameLabel + username + separator + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Gender + Location
        separator = " ".repeat((spaceLeftForContent - gender.length() - location.length() - genderLength - locationLength) / 3);
        System.out.println(border + separator + genderLabel + gender + separator + locationLabel + location + separator + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Bio
        System.out.println(border + bioLabel + " ".repeat(spaceLeftForContent - bioLabelLength) + border);
        CommonMethods.paragraphDisplay(bio, border, spaceLeftForContent);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);
        System.out.println(border + "-".repeat(spaceLeftForContent) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Member Since
        System.out.println(border + memberSinceLabel + memberSince + " ".repeat(spaceLeftForContent - (memberSinceLength + memberSince.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Posts / Following / Followers
        separator = " ".repeat((spaceLeftForContent - posts.length() - following.length() - followers.length() - postsLength - followingLength - followersLength) / 4);
        System.out.println(border + separator + postsLabel + posts + separator + followingLabel + following + separator + followersLabel + followers + separator + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // XP + Level
        separator = " ".repeat((spaceLeftForContent - xp.length() - level.length() - xpLength - levelLength) / 3);
        System.out.println(border + separator + xpLabel + xp + separator + levelLabel + level + separator + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Share Code
        System.out.println(border + shareLabel + shareCode + " ".repeat(spaceLeftForContent - (shareLength + shareCode.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        System.out.println("=".repeat(boxLength));
    }


    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Unable to clear console: " + e.getMessage());
        }
    }



}
