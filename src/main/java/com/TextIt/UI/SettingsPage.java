package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.security.Hashing;
import com.TextIt.security.OTPHandler;
import com.TextIt.service.pages.LoginAuth;
import com.TextIt.service.pages.SignUpAuth;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;
import static com.TextIt.model.utils.CommonMethods.pressEnterToContinue;

public class SettingsPage {

    static Scanner sc = new Scanner(System.in);
    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdata = db.new UserData();
    private static final DataBase.AccountManager accountManager = db.new AccountManager();
    private static final DataBase.Profile profile =db.new Profile();
    private static int userID;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(YELLOW + "No user ID provided. Using demo user ID 1." + RESET);
            userID = 1;
        } else {
            try {
                userID = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println(RED + "Invalid user ID: " + args[0] + ". Using demo user ID 1." + RESET);
                userID = 1;
            }
        }

        Settings();
    }

    private static void Settings() {
        while (true) {
            clearScreen();
            displaySettings();

            System.out.println(GREEN + "┌─────────────────────────────────────────────┐");
            System.out.println(GREEN + "│               SETTINGS MENU                 │");
            System.out.println(GREEN + "└─────────────────────────────────────────────┘" + RESET);
            System.out.println();

            System.out.println(CYAN + "[1] " + RESET + "Account Management");
            System.out.println(CYAN + "[2] " + RESET + "Privacy & Security");
            System.out.println(CYAN + "[3] " + RESET + "Appearance & Themes");
            System.out.println(CYAN + "[4] " + RESET + "Notification Settings");
            System.out.println(CYAN + "[5] " + RESET + "App Preferences");
            System.out.println(CYAN + "[6] " + RESET + "About & Legal");
            System.out.println(CYAN + "[7] " + RESET + "Session Options");
            System.out.println(CYAN + "[8] " + RED + "Exit Settings");
            System.out.println();

            System.out.print(PURPLE + "Enter your choice: " + RESET);

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    accountManagement();
                    break;
                case "2":
                    privacyAndSecurity();
                    break;
                case "3":
                    appearanceAndThemes();
                    break;
                case "4":
                    notificationSettings();
                    break;
                case "5":
                    appPreferences();
                    break;
                case "6":
                    aboutAndLegal();
                    break;
                case "7":
                    sessionOptions();
                    break;
                case "8":
                    System.out.println(GREEN + "\n Settings saved. Goodbye!" + RESET);
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
                    pressEnterToContinue();
            }
        }
    }

    private static void displaySettings() {
        String username = userdata.getUserName(userID);
        if (username == null) {
            username = "Unknown User";
        }
        System.out.println(CYAN + BOLD);
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                 TextIt Settings              ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  User: " + username + " ".repeat(Math.max(1, 37 - username.length())) + "║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    static void accountManagement() {
        while (true) {
            clearScreen();
            System.out.println(GREEN + BOLD + "═══ ACCOUNT MANAGEMENT ═══" + RESET);
            System.out.println();

            // Display current account info
            displayAccountInfo(userID);

            System.out.println(CYAN + "[1] " + RESET + "Change Password");
            System.out.println(CYAN + "[2] " + RESET + "Update Email");
            System.out.println(CYAN + "[3] " + RESET + "Update Phone Number");
            System.out.println(CYAN + "[4] " + RESET + "Edit Profile Information");
            System.out.println(CYAN + "[5] " + RESET + "View Account Details");
            System.out.println(CYAN + "[6] " + RED + "Back to Main Settings");
            System.out.println();

            System.out.print(PURPLE + "Enter your choice: " + RESET);
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    changePassword();
                    break;
                case "2":
                    updateEmail();
                    break;
                case "3":
                    updatePhoneNumber();
                    break;
                case "4":
                    CommonMethods.editProfile(userID);
                    break;
                case "5":
                    viewAccountDetails(userID);
                    break;
                case "6":
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
                    pressEnterToContinue();
            }
        }
    }

    public static void displayAccountInfo(int userId) {
        int boxLength = 70;
        String border = "||";
        int spaceLeftForContent = boxLength - border.length() * 2;

        // ===== Page Headers =====
        String pageHeader = "Account Information";
        String pageDescription = "Details for @" + userdata.getUserName(userId);

        // ===== Labels =====
        String usernameLabel = "Username: ";
        String realNameLabel = "Name: ";
        String emailLabel = "Email: ";
        String mobileLabel = "Mobile: ";
        String shareCodeLabel = "Share Code: ";

        // ===== Data =====
        String username = userdata.getUserName(userId);
        String realName = userdata.getRealName(userId);
        String email = userdata.getEmail(userId);
        String mobile = userdata.getMobileNumber(userId);
        String shareCode = userdata.getUserShareCode(userId);

        // ===== Lengths =====
        int usernameLength = usernameLabel.length();
        int realNameLength = realNameLabel.length();
        int emailLength = emailLabel.length();
        int mobileLength = mobileLabel.length();
        int shareCodeLength = shareCodeLabel.length();

        // ===== Rendering =====
        CommonMethods.clearConsole();
        System.out.println("=".repeat(boxLength));
        System.out.println(" ".repeat((boxLength - pageHeader.length()) / 2) + pageHeader);
        System.out.println(" ".repeat((boxLength - pageDescription.length()) / 2) + pageDescription);
        System.out.println("=".repeat(boxLength));
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Username
        System.out.println(border + usernameLabel + username +
                " ".repeat(spaceLeftForContent - (usernameLength + username.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Real Name
        System.out.println(border + realNameLabel + realName +
                " ".repeat(spaceLeftForContent - (realNameLength + realName.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Email
        System.out.println(border + emailLabel + email +
                " ".repeat(spaceLeftForContent - (emailLength + email.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Mobile
        System.out.println(border + mobileLabel + mobile +
                " ".repeat(spaceLeftForContent - (mobileLength + mobile.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Share Code
        System.out.println(border + shareCodeLabel + shareCode +
                " ".repeat(spaceLeftForContent - (shareCodeLength + shareCode.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        System.out.println("=".repeat(boxLength));
    }


    private static void changePassword() {

        LoginAuth la = new LoginAuth();
        SignUpAuth signup = new SignUpAuth();
        Scanner scanner = new Scanner(System.in);
        String newPassword ="";
        String conformPassword="";

        System.out.println(GREEN + "\n═══ CHANGE PASSWORD ═══" + RESET);

        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();

        if(la.verifyPassword(currentPassword)){
            do {
                System.out.print(YELLOW + "Enter your new password: " + RESET);
                newPassword = scanner.nextLine();
                System.out.print(YELLOW + "Enter conformed password: " + RESET);
                conformPassword = scanner.nextLine();
                if(!newPassword.equals(conformPassword)){
                    System.out.println("New password and confirm password must be the same.");
                }

            } while (!(signup.verifyPassword(newPassword) && newPassword.equals(conformPassword)));

            String hashedPassword = Hashing.generateHashCode(newPassword);

            if(userdata.updatePassword(userID, hashedPassword)){
                System.out.println(GREEN + "\nPassword updated successfully" + RESET);
                CommonMethods.pressEnterToContinue();
            }
        }

        pressEnterToContinue();
    }

    private static void updateEmail() {

        SignUpAuth newUser = new SignUpAuth();
        Scanner scanner = new Scanner(System.in);
        String email ="";
        String generatedOtp ="";


        System.out.println(GREEN + "\n═══ UPDATE EMAIL ═══" + RESET);

        String currentEmail = userdata.getEmail(userID);
        System.out.println("Current email: " + CYAN + userdata.getEmail(userID) + RESET);
        System.out.println();

        while (true) {

            do {                                                // valid if email is valid or not
                System.out.print(YELLOW + "Enter email: " + RESET);
                email = scanner.nextLine();
            } while (!newUser.verifyEmail(email));

            generatedOtp = OTPHandler.generateOTP(6);       // generate a 6 digit otp

            if (OTPHandler.verifyOTPSend(email, generatedOtp)) {        //verify is otp is sent or not
                break;
            }}
            if (!OTPHandler.verifyOTP(generatedOtp, scanner)) {                // verify if otp entered by user is right or wrong
                return;
            }

            if(userdata.updateEmail(userID,email)){
                System.out.println(GREEN + "Email updated successfully" + RESET);
                CommonMethods.pressEnterToContinue();
            }

    }

    private static void updatePhoneNumber() {
        System.out.println(GREEN + "\n═══ UPDATE PHONE NUMBER ═══" + RESET);

        SignUpAuth newUser = new SignUpAuth();
        Scanner scanner = new Scanner(System.in);
        String phoneNumber ="";

        String currentMobile = userdata.getMobileNumber(userID);
        System.out.println("Current mobile: " + CYAN + userdata.getMobileNumber(userID) + RESET);
        System.out.println();

        do {
            System.out.print(YELLOW + "Enter phone number: " + RESET);
            phoneNumber = scanner.nextLine();
        } while (!newUser.verifyPhoneNumber(phoneNumber));

        if (userdata.updateMobileNumber(userID, phoneNumber)) {
            System.out.println(GREEN + "Phone number updated successfully" + RESET);
            CommonMethods.pressEnterToContinue();
        }

    }


    private static void viewAccountDetails(int userId) {
        int boxLength = 70;
        String border = "||";
        int spaceLeftForContent = boxLength - border.length() * 2;

        // ===== Page Headers =====
        String pageHeader = "Account Information";
        String pageDescription = "Overview of @" + userdata.getUserName(userId);

        // ===== Labels =====
        String usernameLabel = "Username: ";
        String realNameLabel = "Name: ";
        String emailLabel = "Email: ";
        String mobileLabel = "Mobile: ";
        String bioLabel = "Bio: ";
        String memberSinceLabel = "Member Since: ";
        String shareCodeLabel = "Share Code: ";

        // ===== User Data (handle nulls manually) =====
        String username = userdata.getUserName(userId);
        if (username == null || username.isBlank()) username = "Username not set";

        String realName = userdata.getRealName(userId);
        if (realName == null || realName.isBlank()) realName = "Name not provided";

        String email = userdata.getEmail(userId);
        if (email == null || email.isBlank()) email = "Email not configured";

        String mobile = userdata.getMobileNumber(userId);
        if (mobile == null || mobile.isBlank()) mobile = "Phone not added";

        String bio = userdata.getBio(userId);
        if (bio == null || bio.isBlank()) bio = "No bio added yet";

        String memberSince = userdata.getMemberSince(userId);
        if (memberSince == null || memberSince.isBlank()) memberSince = "Unknown";

        String shareCode = userdata.getUserShareCode(userId);
        if (shareCode == null || shareCode.isBlank()) shareCode = "Not generated";

        // ===== Lengths =====
        int usernameLength = usernameLabel.length();
        int realNameLength = realNameLabel.length();
        int emailLength = emailLabel.length();
        int mobileLength = mobileLabel.length();
        int bioLabelLength = bioLabel.length();
        int memberSinceLength = memberSinceLabel.length();
        int shareCodeLength = shareCodeLabel.length();

        // ===== Rendering =====
        CommonMethods.clearConsole();
        System.out.println("=".repeat(boxLength));
        System.out.println(" ".repeat((boxLength - pageHeader.length()) / 2) + pageHeader);
        System.out.println(" ".repeat((boxLength - pageDescription.length()) / 2) + pageDescription);
        System.out.println("=".repeat(boxLength));
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Username
        System.out.println(border + usernameLabel + username +
                " ".repeat(spaceLeftForContent - (usernameLength + username.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Real Name
        System.out.println(border + realNameLabel + realName +
                " ".repeat(spaceLeftForContent - (realNameLength + realName.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Email
        System.out.println(border + emailLabel + email +
                " ".repeat(spaceLeftForContent - (emailLength + email.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Mobile
        System.out.println(border + mobileLabel + mobile +
                " ".repeat(spaceLeftForContent - (mobileLength + mobile.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Bio
        System.out.println(border + bioLabel +
                " ".repeat(spaceLeftForContent - bioLabelLength) + border);
        CommonMethods.paragraphDisplay(bio, border, spaceLeftForContent);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Member Since
        System.out.println(border + memberSinceLabel + memberSince +
                " ".repeat(spaceLeftForContent - (memberSinceLength + memberSince.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Share Code
        System.out.println(border + shareCodeLabel + shareCode +
                " ".repeat(spaceLeftForContent - (shareCodeLength + shareCode.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        System.out.println("=".repeat(boxLength));

        pressEnterToContinue();
    }

    static void privacyAndSecurity() {
        while (true) {
            clearScreen();
            System.out.println(GREEN + BOLD + "═══ PRIVACY & SECURITY ═══" + RESET);
            System.out.println();

            System.out.println(CYAN + "[1] " + RESET + "Enable/Disable 2FA (Two-Factor Auth)");
            System.out.println(CYAN + "[2] " + RESET + "Profile Visibility Control");
            System.out.println(CYAN + "[3] " + RESET + "Block / Unblock Users");
            System.out.println(CYAN + "[4] " + RESET + "Show/Hide Activity Status");
            System.out.println(CYAN + "[5] " + RESET + "Data Privacy Settings");
            System.out.println(CYAN + "[6] " + RED + "Back to Main Settings");
            System.out.println();

            System.out.print(PURPLE + "Enter your choice: " + RESET);
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    toggle2FA();
                    break;
                case "2":
                    profileVisibility();
                    break;
                case "3":
                    blockUnblockUsers();
                    break;
                case "4":
                    activityStatus();
                    break;
                case "5":
                    dataPrivacy();
                    break;
                case "6":
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
                    pressEnterToContinue();
            }
        }
    }

    private static void toggle2FA() {
        System.out.println(GREEN + "\n═══ TWO-FACTOR AUTHENTICATION ═══" + RESET);
        System.out.println(YELLOW + "Status: " + GREEN + "Disabled" + RESET + " (Demo mode)");
        System.out.println();
        System.out.println("1. Enable 2FA");
        System.out.println("2. Disable 2FA");
        System.out.println("3. Back");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                System.out.println(GREEN + " 2FA enabled! (Demo )" + RESET);
                break;
            case "2":
                System.out.println(YELLOW + " 2FA disabled! (Demo )" + RESET);
                break;
        }

        if (!choice.equals("3")) {
            pressEnterToContinue();
        }
    }

    private static void profileVisibility() {
        System.out.println(GREEN + "\n═══ PROFILE VISIBILITY ═══" + RESET);
        System.out.println("Current Setting: " + CYAN + "Public" + RESET);
        System.out.println();
        System.out.println("1. Public - Anyone can see your profile");
        System.out.println("2. Friends Only - Only followers can see details");
        System.out.println("3. Private - Only you can see your profile");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choiceInput = sc.nextLine().trim();

        int choice = -1;
        try {
            choice = Integer.parseInt(choiceInput);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        String[] options = {"", "Public", "Friends Only", "Private"};

        if (choice >= 1 && choice <= 3) {
            System.out.println(GREEN + "✓ Profile visibility set to: " + options[choice] + RESET);
        } else {
            System.out.println(RED + "✗ Invalid choice! Please select 1, 2, or 3." + RESET);
        }

        pressEnterToContinue();
    }

    private static void blockUnblockUsers() {
        System.out.println(GREEN + "\n═══ BLOCKED USERS ═══" + RESET);
        System.out.println("Currently blocked users: " + CYAN + "None" + RESET);
        System.out.println();
        System.out.println("1. Block a user");
        System.out.println("2. Unblock a user");
        System.out.println("3. View blocked list");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter username to block: ");
                String userBlock = sc.nextLine();
                System.out.println(GREEN + " User '" + userBlock + "' blocked! (Demo mode)" + RESET);
                break;
            case "2":
                System.out.println(YELLOW + "No users currently blocked." + RESET);
                break;
            case "3":
                System.out.println(CYAN + "Blocked users: None" + RESET);
                break;
        }

        pressEnterToContinue();
    }

    private static void activityStatus() {
        System.out.println(GREEN + "\n═══ ACTIVITY STATUS ═══" + RESET);
        System.out.println("Current Setting: " + GREEN + "Visible" + RESET);
        System.out.println();
        System.out.println("1. Show activity status to everyone");
        System.out.println("2. Show activity status to friends only");
        System.out.println("3. Hide activity status completely");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choiceInput = sc.nextLine().trim();

        int choice = -1;
        try {
            choice = Integer.parseInt(choiceInput);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        String[] options = {"", "Everyone", "Friends Only", "Hidden"};
        if (choice >= 1 && choice <= 3) {
            System.out.println(GREEN + " Activity status set to: " + options[choice] + RESET);
        } else {
            System.out.println(RED + " Invalid choice! Please select 1, 2, or 3." + RESET);
        }

        pressEnterToContinue();
    }

    private static void dataPrivacy() {
        System.out.println(GREEN + "\n═══ DATA PRIVACY ═══" + RESET);
        System.out.println("1. Download my data");
        System.out.println("2. Delete my data");
        System.out.println("3. Data usage analytics");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                System.out.println(GREEN + "Data export initiated! (Demo )" + RESET);
                break;
            case "2":
                System.out.println(RED + "This will permanently delete your data! (Demo )" + RESET);
                break;
            case "3":
                System.out.println(CYAN + "Data usage: 2.5MB storage used (Demo)" + RESET);
                break;
        }

        pressEnterToContinue();
    }

    static void appearanceAndThemes() {
        clearScreen();
        System.out.println(GREEN + BOLD + "═══ APPEARANCE & THEMES ═══" + RESET);
        System.out.println();

        System.out.println("Current Theme: " + CYAN + "Light Mode" + RESET);
        System.out.println();
        System.out.println(CYAN + "[1] " + RESET + "Dark Mode");
        System.out.println(CYAN + "[2] " + RESET + "Light Mode");
        System.out.println(CYAN + "[3] " + RESET + "Auto (System)");
        System.out.println(CYAN + "[4] " + RESET + "Color Customization");
        System.out.println(CYAN + "[5] " + RESET + "Font Settings");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                System.out.println(GREEN + " Switched to Dark Mode! (Demo)" + RESET);
                break;
            case "2":
                System.out.println(GREEN + " Switched to Light Mode! (Demo)" + RESET);
                break;
            case "3":
                System.out.println(GREEN + " Theme set to Auto! (Demo)" + RESET);
                break;
            case "4":
                System.out.println(CYAN + "Color themes: Blue, Green, Purple, Red (Demo)" + RESET);
                break;
            case "5":
                System.out.println(CYAN + "Font sizes: Small, Medium, Large (Demo)" + RESET);
                break;
        }

        pressEnterToContinue();
    }

    static void notificationSettings() {
        clearScreen();
        System.out.println(GREEN + BOLD + "═══ NOTIFICATION SETTINGS ═══" + RESET);
        System.out.println();

        System.out.println("Current Settings:");
        System.out.println("  Push Notifications: " + GREEN + "Enabled" + RESET);
        System.out.println("  Email Alerts: " + YELLOW + "Disabled" + RESET);
        System.out.println("  Sound: " + GREEN + "Enabled" + RESET);
        System.out.println();

        System.out.println(CYAN + "[1] " + RESET + "Toggle Push Notifications");
        System.out.println(CYAN + "[2] " + RESET + "Toggle Email Alerts");
        System.out.println(CYAN + "[3] " + RESET + "Toggle Sound Notifications");
        System.out.println(CYAN + "[4] " + RESET + "Custom Activity Reminders");
        System.out.println(CYAN + "[5] " + RESET + "Quiet Hours Settings");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                System.out.println(GREEN + " Push notifications toggled! (Demo)" + RESET);
                break;
            case "2":
                System.out.println(GREEN + " Email alerts toggled! (Demo)" + RESET);
                break;
            case "3":
                System.out.println(GREEN + " Sound notifications toggled! (Demo)" + RESET);
                break;
            case "4":
                System.out.println(CYAN + "Reminder options: Daily, Weekly, Custom (Demo)" + RESET);
                break;
            case "5":
                System.out.println(CYAN + "Quiet hours: 10 PM - 8 AM (Demo)" + RESET);
                break;
        }

        pressEnterToContinue();
    }

    static void appPreferences() {
        clearScreen();
        System.out.println(GREEN + BOLD + "═══ APP PREFERENCES ═══" + RESET);
        System.out.println();

        System.out.println("Storage Used: " + CYAN + "2.5MB" + RESET);
        System.out.println("Cache Size: " + YELLOW + "0.8MB" + RESET);
        System.out.println();

        System.out.println(CYAN + "[1] " + RESET + "Clear Cache & Temp Files");
        System.out.println(CYAN + "[2] " + RESET + "Language Settings");
        System.out.println(CYAN + "[3] " + RESET + "Auto-save Settings");
        System.out.println(CYAN + "[4] " + RESET + "Backup & Restore");
        System.out.println(CYAN + "[5] " + RESET + "Performance Settings");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                System.out.println(GREEN + " Cache cleared! Freed 0.8MB (Demo)" + RESET);
                break;
            case "2":
                System.out.println(CYAN + "Available languages: English, Spanish, French (Demo)" + RESET);
                break;
            case "3":
                System.out.println(GREEN + " Auto-save enabled! (Demo)" + RESET);
                break;
            case "4":
                System.out.println(CYAN + "Backup options: Local, Cloud (Demo)" + RESET);
                break;
            case "5":
                System.out.println(CYAN + "Performance: Optimized for speed (Demo)" + RESET);
                break;
        }

        pressEnterToContinue();
    }

    static void aboutAndLegal() {
        clearScreen();
        System.out.println(GREEN + BOLD + "═══ ABOUT & LEGAL ═══" + RESET);
        System.out.println();

        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║               TextItCLI                   ║");
        System.out.println("║         Console Blogging Platform         ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║  Version: " + CYAN + "2.0.0" + RESET + "   ║");
        System.out.println("║  Build: " + CYAN + "2025.01.14" + RESET + "║");
        System.out.println("║  Developer: " + CYAN + "TextIt Corporation" + RESET + "         ║");
        System.out.println("║  License: " + CYAN + "TCEL-1.0" + RESET + "                     ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        System.out.println();

        System.out.println(CYAN + "[1] " + RESET + "Terms of Service");
        System.out.println(CYAN + "[2] " + RESET + "Privacy Policy");
        System.out.println(CYAN + "[3] " + RESET + "Contact Developer Support");
        System.out.println(CYAN + "[4] " + RESET + "Check for Updates");
        System.out.println(CYAN + "[5] " + RESET + "Open Source Licenses");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                System.out.println(CYAN + "Terms of Service: Please visit www.TextITCorporation.com/terms" + RESET);
                break;
            case "2":
                System.out.println(CYAN + "Privacy Policy: Please visit www.TextITCorporation.com/privacy" + RESET);
                break;
            case "3":
                System.out.println(CYAN + "Support: support@TextItCorporation.com | +91 99999-88888" + RESET);
                break;
            case "4":
                System.out.println(GREEN + " You're running the latest version!" + RESET);
                break;
            case "5":
                System.out.println(CYAN + "PostgreSQL, Java OpenJDK, Maven dependencies" + RESET);
                break;
        }

        pressEnterToContinue();
    }

    static void sessionOptions() {
        while (true) {
            clearScreen();
            System.out.println(GREEN + BOLD + "═══ SESSION OPTIONS ═══" + RESET);
            System.out.println();

            String username = userdata.getUserName(userID);
            System.out.println("Current Session: " + CYAN + username + RESET);
            System.out.println();

            System.out.println(CYAN + "[1] " + RESET + "Switch Account");
            System.out.println(CYAN + "[2] " + YELLOW + "Logout");
            System.out.println(CYAN + "[3] " + RED + "Delete My Account");
            System.out.println(CYAN + "[4] " + RESET + "Back to Main Settings");
            System.out.println();

            System.out.print(PURPLE + "Enter your choice: " + RESET);
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    switchAccount();
                    break;
                case "2":
                    logout();
                    return;
                case "3":
                    deleteAccount();
                    break;
                case "4":
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
                    pressEnterToContinue();
            }
        }
    }

    private static void switchAccount() {
        System.out.println(GREEN + "\n═══ SWITCH ACCOUNT ═══" + RESET);
        System.out.println(YELLOW + "Available accounts: (Demo)" + RESET);
        System.out.println("1. GM_VRAJ");
        System.out.println("2. Dhruv_HARAMI");
        System.out.println("3. Add new account");

        System.out.print(PURPLE + "Enter your choice: " + RESET);
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                userID = 1;
                System.out.println(GREEN + " Switched to GM_VRAJ" + RESET);
                break;
            case "2":
                userID = 2;
                System.out.println(GREEN + " Switched to Dhruv_HARAMI" + RESET);
                break;
            case "3":
                System.out.println(CYAN + "Redirecting to signup... (Demo)" + RESET);
                break;
        }

        pressEnterToContinue();
    }

    private static void logout() {
        System.out.println(YELLOW + "\n Logging out..." + RESET);
        System.out.println(GREEN + " Successfully logged out!" + RESET);
        System.out.println(CYAN + "Thank you for using TextIt!" + RESET);
        pressEnterToContinue();
    }

    private static void deleteAccount() {
        System.out.println(RED + "\n DELETE ACCOUNT " + RESET);
        System.out.println(YELLOW + "This action cannot be undone!" + RESET);
        System.out.println();
        System.out.print("Type 'DELETE' to confirm account deletion: ");
        String confirmation = sc.nextLine();

        if ("DELETE".equals(confirmation)) {
            System.out.println(RED + "✓ Account deletion confirmed! (Demo)" + RESET);
        } else {
            System.out.println(GREEN + "✓ Account deletion cancelled." + RESET);
        }

        pressEnterToContinue();
    }

    private static void clearScreen() {
        for (int i = 0; i < 4; i++) {
            System.out.println();
        }
    }

    private static void pressEnterToContinue() {
        System.out.print(YELLOW + "\nPress Enter to continue..." + RESET);
        sc.nextLine();
    }
}
