package com.TextIt.database;

import com.TextIt.security.Hashing;

import java.sql.*;
import java.time.LocalDate;

/**
 * The {@code DataBase} class contains static nested classes to manage user-related
 * and OTP-related operations using PostgresSQL. This class serves as the low-level
 * database access layer for the TextIT application.
 */
public class DataBase {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgresSQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    // Database credentials and URL

    private final String DB_URL = "jdbc:postgresql://localhost:5432/Local TextIT";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "dhruv@1221";



 //    public void loadDB(){
//        Properties props = new Properties();
//        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
//
//            if (input == null) {
//                System.out.println("Sorry, unable to find database.properties");
//                return;
//            }
//            props.load(input);
//
//             url = props.getProperty("db.url");
//             username = props.getProperty("db.username");
//             password = props.getProperty("db.password");
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

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

            String query = "INSERT INTO users (first_name, last_name, username, password_hash, mobile_number, email, created_at) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(DB_URL , DB_USERNAME , DB_PASSWORD)) {

                PreparedStatement ps = conn.prepareStatement(query);

                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, username);
                ps.setString(4, hashedPassword);
                ps.setString(5, mobileNumber);
                ps.setString(6, email);
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

    public boolean isServerReachable() {
        try (Connection _ = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            return true;
        } catch (SQLException e) {
            System.out.println("⚠️ Unable to connect to the server. Please check your internet connection or try again later.");
            return false;
        }
    }
}
