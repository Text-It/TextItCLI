package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.service.user.UserData;
import com.TextIt.model.utils.CommonMethods;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;


public class SearchPage {

    private HashMap<String, UserData> userMap;
    private DataBase database;
    private Scanner scanner;
    public SearchPage() {
        this.userMap = new HashMap<>();
        this.database = new DataBase();
        this.scanner = new Scanner(System.in);
        loadUser();
    }


    private void loadUser() {
        if (!database.isServerReachable()) {
            System.out.println(CommonMethods.color("Cannot connect to database. Search functionality unavailable.", CommonMethods.RED));
            return;
        }

        String query = "SELECT user_id, first_name, last_name, username, email FROM users";

        try (Connection conn = DriverManager.getConnection(database.getUrl(), database.getUsername(), database.getPassword())) {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            int userCount = 0;
            while (rs.next()) {
                String username = rs.getString("username").toLowerCase().trim();
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");

                String fullName = firstName + " " + lastName;
                UserData userData = new UserData(username, fullName, email);

                userMap.put(username, userData);
                userCount++;
            }

            System.out.println(CommonMethods.color("Loaded " + userCount + " users into search index.", CommonMethods.GREEN));

        } catch (SQLException e) {
            System.out.println(CommonMethods.color("Error loading users: " + e.getMessage(), CommonMethods.RED));
        }
    }


    public UserData searchByUserName(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println(CommonMethods.color("Username cannot be empty.", CommonMethods.RED));
            return null;
        }

        String searchKey = username.toLowerCase().trim();

        UserData foundUser = userMap.get(searchKey);

        if (foundUser != null) {
            System.out.println(CommonMethods.color("User found!", CommonMethods.GREEN));
            displayUser(foundUser);
        } else {
            System.out.println(CommonMethods.color(" No user found with username: " + username, CommonMethods.RED));
        }

        return foundUser;
    }
    private void displayUser(UserData user) {

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
            System.out.println(CommonMethods.color("3. Back to main menu", CommonMethods.RED));
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
        userMap.clear();
        loadUser();
        System.out.println(CommonMethods.color(" User data refreshed successfully!", CommonMethods.GREEN));
    }

    public int getUserCount() {
        return userMap.size();
    }

    public static void main(String[] args) {
        SearchPage sp = new SearchPage();
        sp.Search();
    }
}

