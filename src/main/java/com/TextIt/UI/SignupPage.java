package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.security.OTPHandler;
import com.TextIt.service.pages.SignUpAuth;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;

public class SignupPage {

    private static final SignUpAuth newUser = new SignUpAuth();
    private static final DataBase connectivity = new DataBase();
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println(GREEN + BOLD + """
                ╔════════════════════════════════════════╗
                ║               Sign Up                  ║
                ╚════════════════════════════════════════╝
                """ + RESET);

        String username, password, email, phoneNumber, generatedOtp, firstName, lastName;

        while (true) {
            if (!connectivity.isServerReachable()) {        //check if server is reachable
                pressEnterToContinue();
                return;
            }
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

        do {
            System.out.print(YELLOW + "Enter username: " + RESET);
            username = scanner.nextLine();
        } while (!newUser.verifyUsername(username));

        do {
            System.out.print(YELLOW + "Enter password: " + RESET);
            password = scanner.nextLine();
        } while (!newUser.verifyPassword(password));


        do {
            System.out.print(YELLOW + "Enter phone number: " + RESET);
            phoneNumber = scanner.nextLine();
        } while (!newUser.verifyPhoneNumber(phoneNumber));

        do {
            System.out.print(YELLOW + "Enter First Name: " + RESET);
            firstName = scanner.nextLine();
        } while (!newUser.verifyRealName(firstName));

        do {
            System.out.print(YELLOW + "Enter Last Name: " + RESET);
            lastName = scanner.nextLine();
        } while (!newUser.verifyRealName(lastName));

        do {

            System.out.println("\n📄 Terms & Conditions");
            System.out.println("--------------------------------------------------");
            System.out.println("By using TextIT, you agree to the following:");
            System.out.println("1. You are responsible for the content you share.");
            System.out.println("2. Misuse or abuse of the platform is strictly prohibited.");
            System.out.println("3. You will not share offensive, hateful, or illegal content.");
            System.out.println("4. Your data may be stored securely for service enhancement.");
            System.out.println("5. OTP verification ensures account authenticity and security.");
            System.out.println("6. You consent to receive transactional emails related to security.");
            System.out.println("7. TextIT is a student project and does not guarantee data backups.");
            System.out.println("8. You agree not to reverse-engineer or distribute the source code.");
            System.out.println("9. Violations may result in temporary or permanent account suspension.");
            System.out.println("10. All rights reserved by TextIT Corporation © 2025");
            System.out.println("--------------------------------------------------");

            System.out.print("🔒 Do you accept the Terms & Conditions? (Y/N): ");
            char agreement = scanner.nextLine().toLowerCase().charAt(0);

            if (!(agreement == 'y')) {
                System.out.println("❌ Registration aborted. You must accept the Terms & Conditions to proceed.");
                pressEnterToContinue();
            } else {
                System.out.println("✅ Terms accepted. Proceeding with account creation...");
                break;
            }

        } while (true);
        {
            DataBase db = new DataBase();
            DataBase.Profile profile = db.new Profile();
            if (profile.registerUser(firstName, lastName, username, password, phoneNumber, email))
                System.out.println(GREEN + BOLD + "\nSign up successful!" + RESET);
            else {
                System.out.println(RED + BOLD + "\nSign up failed. Please try again." + RESET);
                System.out.println("If you have already registered, please login.");
            }
            pressEnterToContinue();}
    }
}
