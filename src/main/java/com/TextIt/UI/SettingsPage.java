package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.security.Hashing;
import com.TextIt.security.OTPHandler;
import com.TextIt.service.pages.LoginAuth;
import com.TextIt.service.pages.SignUpAuth;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;

public class SettingsPage {

    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdata = db.new UserData();
    private static final DataBase.Profile profile = db.new Profile();
    static Scanner sc = new Scanner(System.in);
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
            System.out.println(CYAN + "[2] " + RESET + "Session Options");
            System.out.println(CYAN + "[3] " + RESET + "About & Legal");
            System.out.println(CYAN + "[4] " + RED + "Exit Settings");
            System.out.println();

            System.out.print(PURPLE + "Enter your choice: " + RESET);

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    accountManagement();
                    break;
                case "2":
                    sessionOptions();
                    break;
                case "3":
                    aboutAndLegal();
                    break;
                case "4":
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
        System.out.println(border + usernameLabel + username + " ".repeat(spaceLeftForContent - (usernameLength + username.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Real Name
        System.out.println(border + realNameLabel + realName + " ".repeat(spaceLeftForContent - (realNameLength + realName.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Email
        System.out.println(border + emailLabel + email + " ".repeat(spaceLeftForContent - (emailLength + email.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Mobile
        System.out.println(border + mobileLabel + mobile + " ".repeat(spaceLeftForContent - (mobileLength + mobile.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Share Code
        System.out.println(border + shareCodeLabel + shareCode + " ".repeat(spaceLeftForContent - (shareCodeLength + shareCode.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        System.out.println("=".repeat(boxLength));
    }


    private static void changePassword() {

        LoginAuth la = new LoginAuth();
        SignUpAuth signup = new SignUpAuth();
        Scanner scanner = new Scanner(System.in);
        String newPassword = "";
        String conformPassword = "";

        System.out.println(GREEN + "\n═══ CHANGE PASSWORD ═══" + RESET);

        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();

        if (la.verifyPassword(currentPassword)) {
            do {
                System.out.print(YELLOW + "Enter your new password: " + RESET);
                newPassword = scanner.nextLine();
                System.out.print(YELLOW + "Enter conformed password: " + RESET);
                conformPassword = scanner.nextLine();
                if (!newPassword.equals(conformPassword)) {
                    System.out.println("New password and confirm password must be the same.");
                }

            } while (!(signup.verifyPassword(newPassword) && newPassword.equals(conformPassword)));

            String hashedPassword = Hashing.generateHashCode(newPassword);

            if (userdata.updatePassword(userID, hashedPassword)) {
                System.out.println(GREEN + "\nPassword updated successfully" + RESET);
                CommonMethods.pressEnterToContinue();
            }
        }

        pressEnterToContinue();
    }

    private static void updateEmail() {

        SignUpAuth newUser = new SignUpAuth();
        Scanner scanner = new Scanner(System.in);
        String email = "";
        String generatedOtp = "";


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
            }
        }
        if (!OTPHandler.verifyOTP(generatedOtp, scanner)) {                // verify if otp entered by user is right or wrong
            return;
        }

        if (userdata.updateEmail(userID, email)) {
            System.out.println(GREEN + "Email updated successfully" + RESET);
            CommonMethods.pressEnterToContinue();
        }

    }

    private static void updatePhoneNumber() {
        System.out.println(GREEN + "\n═══ UPDATE PHONE NUMBER ═══" + RESET);

        SignUpAuth newUser = new SignUpAuth();
        Scanner scanner = new Scanner(System.in);
        String phoneNumber = "";

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
        System.out.println(border + usernameLabel + username + " ".repeat(spaceLeftForContent - (usernameLength + username.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Real Name
        System.out.println(border + realNameLabel + realName + " ".repeat(spaceLeftForContent - (realNameLength + realName.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Email
        System.out.println(border + emailLabel + email + " ".repeat(spaceLeftForContent - (emailLength + email.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Mobile
        System.out.println(border + mobileLabel + mobile + " ".repeat(spaceLeftForContent - (mobileLength + mobile.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Bio
        System.out.println(border + bioLabel + " ".repeat(spaceLeftForContent - bioLabelLength) + border);
        CommonMethods.paragraphDisplay(bio, border, spaceLeftForContent);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Member Since
        System.out.println(border + memberSinceLabel + memberSince + " ".repeat(spaceLeftForContent - (memberSinceLength + memberSince.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Share Code
        System.out.println(border + shareCodeLabel + shareCode + " ".repeat(spaceLeftForContent - (shareCodeLength + shareCode.length())) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        System.out.println("=".repeat(boxLength));

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

            System.out.println(CYAN + "[1] " + YELLOW + "Logout");
            System.out.println(CYAN + "[2] " + RED + "Delete My Account");
            System.out.println(CYAN + "[3] " + RESET + "Back to Main Settings");
            System.out.println();

            System.out.print(PURPLE + "Enter your choice: " + RESET);
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    logout();
                    return;
                case "2":
                    deleteAccount();
                    break;
                case "3":
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
                    pressEnterToContinue();
            }
        }
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
}
