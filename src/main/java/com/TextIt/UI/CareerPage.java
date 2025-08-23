package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.security.CareerMailHandler;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CareerPage {

    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdb = db.new UserData();
    private static final DataBase.Career careerdb = db.new Career();

    public static void main(String[] args) throws MessagingException, IOException {
        int userID = Integer.parseInt(args[0]); // Example user ID
        Scanner sc = new Scanner(System.in);

        // --- Header ---
        System.out.println(CommonMethods.BRIGHT_CYAN + CommonMethods.BOLD + "============================================================" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_WHITE + CommonMethods.BOLD + "                        CAREER AT TEXTIT                    " + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + CommonMethods.BOLD + "============================================================" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_PURPLE + "         Apply today and be part of our journey!" + CommonMethods.RESET);
        System.out.println(CommonMethods.BRIGHT_CYAN + CommonMethods.BOLD + "============================================================\n" + CommonMethods.RESET);

        // --- Step 1: Select Department ---
        String[] categories = {CommonMethods.BRIGHT_BLUE + "Development" + CommonMethods.RESET, CommonMethods.BRIGHT_PURPLE + "Marketing" + CommonMethods.RESET, CommonMethods.BRIGHT_CYAN + "Product" + CommonMethods.RESET, CommonMethods.BRIGHT_YELLOW + "Design" + CommonMethods.RESET, CommonMethods.BRIGHT_GREEN + "Data" + CommonMethods.RESET, CommonMethods.BRIGHT_RED + "Security" + CommonMethods.RESET};
        System.out.println(CommonMethods.BRIGHT_YELLOW + CommonMethods.BOLD + "Select your department:" + CommonMethods.RESET);
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        int categoryChoice = getUserChoice(sc, 1, categories.length);

        // --- Step 2: Select Role ---
        String[] roles = getRolesForCategory(categoryChoice);
        System.out.println("\n" + CommonMethods.BRIGHT_YELLOW + CommonMethods.BOLD + "Select your role:" + CommonMethods.RESET);
        for (int i = 0; i < roles.length; i++) {
            System.out.println((i + 1) + ". " + roles[i]);
        }
        int roleChoice = getUserChoice(sc, 1, roles.length);
        String selectedRole = roles[roleChoice - 1];

        // --- Step 3: Get Name and Resume ---
        System.out.print("\n" + CommonMethods.BRIGHT_CYAN + CommonMethods.BOLD + "Enter your full name: " + CommonMethods.RESET);
        String name = sc.nextLine();

        System.out.print(CommonMethods.BOLD + "Enter resume file path (leave blank if none): " + CommonMethods.RESET);
        String resumePath = sc.nextLine();
        File resumeFile = resumePath.isEmpty() ? null : new File(resumePath);

        // --- Step 4: Get applicant email ---
        String applicantEmail = userdb.getEmail(userID);

        // --- Step 5: Save application ---
        boolean saved = careerdb.saveApplication(userID, selectedRole, resumePath.isEmpty() ? null : resumePath);
        if (saved) System.out.println(CommonMethods.GREEN + "Application saved in system." + CommonMethods.RESET);
        else System.out.println(CommonMethods.RED + "Could not save application in system." + CommonMethods.RESET);

        // --- Step 6: Send Emails ---
        System.out.println(CommonMethods.YELLOW + "Sending confirmation email..." + CommonMethods.RESET);
        CareerMailHandler.sendApplication(applicantEmail, name, selectedRole, resumeFile);
        System.out.println(CommonMethods.GREEN + "Email sent successfully!" + CommonMethods.RESET);

        System.out.println("\n" + CommonMethods.BRIGHT_PURPLE + "Press ENTER to return to menu..." + CommonMethods.RESET);
        sc.nextLine();
    }

    // --- Helper Methods ---
    private static int getUserChoice(Scanner sc, int min, int max) {
        int choice = -1;
        while (true) {
            System.out.print("Enter number: ");
            String input = sc.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) break;
                else System.out.println(CommonMethods.RED + "Invalid number. Try again." + CommonMethods.RESET);
            } catch (NumberFormatException e) {
                System.out.println(CommonMethods.RED + "Please enter a valid number." + CommonMethods.RESET);
            }
        }
        return choice;
    }

    private static String[] getRolesForCategory(int categoryChoice) {
        switch (categoryChoice) {
            case 1:
                return new String[]{CommonMethods.BRIGHT_BLUE + "Software Engineer (Backend)" + CommonMethods.RESET, CommonMethods.BRIGHT_BLUE + "Software Engineer (Frontend)" + CommonMethods.RESET, CommonMethods.BRIGHT_BLUE + "Full Stack Developer" + CommonMethods.RESET, CommonMethods.BRIGHT_BLUE + "Mobile App Developer (iOS/Android)" + CommonMethods.RESET, CommonMethods.BRIGHT_BLUE + "DevOps Engineer" + CommonMethods.RESET, CommonMethods.BRIGHT_BLUE + "Machine Learning Engineer" + CommonMethods.RESET};
            case 2:
                return new String[]{CommonMethods.BRIGHT_PURPLE + "Social Media Manager" + CommonMethods.RESET, CommonMethods.BRIGHT_PURPLE + "Content Strategist" + CommonMethods.RESET, CommonMethods.BRIGHT_PURPLE + "Digital Marketing Manager" + CommonMethods.RESET, CommonMethods.BRIGHT_PURPLE + "SEO Specialist" + CommonMethods.RESET, CommonMethods.BRIGHT_PURPLE + "Influencer Marketing Manager" + CommonMethods.RESET};
            case 3:
                return new String[]{CommonMethods.BRIGHT_CYAN + "Product Manager" + CommonMethods.RESET, CommonMethods.BRIGHT_CYAN + "Business Analyst" + CommonMethods.RESET, CommonMethods.BRIGHT_CYAN + "Campaign Manager" + CommonMethods.RESET};
            case 4:
                return new String[]{CommonMethods.BRIGHT_YELLOW + "UX/UI Designer" + CommonMethods.RESET, CommonMethods.BRIGHT_YELLOW + "Graphic Designer" + CommonMethods.RESET, CommonMethods.BRIGHT_YELLOW + "Video Editor" + CommonMethods.RESET, CommonMethods.BRIGHT_YELLOW + "Art Director" + CommonMethods.RESET};
            case 5:
                return new String[]{CommonMethods.BRIGHT_GREEN + "Data Analyst" + CommonMethods.RESET, CommonMethods.BRIGHT_GREEN + "Data Engineer" + CommonMethods.RESET, CommonMethods.BRIGHT_GREEN + "Machine Learning Engineer" + CommonMethods.RESET};
            case 6:
                return new String[]{CommonMethods.BRIGHT_RED + "Security Engineer" + CommonMethods.RESET, CommonMethods.BRIGHT_RED + "Policy Analyst" + CommonMethods.RESET};
            default:
                return new String[]{CommonMethods.GRAY + "Unknown" + CommonMethods.RESET};
        }
    }
}
