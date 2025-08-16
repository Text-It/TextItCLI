package com.TextIt.UI;

import com.TextIt.database.DataBase;
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
        System.out.println("============================================================");
        System.out.println("                        CAREER AT TEXTIT                    ");
        System.out.println("============================================================");
        System.out.println(" Apply today and be part of our journey!");
        System.out.println("============================================================\n");

        // --- Step 1: Select Department ---
        String[] categories = {"Development", "Marketing", "Product", "Design", "Data", "Security"};
        System.out.println("Select your department:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        int categoryChoice = getUserChoice(sc, 1, categories.length);

        // --- Step 2: Select Role ---
        String[] roles = getRolesForCategory(categoryChoice);
        System.out.println("\nSelect your role:");
        for (int i = 0; i < roles.length; i++) {
            System.out.println((i + 1) + ". " + roles[i]);
        }
        int roleChoice = getUserChoice(sc, 1, roles.length);
        String selectedRole = roles[roleChoice - 1];

        // --- Step 3: Get Name and Resume ---
        System.out.print("\nEnter your full name: ");
        String name = sc.nextLine();

        System.out.print("Enter resume file path (leave blank if none): ");
        String resumePath = sc.nextLine();
        File resumeFile = resumePath.isEmpty() ? null : new File(resumePath);

        // --- Step 4: Get applicant email ---
        String applicantEmail = userdb.getEmail(userID);

        // --- Step 5: Save application ---
        boolean saved = careerdb.saveApplication(userID, selectedRole, resumePath.isEmpty() ? null : resumePath);
        if (saved) System.out.println("\nApplication saved in system.");
        else System.out.println("\nCould not save application in system.");

        // --- Step 6: Send Emails ---
        CareerMailHandler.sendApplication(applicantEmail, name, selectedRole, resumeFile);

        System.out.println("\nPress ENTER to return to menu...");
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
                else System.out.println("Invalid number. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return choice;
    }

    private static String[] getRolesForCategory(int categoryChoice) {
        switch (categoryChoice) {
            case 1: return new String[]{"Software Engineer (Backend)", "Software Engineer (Frontend)",
                    "Full Stack Developer", "Mobile App Developer (iOS/Android)",
                    "DevOps Engineer", "Machine Learning Engineer"};
            case 2: return new String[]{"Social Media Manager", "Content Strategist",
                    "Digital Marketing Manager", "SEO Specialist", "Influencer Marketing Manager"};
            case 3: return new String[]{"Product Manager", "Business Analyst", "Campaign Manager"};
            case 4: return new String[]{"UX/UI Designer", "Graphic Designer", "Video Editor", "Art Director"};
            case 5: return new String[]{"Data Analyst", "Data Engineer", "Machine Learning Engineer"};
            case 6: return new String[]{"Security Engineer", "Policy Analyst"};
            default: return new String[]{"Unknown"};
        }
    }
}
