package com.TextIt.UI;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;

public class SettingsPage {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(GREEN + BOLD +
                    "╔════════════════════════════════════════╗\n" +
                    "║           TextIt Settings              ║\n" +
                    "╚════════════════════════════════════════╝"
                    + RESET);
            System.out.println(GREEN + "1.Account Management");
            System.out.println(GREEN + "2.Privacy & Security");
            System.out.println(GREEN + "3.Appearance & Themes");
            System.out.println(GREEN + "4.Notification Settings");
            System.out.println(GREEN + "5.App preference");
            System.out.println(GREEN + "6.About & Legal");
            System.out.println(GREEN + "7.Session Options");
            System.out.println(GREEN + "8.Exit");
            System.out.println(BLUE + "Enter your choice");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    accountManagement();
                    break;
                case 2:
                    privacyAndSecurity();
                    break;
                case 3:
                    appearanceAndThemes();
                    break;
                case 4:
                    notificationSettings();
                    break;
                case 5:
                    appPreferences();
                    break;
                case 6:
                    aboutAndLegal();
                    break;
                case 7:
                    sessionOptions();
                    break;
                case 8:
                    System.out.println("Exiting... Goodbye!"+GREEN);
                    break;
                default:
                    System.out.println("Invalid option. Try again."+GREEN);
            }
        }
    }

    static void accountManagement() {
        System.out.println("-- Account Management --"+GREEN);
        System.out.println("1. Change Password"+PURPLE);
        System.out.println("2. Update Email"+PURPLE);
        System.out.println("3. Update Phone Number"+PURPLE);
        System.out.println("4. Edit Profile Information"+PURPLE);
    }

    static void privacyAndSecurity() {
        System.out.println("-- Privacy & Security --"+GREEN);
        System.out.println("1. Enable 2FA (Two-Factor Auth)"+PURPLE);
        System.out.println("2. Profile Visibility Control"+PURPLE);
        System.out.println("3. Block / Unblock Users"+PURPLE);
        System.out.println("4. Show/Hide Activity Status"+PURPLE);
    }

    static void appearanceAndThemes() {
        System.out.println("-- Appearance & Themes --"+GREEN);
        System.out.println("1. Dark Mode / Light Mode"+PURPLE);
        System.out.println("2. Theme Customization"+PURPLE);
    }

    static void notificationSettings() {
        System.out.println("-- Notification Settings --"+GREEN);
        System.out.println("1. Push Notifications"+PURPLE);
        System.out.println("2. Email Alerts"+PURPLE);
        System.out.println("3. Custom Activity Reminders"+PURPLE);
    }

    static void appPreferences() {
        System.out.println("-- App Preferences --"+GREEN);
        System.out.println("1. Clear Cache & Temp Files"+PURPLE);
    }

    static void aboutAndLegal() {
        System.out.println("-- About & Legal --"+GREEN);
        System.out.println("1. Version: 1.0.0 (Build 2025)"+PURPLE);
        System.out.println("2. Terms of Service"+PURPLE);
        System.out.println("3. Privacy Policy"+PURPLE);
        System.out.println("4. Contact Developer Support"+PURPLE);
    }

    static void sessionOptions() {
        System.out.println("-- Session Options --"+GREEN);
        System.out.println("1. Switch Account"+PURPLE);
        System.out.println("2. Logout"+PURPLE);
        System.out.println("3. Delete My Account"+PURPLE);
    }
}
