package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.security.Hashing;
import com.TextIt.security.OTPHandler;
import com.TextIt.service.pages.LoginAuth;
import com.TextIt.service.pages.SignUpAuth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.nio.file.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

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
        String pageDescription = "@" + userdata.getUserName(userId);

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

    private static String readFileContent(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                return "Document not found. Please check the documentation directory.";
            }
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    private static void displayHeader(String title) {
        int width = 60;
        String line = "═".repeat(width);
        String paddedTitle = " ".repeat((width - title.length() - 2) / 2) + title + " ".repeat((width - title.length() - 1) / 2);
        
        System.out.println("╔" + line + "╗");
        System.out.println("║" + CYAN + BOLD + paddedTitle + RESET + "║");
        System.out.println("╚" + line + "╝\n");
    }

    private static void displayDocument(String title, String filePath) {
        try {
            // Create a temporary file to store the content
            Path tempFile = Files.createTempFile("TextIt_", ".txt");
            String content = readFileContent(filePath);
            Files.writeString(tempFile, content, StandardOpenOption.WRITE);
            
            // Open the file with the default system editor
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;
            
            if (os.contains("win")) {
                // Windows
                pb = new ProcessBuilder("notepad.exe", tempFile.toString());
            } else if (os.contains("mac")) {
                // macOS
                pb = new ProcessBuilder("open", "-t", tempFile.toString());
            } else {
                // Linux/Unix
                pb = new ProcessBuilder("xdg-open", tempFile.toString());
            }
            
            // Start the process and wait for it to finish
            Process process = pb.start();
            
            // Wait for the user to close the editor
            System.out.println(YELLOW + "Opening " + title + " in your default text editor..." + RESET);
            System.out.println("Please close the text editor when you're done viewing.");
            process.waitFor();
            
            // Clean up the temporary file
            Files.deleteIfExists(tempFile);
            
        } catch (IOException | InterruptedException e) {
            System.out.println(RED + "Error opening document: " + e.getMessage() + RESET);
            System.out.println(YELLOW + "Falling back to console display..." + RESET);
            
            // Fallback to console display if opening the editor fails
            try {
                String content = readFileContent(filePath);
                clearScreen();
                displayHeader(title);
                
                // Simple word wrap for console output
                int maxWidth = 78;
                String[] words = content.split("\\s+");
                StringBuilder line = new StringBuilder();
                
                for (String word : words) {
                    if (line.length() + word.length() > maxWidth) {
                        System.out.println(line.toString().trim());
                        line = new StringBuilder();
                    }
                    line.append(word).append(" ");
                }
                if (line.length() > 0) {
                    System.out.println(line.toString().trim());
                }
                
                System.out.println("\n" + CYAN + "Press Enter to return to the previous menu..." + RESET);
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println(RED + "Error displaying document: " + ex.getMessage() + RESET);
                pressEnterToContinue();
            }
        }
    }

    private static boolean isGitInstalled() {
        try {
            Process process = new ProcessBuilder("git", "--version").start();
            return process.waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static void animateProgress(String message, int seconds, int delay) throws InterruptedException {
        String[] spinner = new String[] { "⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏" };
        long startTime = System.currentTimeMillis();
        int counter = 0;
        
        while (System.currentTimeMillis() - startTime < seconds * 1000) {
            System.out.print("\r" + CYAN + spinner[counter % spinner.length] + " " + RESET + message + " ");
            Thread.sleep(delay);
            counter++;
        }
        System.out.print("\r" + " ".repeat(message.length() + 10) + "\r");
    }

    private static void printStatus(String message, boolean success) {
        System.out.print("\r" + (success ? GREEN + "✓ " : RED + "✗ ") + RESET + message + (success ? " [DONE]" : " [FAILED]"));
        if (!success) {
            System.out.println();
        }
    }

    private static void aboutAndLegal() {
        while (true) {
            clearScreen();
            displayHeader("ABOUT & LEGAL");
            
            // System Information
            System.out.println(BOLD + "TextItCLI - Console Blogging Platform\n" + RESET);
            System.out.println(CYAN + "Version: " + RESET + "3.0.0 (2025.08.17)");
            System.out.println(CYAN + "Java Version: " + RESET + System.getProperty("java.version"));
            System.out.println(CYAN + "OS: " + RESET + System.getProperty("os.name") + " " + System.getProperty("os.version"));
            System.out.println(CYAN + "Developer: " + RESET + "TextIt Corporation\n");
            
            // Main Menu
            System.out.println(BOLD + "DOCUMENTATION" + RESET);
            System.out.println(CYAN + "[1] " + RESET + "Terms of Service");
            System.out.println(CYAN + "[2] " + RESET + "Privacy Policy");
            System.out.println(CYAN + "[3] " + RESET + "Code of Conduct");
            System.out.println(CYAN + "[4] " + RESET + "Contributing Guidelines");
            
            System.out.println("\n" + BOLD + "SUPPORT" + RESET);
            System.out.println(CYAN + "[5] " + RESET + "Contact Support");
            System.out.println(CYAN + "[6] " + RESET + "Security Information");
            System.out.println(CYAN + "[7] " + RESET + "Check for Updates");
            
            System.out.println("\n" + BOLD + "LEGAL" + RESET);
            System.out.println(CYAN + "[8] " + RESET + "Open Source Licenses");
            System.out.println(CYAN + "[9] " + RESET + "Trademark Information");
            System.out.println(CYAN + "[0] " + RESET + "Back to Settings\n");

            System.out.print(PURPLE + "Enter your choice: " + RESET);
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    displayDocument("TERMS OF SERVICE", "docs/TERMS_OF_SERVICE.md");
                    break;
                case "2":
                    displayDocument("PRIVACY POLICY", "docs/PRIVACY_POLICY.md");
                    break;
                case "3":
                    displayDocument("CODE OF CONDUCT", "docs/CODE_OF_CONDUCT.md");
                    break;
                case "4":
                    displayDocument("CONTRIBUTING GUIDELINES", "docs/CONTRIBUTING.md");
                    break;
                case "5":
                    // Create a temporary file for the support information
                    try {
                        Path tempFile = Files.createTempFile("TextIt_Support", ".txt");
                        String supportInfo = "For support, please contact us at:\n\n" +
                            "Email: support@textit.com\n" +
                            "Website: https://www.textit.com/support\n\n" +
                            "Our support team is available 24/7 to assist you with any questions or issues you may have.";
                        Files.writeString(tempFile, supportInfo, StandardOpenOption.WRITE);
                        displayDocument("CONTACT SUPPORT", tempFile.toString());
                        Files.deleteIfExists(tempFile);
                    } catch (IOException e) {
                        System.out.println(RED + "Error displaying support information: " + e.getMessage() + RESET);
                        pressEnterToContinue();
                    }
                    break;
                case "6":
                    displayDocument("SECURITY INFORMATION", "docs/SECURITY.md");
                    break;
                case "7":
                    try {
                        clearScreen();
                        displayHeader("CHECK FOR UPDATES");
                        
                        // Initial check
                        animateProgress("Initializing update check...", 1, 100);
                        
                        // Check if Git is installed
                        System.out.print("\n" + CYAN + "🔍 " + RESET + "Checking Git installation");
                        if (!isGitInstalled()) {
                            printStatus("Git is not installed or not in system PATH", false);
                            System.out.println(YELLOW + "\n⚠ Git is required for automatic updates." + RESET);
                            System.out.println("Please install Git from https://git-scm.com/");
                            pressEnterToContinue();
                            break;
                        }
                        printStatus("Git is installed", true);
                        
                        // Fetch updates
                        System.out.print("\n" + CYAN + "🔄 " + RESET + "Fetching latest changes");
                        Process fetchProcess = new ProcessBuilder("git", "fetch", "origin", "main")
                            .directory(new File(System.getProperty("user.dir")))
                            .redirectErrorStream(true)
                            .start();
                        
                        // Show progress while waiting
                        new Thread(() -> {
                            try {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(fetchProcess.getInputStream()));
                                // Consume the output to prevent process from hanging
                                while (reader.readLine() != null) {}
                            } catch (IOException e) {
                                // Ignore
                            }
                        }).start();
                        
                        int fetchExitCode = fetchProcess.waitFor();
                        
                        if (fetchExitCode != 0) {
                            printStatus("Failed to fetch updates", false);
                            System.out.println(YELLOW + "\n⚠ Could not connect to update server." + RESET);
                            System.out.println("Please check your internet connection and try again.");
                            pressEnterToContinue();
                            break;
                        }
                        printStatus("Fetched latest changes", true);
                        
                        // Check for updates
                        System.out.print("\n" + CYAN + "📡 " + RESET + "Checking for available updates");
                        Process statusProcess = new ProcessBuilder("git", "status", "-uno")
                            .directory(new File(System.getProperty("user.dir")))
                            .redirectErrorStream(true)
                            .start();
                        
                        String statusOutput = new String(statusProcess.getInputStream().readAllBytes());
                        
                        if (statusOutput.contains("Your branch is behind")) {
                            printStatus("Updates available", true);
                            
                            System.out.println("\n" + YELLOW + "✨ New updates are available for TextItCLI!" + RESET);
                            System.out.print("\n" + PURPLE + "Do you want to update now? (y/n): " + RESET);
                            String confirm = sc.nextLine().trim().toLowerCase();
                            
                            if (confirm.equals("y") || confirm.equals("yes")) {
                                System.out.println("\n" + CYAN + "🔄 Updating TextItCLI...");
                                System.out.println(YELLOW + "$ git pull origin main" + RESET);
                                
                                Process pullProcess = new ProcessBuilder("git", "pull", "origin", "main")
                                    .directory(new File(System.getProperty("user.dir")))
                                    .redirectErrorStream(true)
                                    .start();
                                
                                // Show pull output in real-time
                                try (BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(pullProcess.getInputStream()))) {
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        System.out.println("  " + line);
                                    }
                                }
                                
                                int pullExitCode = pullProcess.waitFor();
                                
                                if (pullExitCode == 0) {
                                    System.out.println("\n" + GREEN + "✓ Update successful!" + RESET);
                                    System.out.println(YELLOW + "\nPlease restart TextItCLI to apply the updates." + RESET);
                                } else {
                                    System.out.println("\n" + RED + "✗ Update failed. Please try again later." + RESET);
                                }
                            } else {
                                System.out.println("\n" + YELLOW + "Update cancelled." + RESET);
                            }
                        } else {
                            printStatus("You're up to date", true);
                            System.out.println("\n" + GREEN + "✓ You are using the latest version of TextItCLI!" + RESET);
                        }
                        
                        System.out.println("\n" + CYAN + "Last checked: " + 
                            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + 
                            RESET);
                        pressEnterToContinue();
                        
                    } catch (Exception e) {
                        System.out.println(RED + "\n\nError checking for updates: " + e.getMessage() + RESET);
                        pressEnterToContinue();
                    }
                    break;
                case "8":
                    try {
                        Path tempFile = Files.createTempFile("TextIt_Licenses", ".txt");
                        String licenses = "TextItCLI uses the following open source components:\n\n" +
                            "1. Apache Commons Lang - Apache License 2.0\n" +
                            "2. SQLite JDBC - MIT License\n" +
                            "3. JLine - BSD License\n\n" +
                            "For detailed license information, please visit:\n" +
                            "https://www.textit.com/licenses";
                        Files.writeString(tempFile, licenses, StandardOpenOption.WRITE);
                        displayDocument("OPEN SOURCE LICENSES", tempFile.toString());
                        Files.deleteIfExists(tempFile);
                    } catch (IOException e) {
                        System.out.println(RED + "Error displaying license information: " + e.getMessage() + RESET);
                        pressEnterToContinue();
                    }
                    break;
                case "9":
                    displayDocument("TRADEMARK INFORMATION", "docs/TRADEMARK.md");
                    break;
                case "0":
                    return; // Return to settings menu
                default:
                    System.out.println(RED + "\nInvalid choice. Please try again." + RESET);
                    System.out.println("Press Enter to continue...");
                    sc.nextLine();
                    break;
            }
        }
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
        File file = new File("last_session.txt");
        if(file.delete()){
            System.out.println(RED + "Last session has been deleted" + RESET);
        }else {
            System.out.println(RED + "Last session has not been deleted" + RESET);
        }
        System.exit(0);
    }

    private static void deleteAccount() {
        DataBase dataBase = new DataBase();
        System.out.println(RED + "\n DELETE ACCOUNT " + RESET);
        System.out.println(YELLOW + "This action cannot be undone!" + RESET);
        System.out.println();
        System.out.print("Type 'DELETE' to confirm account deletion: ");
        String confirmation = sc.nextLine();

        if ("DELETE".equals(confirmation)) {
            try(Connection conn = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsername(), dataBase.getPassword())){

                PreparedStatement ps1 = conn.prepareStatement("DELETE FROM notifications WHERE by_user_id = ?");
                ps1.setInt(1, userID);
                ps1.executeUpdate();

                PreparedStatement ps2 = conn.prepareStatement("DELETE FROM posts WHERE userid = ?");
                ps2.setInt(1, userID);
                ps2.executeUpdate();

                PreparedStatement ps3 = conn.prepareStatement("DELETE FROM comments WHERE userid = ?");
                ps3.setInt(1, userID);
                ps3.executeUpdate();

                PreparedStatement ps4 = conn.prepareStatement("DELETE FROM likes WHERE userid = ?");
                ps4.setInt(1, userID);
                ps4.executeUpdate();

                // finally delete the user
                PreparedStatement ps5 = conn.prepareStatement("DELETE FROM career_applications WHERE userid = ?");
                ps5.setInt(1, userID);
                ps5.executeUpdate();

                PreparedStatement ps6 = conn.prepareStatement("DELETE FROM messages WHERE id = ?");
                ps6.setInt(1, userID);
                ps6.executeUpdate();

                PreparedStatement ps7 = conn.prepareStatement("DELETE FROM reshare WHERE userid = ?");
                ps7.setInt(1, userID);
                ps7.executeUpdate();

                String query = "delete  from users where userid =? " ;
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, userID);

                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println(RED + "Error deleting account: " + e.getMessage() + RESET);
                throw  new RuntimeException();
            }
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
