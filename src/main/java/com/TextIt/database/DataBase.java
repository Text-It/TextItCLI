package com.TextIt.database;

import com.TextIt.model.Messages;
import com.TextIt.model.exceptions.UserDetailNotMatchException;
import com.TextIt.security.Hashing;
import com.TextIt.service.data_structure.linked_list.DoublyLinkedList;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
            System.err.println("PostgresSQL JDBC Driver not found. x");
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
            System.out.println("Unable to connect to the server. Please check your internet connection or try again later.");
            return false;
        }
    }

    public int featchId(String userData) {
        Profile profile = new Profile();
        boolean emailExists = false;

        String query = "";
        if (profile.isAvailable("username", userData)) {
            query = "SELECT userID FROM users WHERE username = ?";
        } else if (profile.isAvailable("email", userData.toLowerCase())) {
            query = "SELECT userID FROM users WHERE email = ?";
            emailExists = true;
        } else {
            query = "SELECT userID FROM users WHERE mobile_number  = ?";
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

    public class Like{

        public boolean incrementLikesCount(int userid ,int postID){
            String query = "INSERT INTO likes (userid, post_id) VALUES (?, ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userid);
                pst.setInt(2, postID);
                pst.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Can't Like the Post More Than Once");
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
            String query = "SELECT userID FROM users WHERE " + field + " = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

                PreparedStatement statement = conn.prepareStatement(query);

                statement.setString(1, input);
                try (ResultSet rs = statement.executeQuery()) {
                    return rs.next(); // true = available
                }catch (Exception e) {
                    System.out.println("Error occurred while fetching user profile: " + e.getMessage());
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Unable to connect to the server. Please check your internet connection or try again later.");
                return false;
            }
        }


        public boolean registerUser(String firstName, String lastName, String username, String password, String mobileNumber, String email , String shareCode) {
            String hashedPassword = Hashing.generateHashCode(password); // Hash the password
            LocalDate currentDate = LocalDate.now();                    // Account creation date

            String query = "INSERT INTO users (first_name, last_name, username, password_hash, mobile_number, email, created_at,user_url) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

                PreparedStatement ps = conn.prepareStatement(query);

                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, username);
                ps.setString(4, hashedPassword);
                ps.setString(5, mobileNumber);
                ps.setString(6, email.toLowerCase());
                ps.setDate(7, java.sql.Date.valueOf(currentDate));
                ps.setString(8, shareCode);

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

    public class ReShare{
        public boolean reSharePost(int postID, int userID) {
            String getOriginalQuery = "SELECT original_post_id FROM reshare WHERE post_id = ?";
            String insertQuery = "INSERT INTO reshare (post_id, userid, original_post_id) VALUES (?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

                int originalPostID = postID; // default assume it's original

                // Step 1: Try to fetch original_post_id (if this post was already a reshare)
                try (PreparedStatement pst1 = conn.prepareStatement(getOriginalQuery)) {
                    pst1.setInt(1, postID);
                    ResultSet rs = pst1.executeQuery();
                    if (rs.next()) {
                        originalPostID = rs.getInt("original_post_id");
                    }
                }

                // Step 2: Insert into reshare
                try (PreparedStatement pst2 = conn.prepareStatement(insertQuery)) {
                    pst2.setInt(1, postID);
                    pst2.setInt(2, userID);
                    pst2.setInt(3, originalPostID);
                    pst2.executeUpdate();
                }

                return true;

            } catch (SQLException e) {
                System.err.println("Error occurred while resharing post: " + e.getMessage());
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
            String query = "select gender from users where userID = ?";
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
            return "not set yet";
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
            String query = "select user_url from users where userID = ?";
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

        public String getEmail(int userID){
            String query = "select email from users where userID = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching email for = " + userID);
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

    public class Comment {
        public List<String[]> getComments(int postID, int limit, int offset) {
            List<String[]> list = new ArrayList<>();
            String query = "SELECT u.username, c.content, c.created_at " +
                    "FROM comments c JOIN users u ON c.userid = u.userid " +
                    "WHERE c.post_id = ? ORDER BY c.created_at DESC " +
                    "LIMIT ? OFFSET ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, postID);
                pst.setInt(2, limit);
                pst.setInt(3, offset);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    String username = rs.getString("username");
                    String content = rs.getString("content");
                    Timestamp createdAt = rs.getTimestamp("created_at");

                    String formattedTime = formatTime(createdAt);

                    list.add(new String[]{username, content, formattedTime});
                }
            } catch (SQLException e) {
                System.err.println("Error fetching comments: " + e.getMessage());
            }
            return list;
        }

        private String formatTime(Timestamp createdAt) {
            long diffMillis = System.currentTimeMillis() - createdAt.getTime();
            long diffSeconds = diffMillis / 1000;
            long diffMinutes = diffSeconds / 60;
            long diffHours = diffMinutes / 60;
            long diffDays = diffHours / 24;

            if (diffMinutes < 1) {
                return "Posted just now";
            } else if (diffMinutes < 60) {
                return "Posted " + diffMinutes + " minute" + (diffMinutes > 1 ? "s" : "") + " ago";
            } else if (diffHours < 24) {
                return "Posted " + diffHours + " hour" + (diffHours > 1 ? "s" : "") + " ago";
            } else {
                return "Posted " + diffDays + " day" + (diffDays > 1 ? "s" : "") + " ago";
            }
        }


        public boolean addComment(int postID, int userID, String text) {
            String query = "INSERT INTO comments (post_id, userid, content) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 PreparedStatement pst = conn.prepareStatement(query)) {

                pst.setInt(1, postID);    // the post being commented on
                pst.setInt(2, userID);    // the user who is commenting
                pst.setString(3, text);   // the comment text
                pst.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.err.println("❌ Error adding comment: " + e.getMessage());
                return false;
            }
        }
    }

    public class Career{

        public boolean saveApplication(int userId, String role, String resumePath) {
            String query = "INSERT INTO career_applications (userid, role_applied, resume_path) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)){
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, userId);
                ps.setString(2, role);
                ps.setString(3, resumePath);
                ps.executeUpdate();
                return true;
            }
             catch (SQLException e) {
                System.err.println("Error saving application: " + e.getMessage());
                return false;
            }
        }
    }



    public class Post {

        public int getPostCount(int userid) {
            String query = "select count(*) from posts where userID = ?";

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

        public boolean insertPost(int userID, String postContent , String shareCode) {
            String query = "INSERT INTO posts (userID, content,post_url) VALUES (?, ? ,?)";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userID);
                pst.setString(2, postContent);
                pst.setString(3, shareCode);
                int rowsUpdated = pst.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                System.err.println("Error occurred while inserting post: " + e.getMessage());
                return false;
            }
        }

        public DoublyLinkedList<Integer> getPostIds(int limit, int offset) {
            DoublyLinkedList<Integer> ids = new DoublyLinkedList<>();
            String query = "SELECT post_id FROM posts ORDER BY post_point  DESC LIMIT ? OFFSET ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setInt(1, limit);
                pst.setInt(2, offset);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    ids.insertLast(rs.getInt("post_id"));
                }
            } catch (SQLException e) {
                System.err.println("Error fetching post ids: " + e.getMessage());
            }
            return ids;
        }



        public int getPostCommentsCount(int postid) {
            String query = "select count(*) from comments where post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postid);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching post comments count for post_id = " + postid);
            }
            return -1;
        }
        public int getUserId(int postid){
            String query = "select userid from posts where post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postid);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching user id for post_id = " + postid);
            }
            return -1;
        }


        public int getPostLikesCount(int postid) {
            String query = "select count(*) from likes where post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postid);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching post likes count for post_id = " + postid);
            }
            return -1;
        }

        public int getPostResharesCount(int postid) {
            String query = "select count(*) from reshare where original_post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postid);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching post reshares count for post_id = " + postid);
            }
            return -1;
        }

        public String getShareCode(int postid) {
            String query = "select post_url from posts where post_id = ?";
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

        public String getPostContent(int postid) {
            String query = "select content from posts where post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postid);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching Content   for post_id = " + postid);
            }
            return null;
        }

        public String getPostUsername(int postId){
            String query = "select username from users where userid = (select userid from posts where post_id = ?)";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postId);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching post username for post_id = " + postId);
            }
            return "Unknown user";
        }
        public int getPostViewCount(int postId){
            String query = "select view_count from posts where post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postId);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching post view count for post_id = " + postId);
            }
            return -1;
        }

        public boolean updatePostViewCount(int postId){
            String query = "UPDATE posts SET view_count = view_count + 1 WHERE post_id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, postId);
                int rowsUpdated = pst.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                System.err.println("Error occurred while updating post view count: " + e.getMessage());
                return false;
            }
        }


        public String getPostTime(int postId) {
            String query = "SELECT created_at FROM posts WHERE post_id = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                 PreparedStatement pst = conn.prepareStatement(query)) {

                pst.setInt(1, postId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    Timestamp createdAt = rs.getTimestamp(1);

                    long diffMillis = System.currentTimeMillis() - createdAt.getTime();
                    long diffSeconds = diffMillis / 1000;
                    long diffMinutes = diffSeconds / 60;
                    long diffHours = diffMinutes / 60;
                    long diffDays = diffHours / 24;

                    if (diffMinutes < 1) {
                        return "Posted just now";
                    } else if (diffMinutes < 60) {
                        return "Posted " + diffMinutes + " minute" + (diffMinutes > 1 ? "s" : "") + " ago";
                    } else if (diffHours < 24) {
                        return "Posted " + diffHours + " hour" + (diffHours > 1 ? "s" : "") + " ago";
                    } else {
                        return "Posted " + diffDays + " day" + (diffDays > 1 ? "s" : "") + " ago";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return "Unknown time";
        }


    }
    /**
     * The {@code AccountManager} class handles account management operations
     * such as updating passwords, email, phone numbers, and profile information.
     */
    public class AccountManager {

        /**
         * Updates the user's password after verifying the current password.
         *
         * @param userId          The ID of the user
         * @param currentPassword The current password for verification
         * @param newPassword     The new password to set
         * @return true if password was updated successfully, false otherwise
         */
        public boolean updatePassword(int userId, String currentPassword, String newPassword) {
            // First verify the current password
            if (!verifyCurrentPassword(userId, currentPassword)) {
                return false;
            }

            String hashedNewPassword = Hashing.generateHashCode(newPassword);
            String query = "UPDATE users SET password_hash = ? WHERE user_id = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, hashedNewPassword);
                pst.setInt(2, userId);

                int rowsUpdated = pst.executeUpdate();
                return rowsUpdated > 0;

            } catch (SQLException e) {
                System.err.println("Error updating password for user ID = " + userId + ": " + e.getMessage());
                return false;
            }
        }

        /**
         * Updates the user's email address.
         *
         * @param userId   The ID of the user
         * @param newEmail The new email address
         * @return true if email was updated successfully, false otherwise
         */
        public boolean updateEmail(int userId, String newEmail) {
            // Check if email is already in use by another user
            Profile profile = new Profile();
            if (profile.isAvailable("email", newEmail.toLowerCase())) {
                System.out.println("Email is already in use by another account.");
                return false;
            }

            String query = "UPDATE users SET email = ? WHERE user_id = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, newEmail.toLowerCase());
                pst.setInt(2, userId);

                int rowsUpdated = pst.executeUpdate();
                return rowsUpdated > 0;

            } catch (SQLException e) {
                System.err.println("Error updating email for user ID = " + userId + ": " + e.getMessage());
                return false;
            }
        }

        /**
         * Updates the user's mobile number.
         *
         * @param userId          The ID of the user
         * @param newMobileNumber The new mobile number
         * @return true if mobile number was updated successfully, false otherwise
         */
        public boolean updateMobileNumber(int userId, String newMobileNumber) {
            // Check if mobile number is already in use by another user
            Profile profile = new Profile();
            if (profile.isAvailable("mobile_number", newMobileNumber)) {
                System.out.println("Mobile number is already in use by another account.");
                return false;
            }

            String query = "UPDATE users SET mobile_number = ? WHERE user_id = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, newMobileNumber);
                pst.setInt(2, userId);

                int rowsUpdated = pst.executeUpdate();
                return rowsUpdated > 0;

            } catch (SQLException e) {
                System.err.println("Error updating mobile number for user ID = " + userId + ": " + e.getMessage());
                return false;
            }
        }

        /**
         * Updates the user's profile information (first name, last name, bio).
         *
         * @param userId    The ID of the user
         * @param firstName The new first name (null to keep current)
         * @param lastName  The new last name (null to keep current)
         * @param bio       The new bio (null to keep current)
         * @return true if profile was updated successfully, false otherwise
         */
        public boolean updateProfileInfo(int userId, String firstName, String lastName, String bio) {
            StringBuilder queryBuilder = new StringBuilder("UPDATE users SET ");
            boolean hasUpdates = false;

            if (firstName != null && !firstName.trim().isEmpty()) {
                queryBuilder.append("first_name = ?");
                hasUpdates = true;
            }

            if (lastName != null && !lastName.trim().isEmpty()) {
                if (hasUpdates) queryBuilder.append(", ");
                queryBuilder.append("last_name = ?");
                hasUpdates = true;
            }

            if (bio != null && !bio.trim().isEmpty()) {
                if (hasUpdates) queryBuilder.append(", ");
                queryBuilder.append("user_bio = ?");
                hasUpdates = true;
            }

            if (!hasUpdates) {
                return false;
            }

            queryBuilder.append(" WHERE user_id = ?");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(queryBuilder.toString());

                int paramIndex = 1;
                if (firstName != null && !firstName.trim().isEmpty()) {
                    pst.setString(paramIndex++, firstName.trim());
                }
                if (lastName != null && !lastName.trim().isEmpty()) {
                    pst.setString(paramIndex++, lastName.trim());
                }
                if (bio != null && !bio.trim().isEmpty()) {
                    pst.setString(paramIndex++, bio.trim());
                }
                pst.setInt(paramIndex, userId);

                int rowsUpdated = pst.executeUpdate();
                return rowsUpdated > 0;

            } catch (SQLException e) {
                System.err.println("Error updating profile info for user ID = " + userId + ": " + e.getMessage());
                return false;
            }
        }

        /**
         * Gets the current email address for a user.
         *
         * @param userId The ID of the user
         * @return The user's email address or null if not found
         */
        public String getCurrentEmail(int userId) {
            String query = "SELECT email FROM users WHERE user_id = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    return rs.getString(1);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching email for user ID = " + userId);
            }
            return null;
        }

        /**
         * Verifies if the provided password matches the user's current password.
         *
         * @param userId          The ID of the user
         * @param currentPassword The password to verify
         * @return true if password matches, false otherwise
         */
        private boolean verifyCurrentPassword(int userId, String currentPassword) {
            String query = "SELECT password_hash FROM users WHERE user_id = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString(1);
                    String providedHash = Hashing.generateHashCode(currentPassword);
                    return storedHash.equals(providedHash);
                }
            } catch (SQLException e) {
                System.err.println("Error verifying password for user ID = " + userId);
            }
            return false;
        }
    }

    public class ChatListener implements Runnable {
        private final Connection conn;
        private final PGConnection pgConn;
        private final String username;

        public ChatListener(String username) throws Exception {
            this.conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Required for LISTEN to take effect immediately
            this.conn.setAutoCommit(true);

            this.pgConn = conn.unwrap(PGConnection.class);
            this.username = username;

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("LISTEN new_message");
                System.out.println("[DEBUG] LISTEN registered for 'new_message'");
            }
        }

        @Override
        public void run() {
            try (Statement stmt = conn.createStatement()) {
                while (true) {
                    // force Postgres to deliver pending notifications
                    stmt.execute("SELECT 1");

                    PGNotification[] notifications = pgConn.getNotifications();

                    if (notifications != null ) {
                        for (PGNotification n : notifications) {

                            String[] parts = n.getParameter().split(":", 3);
                            if (parts.length < 3) continue;

                            String receiver = parts[0];
                            String sender = parts[1];
                            String msg = parts[2];

                            if (receiver.trim().equalsIgnoreCase(username.trim())) {
                                System.out.println();
                                System.out.println(sender + ": " + msg);
                                System.out.print("> "); // restore prompt
                            }
                        }
                    }

                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class Chats{
        Connection conn;
        public Chats() throws Exception {
            this.conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        }

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

            // get chat history
            public List<Messages> getMessages(String user1, String user2) throws SQLException {
                List<Messages> messages = new ArrayList<>();

                String sql = "SELECT sender, receiver, message, sent_at " + "FROM messages " + "WHERE (sender = ? AND receiver = ?)  ";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, user2);
                    ps.setString(2, user1);
//                    ps.setString(3, user2);
//                    ps.setString(4, user1);

                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Messages m = new Messages(
                                    rs.getString("sender"),
                                    rs.getString("receiver"),
                                    rs.getString("message"),
                                    rs.getString("sent_at")
                            );
                            messages.add(m);
                        }
                    }
                }

                return messages;
            }
        }

    }

