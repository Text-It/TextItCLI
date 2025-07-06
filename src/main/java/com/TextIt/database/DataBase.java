package com.TextIt.database;

import java.sql.*;

public class DataBase {
    private static final String url = "jdbc:postgresql://localhost:5432/Local TextIT";
    private static final String username = "postgres";
    private static final String password = "dhruv@1221";
    private static Connection conn;

    static public class Profile {

        /**
         * <h1>Method to find is input already occupied</h1>
         * <p>
         * Method checks in a database whether this user detail is already used for a different preexisting account
         * <br>
         * What details it checks.
         * <list>
         *         <ul>Username</ul>
         *         <ul>Mobile Number</ul>
         *         <ul>Email</ul>
         *     </list>
         * </p>
         *
         * @param field the detail to verify i.e. username
         * @param input the data to verify
         * @return false if data already exist else returns true
         * @throws SQLException if connection lost
         */
        public boolean isAvailable(String field, String input) throws SQLException {

            conn = DriverManager.getConnection(url, username, password);

            String query = "select uid from profile where " + field + " = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, input);


            ResultSet rs = statement.executeQuery();

            return rs.next();
        }
    }
}

