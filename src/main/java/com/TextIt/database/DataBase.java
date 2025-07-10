package com.TextIt.database;

import java.sql.*;

/**
 * The {@code DataBase} class contains static nested classes to manage user-related
 * and OTP-related operations using PostgreSQL. This class serves as the low-level
 * database access layer for the TextIT application.
 */
public class DataBase {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    // Database credentials and URL
    private final String url = "jdbc:postgresql://192.168.52.81:5432/TextIt";
    private final String username = "dhruv";
    private final String password = "dhruv@1221";


    /**
     * The {@code load}method is used to load {@code url},{@code password},{@code uername} to
     * Databse class
     *
     *
     *
     *
     */
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
            try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, input);
                try (ResultSet rs = statement.executeQuery()) {
                    return rs.next(); // true = available
                }
            } catch (SQLException e) {
                System.err.println("Failed to check availability: " + e.getMessage());
                return false;
            }
        }
    }
}
