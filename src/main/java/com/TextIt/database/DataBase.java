package com.TextIt.database;

import com.TextIt.model.exceptions.UserDetailNotMatchException;
import com.TextIt.security.Hashing;
import com.TextIt.service.user.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

/**
 * The {@code DataBase} class contains static nested classes to manage user-related
 * and OTP-related operations using PostgresSQL. This class serves as the low-level
 * database access layer for the TextIT application.
 */
public class DataBase {

    // Database credentials and URL
    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;

    {
        try {
            Class.forName("org.postgresql.Driver");
            loadDB();
        } catch (ClassNotFoundException e) {
            System.err.println("PostgresSQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return DB_URL;
    }

    public String getUsername() {
        return DB_USERNAME;
    }

    public String getPassword() {
        return DB_PASSWORD;
    }

    public void loadDB() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {

            if (input == null) {
                System.out.println("Sorry, unable to find database.properties");
                return;
            }
            props.load(input);

            DB_URL = props.getProperty("db.url");
            DB_USERNAME = props.getProperty("db.username");
            DB_PASSWORD = props.getProperty("db.password");


        } catch (IOException e) {
            System.err.println("Problem in loading database.properties file");
            e.printStackTrace();
        }

    }

    public boolean isServerReachable() {
        try (Connection _ = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            return true;
        } catch (SQLException e) {
            System.out.println("⚠️ Unable to connect to the server. Please check your internet connection or try again later.");
            return false;
        }
    }

    public int featchId(String userData) {
        Profile profile = new Profile();

        String query = "";
        if (profile.isAvailable("username", userData)) {
            query = "SELECT user_id FROM users WHERE username = ?";
        } else if (profile.isAvailable("email", userData)) {
            query = "SELECT user_id FROM users WHERE email = ?";
        } else {
            query = "SELECT user_id FROM users WHERE mobile_number  = ?";
        }

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, userData);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            } else {
                throw new UserDetailNotMatchException("No DataFound about the User");

            }

        } catch (SQLException e) {
            System.err.println("Error occurred while registering user: " + e.getMessage());
            e.printStackTrace(); // Optional: useful during debugging

        }
        return -1;
    }

    // fetch User_details byUser_id
    public UserData getUserData(int userId) {

        UserData user = new UserData();
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new UserData(rs.getString(4), rs.getString(2).concat(rs.getString(3)), rs.getString(7), rs.getDate(8).toLocalDate().getYear() , rs.getString(10));
            } else {
                System.out.println("User not found.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while registering user: " + e.getMessage());
            e.printStackTrace(); // Optional: useful during debugging

        }
        return null;
    }

    /**
     * The {@code Profile} class handles verification of unique user details
     * such as email, username, or phone number.
     */
    public class Profile {

        /**
         * Checks if a value for a specific field (like email, username, or phone number)
         * already exists in the database.
         *
         * @param field the database column to check (e.g., "username")
         * @param input the value to check for uniqueness
         * @return true if the input is available (not taken), false if it already exists
         */
        public boolean isAvailable(String field, String input) {
            String query = "SELECT user_id FROM users WHERE " + field + " = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

                PreparedStatement statement = conn.prepareStatement(query);

                statement.setString(1, input);
                try (ResultSet rs = statement.executeQuery()) {
                    return rs.next(); // true = available
                }
            } catch (SQLException e) {
                System.out.println("⚠️ Unable to connect to the server. Please check your internet connection or try again later.");
                return false;
            }
        }


        public boolean registerUser(String firstName, String lastName, String username, String password, String mobileNumber, String email) {
            String hashedPassword = Hashing.generateHashCode(password); // Hash the password
            LocalDate currentDate = LocalDate.now();                    // Account creation date

            String query = "INSERT INTO users (first_name, last_name, username, password_hash, mobile_number, email, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

                PreparedStatement ps = conn.prepareStatement(query);

                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, username);
                ps.setString(4, hashedPassword);
                ps.setString(5, mobileNumber);
                ps.setString(6, email.toLowerCase());
                ps.setDate(7, java.sql.Date.valueOf(currentDate));

                ps.executeUpdate();
                System.out.println("User registered successfully.");
                return true;

            } catch (SQLException e) {
                System.err.println("Error occurred while registering user: " + e.getMessage());
                e.printStackTrace(); // Optional: useful during debugging
                return false;
            }
        }

        public boolean updateProfile(String toUpdate, String updatedValue, String fromUpdate, String identifyingFactor) {
            String query = "UPDATE users SET " + toUpdate + " = ? WHERE " + fromUpdate + " = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, updatedValue);
                statement.setString(2, identifyingFactor);

                int rowsUpdated = statement.executeUpdate(); // Use executeUpdate for UPDATE queries
                return rowsUpdated > 0; // return true if at least one row was updated

            } catch (SQLException e) {
                System.out.println("⚠️ Unable to update profile: " + e.getMessage());
                return false;
            }
        }
    }

    public class Post{

        public int postCount(int userid){
            String query = "select count(*) from posts where user_id = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD)){
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1,userid);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching post count for user_id = " + userid);
            }
            return -1;
        }

        public String getShareCode(int postid){
            String query = "select share_code from posts where post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD)){
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1,postid);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching share code for post_id = " + postid);
            }
            return null;
        }
    }
}
