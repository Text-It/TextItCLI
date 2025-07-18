package com.TextIt.service.pages;



import com.TextIt.database.DataBase;
import com.TextIt.model.exceptions.*;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.security.Hashing;
import com.TextIt.security.OTPHandler;

import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;

public class LoginAuth {

    //Object of class DataBase
    private final DataBase dataBase = new DataBase();
    private final DataBase.Profile profile =dataBase.new Profile();


    /**
     * <h1>User Details</h1>
     * <p>This method is used to check whether a user with the following detail is signed in simple terms does a user exist with this detail
     * <br>
     * These details are checked
     * <br>
     * 1) Username
     * <br>
     * 2)Phone Number
     * <br>
     * 3)Email
     * <br>
     * At least one out of the above three conditions must be satisfied
     * <br>
     * </p>
     *
     * @param input user provided detail(name , phone number , email)
     * @return true, if one out of three conditions satisfies else, it returns false
     */
    public boolean verifyUserDetail(String input){

        try {
            if (profile.isAvailable("username", input)) {
                return true;
            } else if (profile.isAvailable("mobile_number", input)) {
                return true;
            } else if (profile.isAvailable("email", input)) {
                return true;
            } else {
                throw new UserDetailNotMatchException("no such user exists with the given detail");
            }
        } catch (UserDetailNotMatchException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void handleForgotPassword(Scanner scanner){

        //Object of Signup
        SignUp signup = new SignUp();

        //Variables
        String email, newPassword , conformPassword;

        System.out.println(CYAN + "\nVerifying your identity..." + RESET);

        System.out.print(YELLOW + "Enter your registered email for verification: " + RESET);
        email = scanner.nextLine().toLowerCase();

        if(!verifyUserDetail(email)){
            return;
        }

        String generatedOtp = OTPHandler.generateOTP(6);       // generate a 6 digit otp

        if (!OTPHandler.verifyOTPSend(email, generatedOtp)) {        //verify is otp is sent or not
            return;
        }

        if (!OTPHandler.verifyOTP(generatedOtp, scanner)) {                // verify if otp entered by a user is right or wrong
            return;
        }

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

        if(profile.updateProfile("password_hash" , hashedPassword , "email" , email )){
            System.out.println(GREEN + "\nPassword updated successfully" + RESET);
            CommonMethods.pressEnterToContinue();
        }
    }

    /**
     * <h1>Password Validator</h1>
     * <p>
     * This method is used to check if the input password matches with the password provided at the time of account creation
     * <br>
     * Steps
     * <br>
     * 1) First user-provided password is converted to hash because in database password is stored in hashcode because of security purpose
     * <br>
     * 2) Then this converted hashed password is checked if it is correct or not
     * <br>
     * </p>
     *
     * @param password user provided password
     * @return true, if the password matches else false
     */
    public boolean verifyPassword(String password){

         String hashedPassword = Hashing.generateHashCode(password);

        try {
            if (profile.isAvailable("password_hash", hashedPassword)) {
                return true;
            } else {
                throw new PasswordNotMatchException("password does not match");
            }
        } catch (PasswordNotMatchException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Return{@code True}  When Object is different (Like String)
     */
    public boolean isDifferent(Object s){
        return !(this==s);
    }


}
