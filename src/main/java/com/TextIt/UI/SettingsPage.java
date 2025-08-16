package com.TextIt.UI;

import com.TextIt.database.DataBase;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;

public class SettingsPage {

    static Scanner sc = new Scanner(System.in);
    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdata = db.new UserData();
    private static final DataBase.AccountManager accountManager = db.new AccountManager();
    private static int UserId;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(YELLOW + "No user ID provided. Using demo user ID 1." + RESET);
            UserId = 1;
        } else {
            try {
                UserId = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println(RED + "Invalid user ID: " + args[0] + ". Using demo user ID 1." + RESET);
                UserId = 1;
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
        String username = userdata.getUserName(UserId);
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
            displayAccountInfo();

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
                    editProfileInfo();
                    break;
                case "5":
                    viewAccountDetails();
                    break;
                case "6":
                    return;
                default:
                    System.out.println(RED + "Invalid option. Please try again." + RESET);
                    pressEnterToContinue();
            }
        }
    }

    private static void displayAccountInfo() {
        String username = userdata.getUserName(UserId);
        String realName = userdata.getRealName(UserId);
        String email = accountManager.getCurrentEmail(UserId);
        String mobile = userdata.getMobileNumber(UserId);
        String shareCode = userdata.getUserShareCode(UserId);

        System.out.println(YELLOW + "Current Account: " + RESET);
        System.out.println("  Username: " + CYAN + getValueOrDefault(username, "No username set") + RESET);
        System.out.println("  Name: " + CYAN + getValueOrDefault(realName, "Name not provided") + RESET);
        System.out.println("  Email: " + CYAN + getValueOrDefault(email, "Email not set") + RESET);
        System.out.println("  Mobile: " + CYAN + getValueOrDefault(mobile, "Phone not added") + RESET);
        System.out.println("  Share Code: " + CYAN + getValueOrDefault(shareCode, "Code not generated") + RESET);
        System.out.println();
    }

    private static void changePassword() {
        System.out.println(GREEN + "\n═══ CHANGE PASSWORD ═══" + RESET);

        System.out.print("Enter current password: ");
        String currentPassword = sc.nextLine();

        if (currentPassword.trim().isEmpty()) {
            System.out.println(RED + "Current password cannot be empty!" + RESET);
            pressEnterToContinue();
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        System.out.print("Confirm new password: ");
        String confirmPassword = sc.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println(RED + " Passwords don't match!" + RESET);
        } else if (newPassword.length() < 6) {
            System.out.println(RED + " Password must be at least 6 characters!" + RESET);
        } else {
            boolean success = accountManager.updatePassword(UserId, currentPassword, newPassword);
            if (success) {
                System.out.println(GREEN + " Password changed successfully!" + RESET);
            } else {
                System.out.println(RED + "Failed to change password. Please check your current password." + RESET);
            }
        }

        pressEnterToContinue();
    }

    private static void updateEmail() {
        System.out.println(GREEN + "\n═══ UPDATE EMAIL ═══" + RESET);

        String currentEmail = accountManager.getCurrentEmail(UserId);
        System.out.println("Current email: " + CYAN + getValueOrDefault(currentEmail, "No email find") + RESET);
        System.out.println();

        System.out.print("Enter new email address: ");
        String newEmail = sc.nextLine().trim();

        if (newEmail.isEmpty()) {
            System.out.println(YELLOW + "No changes made." + RESET);
        } else if (!newEmail.contains("@") || !newEmail.contains(".") || newEmail.length() < 5) {
            System.out.println(RED + " Invalid email format!" + RESET);
        } else {
            boolean success = accountManager.updateEmail(UserId, newEmail);
            if (success) {
                System.out.println(GREEN + " Email updated to: " + newEmail + RESET);
            } else {
                System.out.println(RED + "Failed to update email. Email may already be in use." + RESET);
            }
        }

        pressEnterToContinue();
    }

    private static void updatePhoneNumber() {
        System.out.println(GREEN + "\n═══ UPDATE PHONE NUMBER ═══" + RESET);

        String currentMobile = userdata.getMobileNumber(UserId);
        System.out.println("Current mobile: " + CYAN + getValueOrDefault(currentMobile, "[No phone number added]") + RESET);
        System.out.println();

        System.out.print("Enter new phone number (10 digits, starting with 6-9): ");
        String newPhone = sc.nextLine().trim();

        if (newPhone.isEmpty()) {
            System.out.println(YELLOW + "No changes made." + RESET);
        } else {
            boolean isValid = true;

            if (newPhone.length() != 10) {
                isValid = false;
            } else if (newPhone.charAt(0) < '6' || newPhone.charAt(0) > '9') {
                isValid = false;
            } else {
                for (int i = 0; i < newPhone.length(); i++) {
                    if (!Character.isDigit(newPhone.charAt(i))) {
                        isValid = false;
                        break;
                    }
                }
            }

            if (!isValid) {
                System.out.println(RED + "Invalid phone number! Must be 10 digits, start with 6-9, and contain only numbers." + RESET);
            } else {
                boolean success = accountManager.updateMobileNumber(UserId, newPhone);
                if (success) {
                    System.out.println(GREEN + " Phone number updated to: " + newPhone + RESET);
                } else {
                    System.out.println(RED + " Failed to update phone number. Number may already be in use." + RESET);
                }
            }
        }

        pressEnterToContinue();
    }

    private static void editProfileInfo() {
        System.out.println(GREEN + "\n═══ EDIT PROFILE INFORMATION ═══" + RESET);

        String currentFirstName = userdata.getFirstName(UserId);
        String currentLastName = userdata.getLastName(UserId);
        String currentBio = userdata.getBio(UserId);

        System.out.println("Current Information:");
        System.out.println("  First Name: " + CYAN + getValueOrDefault(currentFirstName, "First name not provided") + RESET);
        System.out.println("  Last Name: " + CYAN + getValueOrDefault(currentLastName, "Last name not provided") + RESET);
        System.out.println("  Bio: " + CYAN + getValueOrDefault(currentBio, "No bio written yet") + RESET);
        System.out.println();

        System.out.print("Enter new first name (or press Enter to keep current): ");
        String newFirstName = sc.nextLine();

        System.out.print("Enter new last name (or press Enter to keep current): ");
        String newLastName = sc.nextLine();

        System.out.print("Enter new bio (or press Enter to keep current): ");
        String newBio = sc.nextLine();

        boolean success = accountManager.updateProfileInfo(UserId,
                newFirstName.trim().isEmpty() ? null : newFirstName,
                newLastName.trim().isEmpty() ? null : newLastName,
                newBio.trim().isEmpty() ? null : newBio);

        if (success) {
            System.out.println(GREEN + " Profile information updated successfully!" + RESET);
        } else {
            System.out.println(YELLOW + "No changes were made to your profile." + RESET);
        }

        pressEnterToContinue();
    }

    private static void viewAccountDetails() {
        System.out.println(GREEN + "\n═══ ACCOUNT DETAILS ═══" + RESET);

        String username = userdata.getUserName(UserId);
        String realName = userdata.getRealName(UserId);
        String bio = userdata.getBio(UserId);
        String mobile = userdata.getMobileNumber(UserId);
        String email = accountManager.getCurrentEmail(UserId);
        int memberSince = userdata.getMemberSince(UserId);
        String shareCode = userdata.getUserShareCode(UserId);

        username = getValueOrDefault(username, "Username not set");
        realName = getValueOrDefault(realName, "Name not provided");
        bio = getValueOrDefault(bio, "No bio added yet");
        mobile = getValueOrDefault(mobile, "Phone not added");
        email = getValueOrDefault(email, "Email not configured");
        shareCode = getValueOrDefault(shareCode, "Share code not generated");

        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│                ACCOUNT INFO                 │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ Username: " + CYAN + username + RESET + " ".repeat(Math.max(1, 32 - username.length())) + "│");
        System.out.println("│ Real Name: " + CYAN + realName + RESET + " ".repeat(Math.max(1, 31 - realName.length())) + "│");
        System.out.println("│ Email: " + CYAN + email + RESET + " ".repeat(Math.max(1, 36 - email.length())) + "│");
        String displayBio = bio.length() > 30 ? bio.substring(0, 30) + "..." : bio;
        System.out.println("│ Bio: " + CYAN + displayBio + RESET + " ".repeat(Math.max(1, 37 - displayBio.length())) + "│");
        System.out.println("│ Mobile: " + CYAN + mobile + RESET + " ".repeat(Math.max(1, 34 - mobile.length())) + "│");
        String memberText = memberSince > 0 ? memberSince + " years" : "Recently joined";
        System.out.println("│ Member Since: " + CYAN + memberText + RESET + " ".repeat(Math.max(1, 25 - memberText.length())) + "│");
        System.out.println("│ Share Code: " + CYAN + shareCode + RESET + " ".repeat(Math.max(1, 30 - shareCode.length())) + "│");
        System.out.println("└─────────────────────────────────────────────┘");

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

            String username = userdata.getUserName(UserId);
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
                UserId = 1;
                System.out.println(GREEN + " Switched to GM_VRAJ" + RESET);
                break;
            case "2":
                UserId = 2;
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
    private static String getValueOrDefault(String value, String defaultValue) {
        if (value == null || value.trim().isEmpty() || "null".equalsIgnoreCase(value)) {
            return defaultValue;
        }
        return value;
    }
}
