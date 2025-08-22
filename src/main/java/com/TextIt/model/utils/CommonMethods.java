package com.TextIt.model.utils;

import com.TextIt.database.DataBase;

import java.io.File;
import java.sql.*;
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

    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static final String BLACK = "\u001B[30m";
    public static final String GRAY = "\u001B[90m";


    public static final String CLEAR_SCREEN = "\u001B[2J\u001B[H";


    //Objects
    static Scanner scanner = new Scanner(System.in);
    private static final DataBase db = new DataBase();
    public static final DataBase.UserData userdata =db.new UserData();
    private static final DataBase.Post userpost =db.new Post();
    private static final DataBase.UserFollows userfollows =db.new UserFollows();

    public static void printDivider() {
        System.out.println(BRIGHT_CYAN + "-".repeat(80) + RESET);
    }
    public static void printChoice(int number, String description, String color) {
        System.out.println(BRIGHT_YELLOW + number + ". " + color + description + RESET);
    }


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
        System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);
        System.out.println(" ".repeat((boxLength - headerLength) / 2) + BRIGHT_WHITE + BOLD + pageHeader + RESET);
        System.out.println(" ".repeat((boxLength - descriptionLength) / 2) + BRIGHT_CYAN + pageDescription + RESET);
        System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Real Name + Username
        String separator = " ".repeat((spaceLeftForContent - realName.length() - username.length() - realNameLength - usernameLength) / 3);
        System.out.println(
                border + separator +
                YELLOW + realNameLabel + RESET + BRIGHT_WHITE + realName + RESET +
                separator + YELLOW + usernameLabel + RESET + BLUE + username + RESET +
                separator + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Gender + Location
        separator = " ".repeat((spaceLeftForContent - gender.length() - location.length() - genderLength - locationLength) / 3);
        System.out.println(
                border + separator +
                YELLOW + genderLabel + RESET + BRIGHT_WHITE + gender + RESET +
                separator + YELLOW + locationLabel + RESET + BRIGHT_WHITE + location + RESET +
                separator + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Bio
        System.out.println(border + YELLOW + bioLabel + RESET + " ".repeat(spaceLeftForContent - bioLabelLength) + border);
        CommonMethods.paragraphDisplay(bio, border, spaceLeftForContent);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);
        System.out.println(border + GRAY + "-".repeat(spaceLeftForContent) + RESET + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Member Since
        System.out.println(
                border + YELLOW + memberSinceLabel + RESET + BRIGHT_PURPLE + memberSince + RESET +
                " ".repeat(spaceLeftForContent - (memberSinceLength + memberSince.length())) + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Posts / Following / Followers
        separator = " ".repeat((spaceLeftForContent - posts.length() - following.length() - followers.length() - postsLength - followingLength - followersLength) / 4);
        System.out.println(
                border + separator +
                YELLOW + postsLabel + RESET + GREEN + posts + RESET +
                separator + YELLOW + followingLabel + RESET + GREEN + following + RESET +
                separator + YELLOW + followersLabel + RESET + GREEN + followers + RESET +
                separator + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // XP + Level
        separator = " ".repeat((spaceLeftForContent - xp.length() - level.length() - xpLength - levelLength) / 3);
        System.out.println(
                border + separator +
                YELLOW + xpLabel + RESET + BRIGHT_GREEN + xp + RESET +
                separator + YELLOW + levelLabel + RESET + BRIGHT_GREEN + level + RESET +
                separator + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Share Code
        System.out.println(
                border + YELLOW + shareLabel + RESET + BRIGHT_PURPLE + shareCode + RESET +
                " ".repeat(spaceLeftForContent - (shareLength + shareCode.length())) + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);
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
    public static void editProfile(int userId) {
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
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new First Name: ");
                    String newFirst = scanner.nextLine();
                    if(userdata.updateFirstName(userId, newFirst)){
                    System.out.println("First name updated!");
                    }
                }
                case 2 -> {
                    System.out.print("Enter new Last Name: ");
                    String newLast = scanner.nextLine();
                    if(userdata.updateLastName(userId, newLast)){
                    System.out.println("Last name updated!");}
                }
                case 3 -> {
                    System.out.print("Enter new Username: ");
                    String newUsername = scanner.nextLine();
                    if (userdata.updateUserName(userId, newUsername)){
                    System.out.println("Username updated!");}
                }
                case 4 -> {
                    System.out.print("Enter new Gender: ");
                    String newGender = scanner.nextLine();
                    if(userdata.updateGender(userId, newGender)) {
                        System.out.println("Gender updated!");
                    }
                }
                case 5 -> {
                    System.out.print("Enter new Location: ");
                    String newLocation = scanner.nextLine();
                    if(userdata.updateLocation(userId, newLocation)){
                    System.out.println("Location updated!");}
                }
                case 6 -> {
                    System.out.print("Enter new Bio: ");
                    String newBio = scanner.nextLine();
                    if(userdata.updateBio(userId, newBio)){
                    System.out.println("Bio updated!");}
                }
                case 7 -> {
                    System.out.println("Exiting Edit Profile.");
                    editing = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static int featchIdForNotification(String content , String type) throws SQLException {
        DataBase db = new DataBase();
        try (Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword())) {
            if (type.equalsIgnoreCase("message")) {
                PreparedStatement ps = conn.prepareStatement("select id from messages where  message= ? ");
                ps.setString(1, content);
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt(1);
            } else if (type.equalsIgnoreCase("Comments")) {
                PreparedStatement ps = conn.prepareStatement("select c_id from comments where content = ? ");
                ps.setString(1, content);
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt(1);
            } else if (type.equalsIgnoreCase("like")) {
                PreparedStatement ps = conn.prepareStatement("select like_id from likes where user_id = ? ");
                ps.setString(1, content);
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt(1);

            } else {
                System.out.println("Unsupported notification type: " + type);
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }



}
