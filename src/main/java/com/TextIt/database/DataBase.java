package com.TextIt.database;

import java.sql.*;

/**
 * The {@code DataBase} class contains static nested classes to manage user-related
 * and OTP-related operations using PostgreSQL. This class serves as the low-level
 * database access layer for the TextIT application.
 */
public class DataBase {

    // Database credentials and URL
    private static final String url = "jdbc:postgresql://localhost:5432/Local TextIT";
    private static final String username = "postgres";
    private static final String password = "dhruv@1221";

    /**
     * The {@code Profile} class handles verification of unique user details
     * such as email, username, or phone number.
     */
    public static class Profile {

        /**
         * Checks if a value for a specific field (like email, username, or phone number)
         * already exists in the database.
         *
         * @param field the database column to check (e.g., "username")
         * @param input the value to check for uniqueness
         * @return true if the input is available (not taken), false if it already exists
         */
        public boolean isAvailable(String field, String input) {
            String query = "SELECT uid FROM profile WHERE " + field + " = ?";
            try (
                    Connection conn = DriverManager.getConnection(url, username, password);
                    PreparedStatement statement = conn.prepareStatement(query)
            ) {
                statement.setString(1, input);
                try (ResultSet rs = statement.executeQuery()) {
                    return !rs.next(); // true = available
                }
            } catch (SQLException e) {
                System.err.println("Failed to check availability: " + e.getMessage());
                return false;
            }
        }
    }

    /**
     * The {@code OTP} class handles storing and verifying OTPs in the database.
     */
    public static class OTP {

        /**
         * Stores a newly generated OTP in the database against a given email.
         *
         * @param email recipient's email
         * @param otp   the generated OTP
         */
        public void storeOTP(String email, String otp) {
            String query = "INSERT INTO otp (email_id, otp) VALUES (?, ?)";
            try (
                    Connection conn = DriverManager.getConnection(url, username, password);
                    PreparedStatement statement = conn.prepareStatement(query)
            ) {
                statement.setString(1, email);
                statement.setString(2, otp);
                statement.executeUpdate();
                System.out.println("OTP stored in database for " + email);
            } catch (SQLException e) {
                System.err.println("Failed to store OTP: " + e.getMessage());
            }
        }

        /**
         * Verifies the OTP submitted by the user.
         *
         * @param email         the email used to send the OTP
         * @param userInputOTP  the OTP entered by the user
         * @return true if the OTP is valid, false otherwise
         */
        public boolean verifyOTP(String email, String userInputOTP) {
            String query = "SELECT email_id FROM otp WHERE email_id = ? AND otp = ?";
            try (
                    Connection conn = DriverManager.getConnection(url, username, password);
                    PreparedStatement statement = conn.prepareStatement(query)
            ) {
                statement.setString(1, email);
                statement.setString(2, userInputOTP);
                try (ResultSet rs = statement.executeQuery()) {
                    return rs.next(); // true = match found
                }
            } catch (SQLException e) {
                System.err.println("Failed to verify OTP: " + e.getMessage());
                return false;
            }
        }

    }
}
