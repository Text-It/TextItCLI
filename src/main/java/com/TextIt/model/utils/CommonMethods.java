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
    public  static void userProfile(int userid){

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
