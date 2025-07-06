package com.TextIt.service.pages;



import com.TextIt.database.DataBase;
import com.TextIt.model.auth.Authentication;
import com.TextIt.model.exceptions.*;


import java.sql.SQLException;

public class SignUp implements Authentication {

    //Object's Of class Database
    DataBase db = new DataBase();
    DataBase.Profile profile = new DataBase.Profile();


    /**
     * This method is to validate username
     * <p>
     * <br>
     * Factors to validate a username
     * <br>
     * 1) Should not be empty.
     * <br>
     * 2) Should not be associated with another account.
     * <br>
     * </p>
     * On fulfilling the above conditions, a username is assigned to an account
     * <br>
     *
     * @param username user provided username
     * @return true if the username is validly else false
     */
    @Override
    public boolean verifyUsername(String username) {

        try {
            if (username.isEmpty()) {
                throw new EmptyInputException("Username is empty");
            }

            if (profile.isAvailable("username", username)) {
                throw new DataAlreadyUsedException("Username already exists");
            }
        } catch (EmptyInputException | DataAlreadyUsedException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * This Method is to validate the Password
     * <p>
     * <br>
     * Factors for a valid password
     * <br>
     * 1) Should not be empty.
     * <br>
     * 2) At least one Uppercase character.
     * <br>
     * 3) At least one Lowercase character.
     * <br>
     * 4) At least one Integer character.
     * <br>
     * 5) At least one Special character from these options [!, @, $, &, *].
     * </p>
     * <br>
     * On fulfilling the above condition, a password is set for an account
     *
     * @param password user provided password
     * @return true if the password is validly else false
     */
    @Override
    public boolean verifyPassword(String password) {

        try {

            if (password.isEmpty()) {
                throw new EmptyInputException("Password can't be empty");
            }
            if (!isCharFound(password, 'A', 'Z')) {
                throw new UpperCaseNotFoundException("Minimum One Upper Case Character Required");
            }
            if (!isCharFound(password, 'a', 'z')) {
                throw new LowerCaseNotFoundException("Minimum One Lower Case Character Required");
            }
            if (!isCharFound(password, '0', '9')) {
                throw new NumericNotFoundException("Minimum One Number (0-9) Required");
            }
            if (!password.matches(".*[!@$&*].*")) {
                throw new SpecialCharacterNotFoundException("Special Character Not Found");
            }

            if (password.length() < 8 || password.length() > 16) {
                throw new IllegalLengthException("Password must be 8 to 16 characters long");
            }

        } catch (EmptyInputException | IllegalLengthException | UpperCaseNotFoundException |
                 LowerCaseNotFoundException | NumericNotFoundException | SpecialCharacterNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * This method is to validate email
     * <p>
     * <br>
     * Factors to validate a email
     * <br>
     * 1) Should not be empty.
     * <br>
     * 2) Should not be associated with another account.
     * <br>
     * </p>
     * On fulfilling the above conditions, an email is assigned to an account
     * <br>
     *
     * @param email user provided email
     * @return true if the email is validly else false
     */
    @Override
    public boolean verifyEmail(String email) {

        try {
            if (email.isEmpty()) {
                throw new EmptyInputException("Email can't be empty");
            }
            if (profile.isAvailable("email", email)) {
                throw new DataAlreadyUsedException("email already exists");
            }
        } catch (EmptyInputException | DataAlreadyUsedException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * This Method is to validate phone number
     *
     * <p>
     * Factors for a valid phone number
     * <br>
     * 1) Should not be empty.
     * <br>
     * 2) Should contain integer only.
     * <br>
     * 3) Should contain 10 to 15 digits.
     * <br>
     * 4) Should not be associated with another account.
     * <br>
     * On fulfilling the above conditions, phone number is assigned to an account
     * </p>
     *
     * @param phoneNumber user provided phone number
     * @return true if the above conditions are satisfied else returns false
     */
    @Override
    public boolean verifyPhoneNumber(String phoneNumber) {
        try {
            if (phoneNumber.isEmpty()) {
                throw new EmptyInputException("Phone Number can't be empty");
            }
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (phoneNumber.charAt(i) < '0' || phoneNumber.charAt(i) > '9') {
                    throw new NumberFormatException("Phone Number must contain numbers only");
                }
            }
            if (phoneNumber.length() < 10 || phoneNumber.length() > 15) {
                throw new IllegalLengthException("Phone Number must contain 10 to 15 digits");
            }
            if (profile.isAvailable("phonenumber", phoneNumber)) {
                throw new DataAlreadyUsedException("phonenumber already exists");
            }
        } catch (IllegalLengthException | EmptyInputException | NumberFormatException | DataAlreadyUsedException |
                 SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * This method is used to find whether a character is found in the provided input field
     * <br>
     * <p>
     * How it Works
     * <br>
     * it transverse through the input field and checks that if a character from a given range of characters is found in the field
     * </p>
     * <br>
     * <p>
     * example
     * input -> "Lorem", starting -> "A", ending -> "Z"
     * <br>
     * it returns true because the uppercase found "L";
     * </p>
     * <br>
     *
     * @param input    user provided input
     * @param starting is the start point of range of characters
     * @param ending   is the ending character of range of characters
     * @return true, if a character is found in the field else returns false
     */
    private boolean isCharFound(String input, char starting, char ending) {

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= starting && input.charAt(i) <= ending) {
            return true;
            }
        }
        return false;
    }
}
