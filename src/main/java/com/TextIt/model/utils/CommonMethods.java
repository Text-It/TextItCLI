package com.TextIt.model.utils;

import java.util.Scanner;

public class CommonMethods {

    //Objects
    static Scanner scanner = new Scanner(System.in);

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    public static void pressEnterToContinue() {
        System.out.println(PURPLE + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }


}
