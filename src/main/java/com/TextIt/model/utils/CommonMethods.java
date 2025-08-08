package com.TextIt.model.utils;

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
}
