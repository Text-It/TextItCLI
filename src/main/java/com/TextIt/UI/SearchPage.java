package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.service.data_structure.Hash_Map.HashMapp;
import com.TextIt.model.utils.CommonMethods;
import java.sql.*;
import java.util.Scanner;


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


    public static void main(String[] args) {
        SearchPage sp = new SearchPage();
        sp.Search();
    }
}

