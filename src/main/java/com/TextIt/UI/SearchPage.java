package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.service.data_structure.Hash_Map.HashMapp;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.data_structure.linked_list.DoublyLinkedList;

import java.sql.*;
import java.util.Scanner;

import static com.TextIt.UI.FeedPage.printOptionRow;


public class SearchPage {

    private final HashMapp<String, String> userData;
    private final DataBase database;
    private final Scanner scanner;
    public SearchPage() {
        this.userData = new HashMapp<>();
        this.database = new DataBase();
        this.scanner = new Scanner(System.in);
        loadUser();
    }


    private void loadUser() {
        if (!database.isServerReachable()) {
            System.out.println(CommonMethods.color("Cannot connect to database. Search functionality unavailable.", CommonMethods.RED));
            return;
        }

        String query = "SELECT userid, first_name, last_name, username, email FROM users";

        try (Connection conn = DriverManager.getConnection(database.getUrl(), database.getUsername(), database.getPassword())) {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            int userCount = 0;
            while (rs.next()) {
                String username = rs.getString("username").toLowerCase().trim();
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String userid = rs.getString("userid");

                String fullName = firstName + " " + lastName;

               userData.put(username, userid);
                userCount++;
            }

            System.out.println(CommonMethods.color("Loaded " + userCount + " users into search index.", CommonMethods.GREEN));

        } catch (SQLException e) {
            System.out.println(CommonMethods.color("Error loading users: " + e.getMessage(), CommonMethods.RED));
        }
    }


    public String searchByUserName(String username) {
        if (username == null ) {
            System.out.println(CommonMethods.color("Username cannot be empty.", CommonMethods.RED));
            return null;
        }

        String searchKey = username.trim();

        String foundUser = userData.get(searchKey);

        if (foundUser != null) {
            System.out.println(CommonMethods.color("User found!", CommonMethods.GREEN));
            CommonMethods.userProfile(Integer.parseInt(foundUser));
        } else {
            System.out.println(CommonMethods.color(" No user found with username: " + username, CommonMethods.RED));
        }
        return foundUser;
    }


    public void Search() {
        while (true) {
            System.out.println(CommonMethods.GREEN + CommonMethods.BOLD +
                    "╔════════════════════════════════════════╗\n" +
                    "║              Search User               ║\n" +
                    "╚════════════════════════════════════════╝" +
                    CommonMethods.RESET);

            System.out.println(CommonMethods.color("1. Search by exact username", CommonMethods.GREEN));
            System.out.println(CommonMethods.color("2. Refresh user data", CommonMethods.BLUE));
            System.out.println(CommonMethods.color("3 Past Link here", CommonMethods.CYAN));
            System.out.println(CommonMethods.color("4. Back to main menu", CommonMethods.RED));
            System.out.print(CommonMethods.color("Enter your choice (1-3): ", CommonMethods.BOLD));

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    searchUser();
                    break;
                case "2":
                    refresh();
                    break;
                case "3":
                    // link past to show them uer_profile/user_post (from the Link)
                    searchByLink();
                    break;
                case "4":
                    System.out.println(CommonMethods.color("Returning to main menu...", CommonMethods.GREEN));
                    return;
                default:
                    System.out.println(CommonMethods.color("Invalid choice. Please enter 1, 2, 3.", CommonMethods.RED));
            }

            CommonMethods.pressEnterToContinue();
        }
    }
    private void searchUser() {
        System.out.print(CommonMethods.color("Enter username to search: ", CommonMethods.BOLD));
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println(CommonMethods.color("Username cannot be empty.", CommonMethods.RED));
            return;
        }

        System.out.println(CommonMethods.color(" Searching for user: " + username, CommonMethods.CYAN));
        searchByUserName(username);
    }
    private void refresh() {
        System.out.println(CommonMethods.color(" Refreshing user data from database...", CommonMethods.BLUE));
        userData.clear();
        loadUser();
        System.out.println(CommonMethods.color(" User data refreshed successfully!", CommonMethods.GREEN));
    }


    private void searchByLink() {
        System.out.println("Past Link here");
        System.out.print(CommonMethods.color("Searching for link: " , CommonMethods.CYAN));
        String link = scanner.nextLine().trim();
        if (link.isEmpty()) {
            System.out.println(CommonMethods.color("Link cannot be empty.", CommonMethods.RED));
        }else {
            try (Connection conn = DriverManager.getConnection(database.getUrl(), database.getUsername(), database.getPassword())) {
                String query = "select username from users where user_url =  ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, link);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    String username = rs.getString("username");
                    searchByUserName(username);
                }else{
                    String query1 = "select userid from posts where post_url = ?";
                    PreparedStatement ps1 = conn.prepareStatement(query1);
                    ps1.setString(1, link);
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next()){
                        int userid = rs1.getInt(1);
                        postSearchView(userid);
                    }
                }
            } catch (Exception e) {
                System.out.println(CommonMethods.color("Error searching for link: " + e.getMessage(), CommonMethods.RED));
                //hrow new RuntimeException();
            }

        }
    }

    private void  postSearchView(int userid ) {

        DataBase db = new DataBase();
        DataBase.UserData userdb = db.new UserData();
        DataBase.Post postdb = db.new Post();
        DataBase.Like likedb = db.new Like();
        DataBase.ReShare resharedb = db.new ReShare();
        //CommonMethods.clearConsole(); // clear screen before each render


        //Important Variables
        int userID = userid;
        boolean feedType = true;
        DoublyLinkedList<Integer> buffer;
        int boxLength = 70;
        String border = "||";
        int spaceLeftForContent = boxLength - border.length() * 2;

        // pagination variables
        int pageSize = 1;   // show 1 post at a time
        int offset = 0;
        if (feedType) {
            buffer = postdb.getPostIds(pageSize, offset);
        } else {
            buffer = postdb.getPostIdsforParticularUser(pageSize, offset, userID);
        }
        int bufferIndex = 0;

        //Some predifined texts
        String pageHeader = "REEL";
        String pageDiscription = "Discover what other Textizens are Posting";
        String whoPosted = "Reel By: ";
        String timePosted = "Posted At: ";
        String content = "Content: ";
        String like = "Like: ";
        String comment = "Comment: ";
        String views = "Views: ";
        String share = "Share: ";
        String reShare = "ReShare: ";
        String options = "Options: ";

        //Length of predifined texts
        int headerLength = pageHeader.length();
        int discriptionLength = pageDiscription.length();
        int whoPostedLength = whoPosted.length();
        int timePostedLength = timePosted.length();
        int contentLength = content.length();
        int likeLength = like.length();
        int commentLength = comment.length();
        int viewsLength = views.length();
        //int shareLength = share.length();
        int reShareLength = reShare.length();
        int optionsLength = options.length();

        //Options sections Texts
        String option1 = "1)Comment";
        String option2 = "2)Like";
        String option3 = "3)ReShare";
        String option4 = "4)Profile";
        String option5 = "5)Report";
        String option6 = "6)Share";
        String option7 = "7)Previous";
        String option8 = "8)Next ";

        //Letgth of option table lengths
        int option1Length = option1.length();
        int option2Length = option2.length();
        int option3Length = option3.length();
        int option4Length = option4.length();
        int option5Length = option5.length();
        int option6Length = option6.length();
        int option7Length = option7.length();
        int option8Length = option8.length();

        if (buffer.isEmpty()) {
            System.out.println("No posts available.");
            CommonMethods.pressEnterToContinue();
            return;
        }

        int postId = buffer.index(bufferIndex);

        //Fetched Data From DataBase
        String userName = postdb.getPostUsername(postId);
        String postTime = postdb.getPostTime(postId);
        String postContent = postdb.getPostContent(postId);
        int postCommentsCount = postdb.getPostCommentsCount(postId);
        int postLikesCount = postdb.getPostLikesCount(postId);
        int postResharesCount = postdb.getPostResharesCount(postId);
        int postViewCount = postdb.getPostViewCount(postId);

        //Fetched Data Length
        int userNameLength = userName.length();
        int postTimeLength = postTime.length();
        int postContentLength = postContent.length();
        int postCommentsCountLength = String.valueOf(postCommentsCount).length();
        int postLikesCountLength = String.valueOf(postLikesCount).length();
        int postResharesCountLength = String.valueOf(postResharesCount).length();
        int postViewCountLength = String.valueOf(postViewCount).length();

        //Update View Count
        postdb.updatePostViewCount(postId);

        // Header Section
        System.out.println("-".repeat(boxLength));
        System.out.println(" ".repeat((boxLength - headerLength) / 2) + pageHeader);
        System.out.println(" ".repeat((boxLength - discriptionLength) / 2) + pageDiscription);
        System.out.println("-".repeat(boxLength));
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // User and Post Time
        int userTimeTotalWidth = whoPostedLength + userNameLength + timePostedLength + postTimeLength;
        int userTimePadding = spaceLeftForContent - userTimeTotalWidth;
        int userTimeSeparator = Math.max(1, userTimePadding / 3);
        System.out.println(border +
                whoPosted + userName +
                " ".repeat(userTimeSeparator) +
                timePosted + postTime +
                " ".repeat(spaceLeftForContent - userTimeTotalWidth - userTimeSeparator) +
                border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);
        System.out.println("-".repeat(boxLength));

        // Post Content
        System.out.println(border + content + " ".repeat(spaceLeftForContent - contentLength) + border);
        CommonMethods.paragraphDisplay(postContent, border, spaceLeftForContent);
        System.out.println(border + "-".repeat(spaceLeftForContent) + border);

        // Likes and Comments
        int stats1TotalWidth = commentLength + postCommentsCountLength + likeLength + postLikesCountLength;
        int stats1Padding = spaceLeftForContent - stats1TotalWidth;
        int stats1Separator = Math.max(1, stats1Padding / 3);
        System.out.println(border +
                comment + postCommentsCount +
                " ".repeat(stats1Separator) +
                like + postLikesCount +
                " ".repeat(spaceLeftForContent - stats1TotalWidth - stats1Separator) +
                border
        );

        // Reshares and Views
        int stats2TotalWidth = reShareLength + postResharesCountLength + viewsLength + postViewCountLength;
        int stats2Padding = spaceLeftForContent - stats2TotalWidth;
        int stats2Separator = Math.max(1, stats2Padding / 3);
        System.out.println(border +
                reShare + postResharesCount +
                " ".repeat(stats2Separator) +
                views + postViewCount +
                " ".repeat(spaceLeftForContent - stats2TotalWidth - stats2Separator) +
                border
        );

        System.out.println(border + "-".repeat(spaceLeftForContent) + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Option Rows (2 options per row)
        printOptionRow(option1, option1Length, option2, option2Length, border, spaceLeftForContent);
        printOptionRow(option3, option3Length, option4, option4Length, border, spaceLeftForContent);
        printOptionRow(option5, option5Length, option6, option6Length, border, spaceLeftForContent);
        printOptionRow(option7, option7Length, option8, option8Length, border, spaceLeftForContent);

        System.out.println("-".repeat(boxLength));
    }


    public static void main(String[] args) {
        SearchPage sp = new SearchPage();
        sp.Search();
    }
}

