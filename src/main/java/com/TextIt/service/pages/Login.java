package com.TextIt.service.pages;



import com.TextIt.database.DataBase;
import com.TextIt.model.exceptions.*;
import com.TextIt.security.Hashing;

import java.sql.SQLException;

public class Login {

    //Object of class DataBase
    private final DataBase dataBase = new DataBase();
    private final DataBase.Profile profile =dataBase.new Profile();


    /**
     * <h1>User Details</h1>
     * <p>This method is used to check whether a user with the following detail is signed in simple terms does a user exist with this details
     * <br>
     * These details are checked
     * <br>
     * 1) Username
     * <br>
     * 2)Phone Number
     * <br>
     * 3)Email
     * <br>
     * Atleast one out of the above three conditions must be satisfied
     * <br>
     * </p>
     *
     * @param input user provided detail(name , phone number , email)
     * @return true, if one out of three conditions satisfies else it returns false
     * @throws SQLException if server crashes or poor connection
     */
    public boolean verifyUserDetail(String input) throws SQLException {

        try {
            if (profile.isAvailable("username", input)) {
                return true;
            } else if (profile.isAvailable("mobile_number", input)) {
                return true;
            } else if (profile.isAvailable("email", input)) {
                return true;
            } else {
                throw new UserDetailNotMatchException("no such user with this username or mobile number or email");
            }
        } catch (UserDetailNotMatchException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * <h1>Password Validator</h1>
     * <p>
     * This method is used to check if the input password matches with the password provided at time of account creation
     * <br>
     * Steps
     * <br>
     * 1) First user provided password is converted to hash because in database password is stored in hashcode because of security purpose
     * <br>
     * 2) Then this converted hashed password is checked if it is correct or not
     * <br>
     * </p>
     *
     * @param password user provided password
     * @return true, if password matches else false
     * @throws SQLException if server crashes or poor connection
     */
    public boolean verifyPassword(String password) throws SQLException {

         String hashedPassword = Hashing.generateHashCode(password);

        try {
            if (profile.isAvailable("password", hashedPassword)) {
                return true;
            } else {
                throw new PasswordNotMatchException("password does not match");
            }
        } catch (PasswordNotMatchException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
