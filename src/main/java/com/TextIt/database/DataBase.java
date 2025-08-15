package com.TextIt.database;

import com.TextIt.model.exceptions.UserDetailNotMatchException;
import com.TextIt.security.Hashing;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

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
        boolean emailExists = false;

        String query = "";
        if (profile.isAvailable("username", userData)) {
            query = "SELECT user_id FROM users WHERE username = ?";
        } else if (profile.isAvailable("email", userData.toLowerCase())) {
            query = "SELECT user_id FROM users WHERE email = ?";
            emailExists = true;
        } else {
            query = "SELECT user_id FROM users WHERE mobile_number  = ?";
        }

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement statement = con.prepareStatement(query);
            // for comparing email to database email Only
            if (emailExists) {
                statement.setString(1, userData.toLowerCase());
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return -1;

            }
            statement.setString(1, userData);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new UserDetailNotMatchException("No DataFound about the User");
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while registering user: " + e.getMessage());
            e.printStackTrace(); // Optional: useful during debugging

        }
        return -1;
    }

    public class UserFollows{

        public int getFollowersCount(int userID){
            String query = "select count(*) from user_follows where following_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching follower count for userID = " + userID);
            }
            return -1;
        }

        public int getFollowingCount(int userID){
            String query = "select count(*) from user_follows where follower_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching following count for userID = " + userID);
            }
            return -1;
        }

        public boolean followUser(int followerID, int followingID){
            String query = "INSERT INTO user_follows (follower_id, following_id) VALUES (?, ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, followerID);
                pst.setInt(2, followingID);
                pst.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Error occurred while following user: " + e.getMessage());
            }
            return false;
        }

        public boolean unFollowUser(int followerID, int followingID){
            String query = "DELETE FROM user_follows WHERE follower_id = ? AND following_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, followerID);
                pst.setInt(2, followingID);
                pst.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Error occurred while unfollowing user: " + e.getMessage());
            }
            return false;
        }
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

    public class UserData {

        public UserData() {
        }

        public String getUserName(int userID) {
            String query = "select username from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching user name for = " + userID);
            }
            return null;
        }

        public String getGender(int userID) {
            String query = "select user_gender from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching gender for = " + userID);
            }
            return null;
        }

        public String getLocation(int userID) {
            String query = "select user_location from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching location for = " + userID);
            }
            return null;
        }

        public String getBio(int userID) {
            String query = "select user_bio from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching bio for = " + userID);
            }
            return null;
        }



        public String getFirstName(int userID) {
            String query = "select first_name from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching first name for = " + userID);
            }
            return null;
        }

        public int getXP(int userID) {
            String query = "select user_xp from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching XP for = " + userID);
            }
            return -1;
        }

        public int getLevel(int userID) {
            String query = "select user_level from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching level for = " + userID);
            }
            return -1;
        }

        public int getMemberSince(int userID) {
            String query = "SELECT created_at FROM users WHERE userID = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 PreparedStatement pst = conn.prepareStatement(query)) {

                pst.setInt(1, userID);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        java.sql.Date date = rs.getDate(1); // Directly get as Date
                        java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());
                        if (date != null) {
                            return today.toLocalDate().getYear() - date.toLocalDate().getYear(); // Extract only year
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error fetching member since year for userID = " + userID);
            }

            return -1; // Indicates not found or error
        }

        public String getUserShareCode(int userID) {
            String query = "select user_url from users where user_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching share code for = " + userID);
            }
            return null;
        }


        public String getLastName(int userID) {
            String query = "select last_name from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching last name for = " + userID);
            }
            return null;
        }

        public String getRealName(int userID){
            return getFirstName(userID) + " " + getLastName(userID);
        }

        public String getMobileNumber(int userID) {
            String query = "select mobile_number from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching mobile number for = " + userID);
            }
            return null;
        }
    }


    public class Post {

        public int getPostCount(int userid) {
            String query = "select count(*) from posts where user_id = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userid);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching post count for user_id = " + userid);
            }
            return -1;
        }

        public String getShareCode(int postid) {
            String query = "select share_code from posts where post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postid);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching share code for post_id = " + postid);
            }
            return null;
        }
    }
    public class ChatListener  implements Runnable   {
        private Connection conn;
        private PGConnection pgConn;
        private String username;

        public ChatListener(String username) throws Exception {
            this. conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             this. pgConn = conn.unwrap(PGConnection.class);
             this.username = username;


            // Listen to the chat channel
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("LISTEN new_message");
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    PGNotification[] notifications = pgConn.getNotifications();
                    if (notifications != null) {
                        for (PGNotification n : notifications) {
                            String[] parts = n.getParameter().split(":", 3);
                            String receiver = parts[0];
                            String sender = parts[1];
                            String msg = parts[2];

                            if (receiver.equalsIgnoreCase(username)) {
                                System.out.println("\n" + sender + ": " + msg);
                                System.out.print("> ");
                            }
                        }
                    }
                    Thread.sleep(500); // avoid busy-wait
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
     public class Chats{

        public void send(String sender, String receiver, String msg) throws SQLException {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String sql = "INSERT INTO messages (sender, receiver, message) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, sender);
                ps.setString(2, receiver);
                ps.setString(3, msg);
                ps.executeUpdate();
            }
        }
    }
}
