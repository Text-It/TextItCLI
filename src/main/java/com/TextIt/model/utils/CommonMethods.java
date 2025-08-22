package com.TextIt.model.utils;

import com.TextIt.database.DataBase;
import com.TextIt.service.data_structure.linked_list.DoublyLinkedList;

import java.io.File;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CommonMethods {

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static final String BLACK = "\u001B[30m";
    public static final String GRAY = "\u001B[90m";


    public static final String CLEAR_SCREEN = "\u001B[2J\u001B[H";


    //Objects
    static Scanner scanner = new Scanner(System.in);
    private static final DataBase db = new DataBase();
    public static final DataBase.UserData userdata =db.new UserData();
    private static final DataBase.Post userpost =db.new Post();
    private static final DataBase.UserFollows userfollows =db.new UserFollows();

    public static void printDivider() {
        System.out.println(BRIGHT_CYAN + "-".repeat(80) + RESET);
    }
    public static void printChoice(int number, String description, String color) {
        System.out.println(BRIGHT_YELLOW + number + ". " + color + description + RESET);
    }


    public static void pressEnterToContinue() {
        System.out.println(PURPLE + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    public static String color(String text, String color) {
        return color + text + RESET;
    }

    public static void openInNewCMD(String className, String... args) {
        try {
            // Get java executable and working directory
            String javaBin = System.getProperty("java.home") + "\\bin\\java";
            String workingDir = System.getProperty("user.dir");

            // Build classpath: include compiled classes and all jars in target/dependency
            String classpath = "\"" + workingDir + "\\target\\classes\"";

            File depDir = new File(workingDir + "\\target\\dependency");
            if (depDir.exists()) {
                File[] jars = depDir.listFiles((dir, name) -> name.endsWith(".jar"));
                if (jars != null) {
                    for (File jar : jars) {
                        classpath += ";" + "\"" + jar.getAbsolutePath() + "\"";
                    }
                }
            }

            // Prepare argument string
            String argString = String.join(" ", args);

            // Final Java command
            String command = String.format("\"%s\" -cp %s %s %s", javaBin, classpath, className, argString);

            // Open in new CMD window
            new ProcessBuilder("cmd", "/c", "start", "cmd", "/k", "title " + className + " && " + command).directory(new File(workingDir)).inheritIO().start();

        } catch (Exception e) {
            System.err.println("❌ Failed to launch " + className);
            e.printStackTrace();
        }
    }

    public static void paragraphDisplay(String text, String borderDesign, int boxWidth) {
        if (text == null || text.trim().isEmpty()) {
            System.out.println(borderDesign + " ".repeat(boxWidth) + borderDesign);
            return;
        }

        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder(borderDesign);
        int currentLineLength = borderDesign.length();
        int spaceLeft = boxWidth - borderDesign.length();

        for (String word : words) {
            // If adding this word would exceed the line length, start a new line
            if (currentLineLength + word.length()  > boxWidth) {
                // Fill the remaining space with spaces
                while (currentLineLength -1 < boxWidth) {
                    line.append(" ");
                    currentLineLength++;
                }
                // Add the right border and start a new line
                System.out.println(line + borderDesign);
                line = new StringBuilder(borderDesign);
                currentLineLength = borderDesign.length();
            }
            
            // Add the word to the current line
            if (currentLineLength > borderDesign.length()) {
                line.append(" ");
                currentLineLength++;
            }
            line.append(word);
            currentLineLength += word.length();
        }

        // Handle the last line
        if (currentLineLength > borderDesign.length()) {
            while (currentLineLength -1 < boxWidth) {
                line.append(" ");
                currentLineLength++;
            }
            System.out.println(line + borderDesign);
        }
    }
    public static void userProfile(int userId) {
        int boxLength = 70;
        String border = "||";
        int spaceLeftForContent = boxLength - border.length() * 2;

        // ===== Page Headers =====
        String pageHeader = userdata.getUserName(userId) + "'s Profile";
        String pageDescription = "Welcome to " + userdata.getUserName(userId) + "'s space";

        // ===== Section Labels =====
        String realNameLabel = "Name: ";
        String usernameLabel = "Username: ";
        String genderLabel = "Gender: ";
        String locationLabel = "Location: ";
        String bioLabel = "BIO: ";
        String memberSinceLabel = "Member Since: ";
        String postsLabel = "Posts: ";
        String followingLabel = "Following: ";
        String followersLabel = "Followers: ";
        String xpLabel = "XP: ";
        String levelLabel = "Level: ";
        String shareLabel = "Share: ";

        // ===== Lengths =====
        int headerLength = pageHeader.length();
        int descriptionLength = pageDescription.length();
        int realNameLength = realNameLabel.length();
        int usernameLength = usernameLabel.length();
        int genderLength = genderLabel.length();
        int locationLength = locationLabel.length();
        int bioLabelLength = bioLabel.length();
        int memberSinceLength = memberSinceLabel.length();
        int postsLength = postsLabel.length();
        int followingLength = followingLabel.length();
        int followersLength = followersLabel.length();
        int xpLength = xpLabel.length();
        int levelLength = levelLabel.length();
        int shareLength = shareLabel.length();

        // ===== User Data =====
        String realName = userdata.getRealName(userId);
        String username = "@" + userdata.getUserName(userId);
        String gender = userdata.getGender(userId);
        String location = userdata.getLocation(userId);
        String bio = userdata.getBio(userId);
        String memberSince = userdata.getMemberSince(userId); // int → string
        String posts = String.valueOf(userpost.getPostCount(userId));
        String following = String.valueOf(userfollows.getFollowingCount(userId));
        String followers = String.valueOf(userfollows.getFollowersCount(userId));
        String xp = String.valueOf(userdata.getXP(userId));
        String level = String.valueOf(userdata.getLevel(userId));
        String shareCode = userdata.getUserShareCode(userId);

        // ===== Rendering =====
        CommonMethods.clearConsole();
        System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);
        System.out.println(" ".repeat((boxLength - headerLength) / 2) + BRIGHT_WHITE + BOLD + pageHeader + RESET);
        System.out.println(" ".repeat((boxLength - descriptionLength) / 2) + BRIGHT_CYAN + pageDescription + RESET);
        System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Real Name + Username
        int totalContentWidth = realNameLabel.length() + realName.length() + usernameLabel.length() + username.length();
        int totalPadding = spaceLeftForContent - totalContentWidth;
        int separatorWidth = Math.max(1, totalPadding / 3);
        String separator = " ".repeat(separatorWidth);
        System.out.println(
                border +
                YELLOW + realNameLabel + RESET + BRIGHT_WHITE + realName + RESET +
                separator + YELLOW + usernameLabel + RESET + BLUE + username + RESET +
                " ".repeat(spaceLeftForContent - totalContentWidth - separatorWidth) + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Gender + Location
        int genderLocationTotalWidth = genderLabel.length() + gender.length() + locationLabel.length() + location.length();
        int genderLocationPadding = spaceLeftForContent - genderLocationTotalWidth;
        int genderLocationSeparator = Math.max(1, genderLocationPadding / 3);
        System.out.println(
                border +
                YELLOW + genderLabel + RESET + BRIGHT_WHITE + gender + RESET +
                " ".repeat(genderLocationSeparator) + YELLOW + locationLabel + RESET + BRIGHT_WHITE + location + RESET +
                " ".repeat(spaceLeftForContent - genderLocationTotalWidth - genderLocationSeparator) + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Bio
        System.out.println(border + YELLOW + bioLabel + RESET + " ".repeat(spaceLeftForContent - bioLabelLength) + border);
        CommonMethods.paragraphDisplay(bio, border, spaceLeftForContent);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);
        System.out.println(border + GRAY + "-".repeat(spaceLeftForContent) + RESET + border);
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Member Since
        System.out.println(
                border + YELLOW + memberSinceLabel + RESET + BRIGHT_PURPLE + memberSince + RESET +
                " ".repeat(spaceLeftForContent - (memberSinceLength + memberSince.length())) + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Posts / Following / Followers
        int statsTotalWidth = postsLabel.length() + posts.length() + followingLabel.length() + 
                            following.length() + followersLabel.length() + followers.length();
        int statsPadding = spaceLeftForContent - statsTotalWidth;
        int statsSeparator = Math.max(1, statsPadding / 4);
        System.out.println(
                border +
                YELLOW + postsLabel + RESET + GREEN + posts + RESET +
                " ".repeat(statsSeparator) + YELLOW + followingLabel + RESET + GREEN + following + RESET +
                " ".repeat(statsSeparator) + YELLOW + followersLabel + RESET + GREEN + followers + RESET +
                " ".repeat(spaceLeftForContent - statsTotalWidth - (2 * statsSeparator)) + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // XP + Level
        int xpLevelTotalWidth = xpLabel.length() + xp.length() + levelLabel.length() + level.length();
        int xpLevelPadding = spaceLeftForContent - xpLevelTotalWidth;
        int xpLevelSeparator = Math.max(1, xpLevelPadding / 3);
        System.out.println(
                border +
                YELLOW + xpLabel + RESET + BRIGHT_GREEN + xp + RESET +
                " ".repeat(xpLevelSeparator) + YELLOW + levelLabel + RESET + BRIGHT_GREEN + level + RESET +
                " ".repeat(spaceLeftForContent - xpLevelTotalWidth - xpLevelSeparator) + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        // Share Code
        System.out.println(
                border + YELLOW + shareLabel + RESET + BRIGHT_PURPLE + shareCode + RESET +
                " ".repeat(spaceLeftForContent - (shareLength + shareCode.length())) + border
        );
        System.out.println(border + " ".repeat(spaceLeftForContent) + border);

        System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);
    }


    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Unable to clear console: " + e.getMessage());
        }
    }
    public static void editProfile(int userId) {
        boolean editing = true;
        clearConsole();

        while (editing) {
            // Fetch latest values from DB
            String firstName = userdata.getFirstName(userId);
            String lastName = userdata.getLastName(userId);
            String username = userdata.getUserName(userId);
            String gender = userdata.getGender(userId);
            String location = userdata.getLocation(userId);
            String bio = userdata.getBio(userId);

            int boxLength = 70;
            String border = "||";
            int spaceLeftForContent = boxLength - border.length() * 2;

            String pageHeader = "Edit Profile";
            int headerLength = pageHeader.length();

            // Render Profile Edit Menu
            System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);
            System.out.println(" ".repeat((boxLength - headerLength) / 2) + BRIGHT_WHITE + BOLD + pageHeader + RESET + " ".repeat((boxLength - headerLength) / 2));
            System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);

            System.out.println(border + " " + YELLOW + "1)" + RESET + " " + BLUE + "First Name" + RESET + " : " + BRIGHT_WHITE + firstName + RESET + " ".repeat(spaceLeftForContent - 15 - firstName.length()) + border);
            System.out.println(border + " " + YELLOW + "2)" + RESET + " " + BLUE + "Last Name" + RESET + "  : " + BRIGHT_WHITE + lastName + RESET + " ".repeat(spaceLeftForContent - 15 - lastName.length()) + border);
            System.out.println(border + " " + YELLOW + "3)" + RESET + " " + BLUE + "Username" + RESET + "   : " + BRIGHT_WHITE + username + RESET + " ".repeat(spaceLeftForContent - 15 - username.length()) + border);
            System.out.println(border + " " + YELLOW + "4)" + RESET + " " + BLUE + "Gender" + RESET + "     : " + BRIGHT_WHITE + gender + RESET + " ".repeat(spaceLeftForContent - 15 - gender.length()) + border);
            System.out.println(border + " " + YELLOW + "5)" + RESET + " " + BLUE + "Location" + RESET + "   : " + BRIGHT_WHITE + location + RESET + " ".repeat(spaceLeftForContent - 15 - location.length()) + border);
            System.out.println(border + " " + YELLOW + "6)" + RESET + " " + BLUE + "Bio" + RESET + "        : " + BRIGHT_WHITE + bio + RESET + " ".repeat(spaceLeftForContent - 15 - bio.length()) + border);
            System.out.println(GRAY + "-".repeat(boxLength) + RESET);
            System.out.println(border + " " + YELLOW + "7)" + RESET + " " + RED + "Exit" + RESET + " " + " ".repeat(spaceLeftForContent - 7) + border);
            System.out.println(BRIGHT_CYAN + BOLD + "=".repeat(boxLength) + RESET);

            // User choice
            System.out.print(GREEN + "Enter the number of the field you want to edit: " + RESET);
            int choice;

            try {
                choice = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println(RED + "Invalid choice. Please try again." + RESET);
                continue;
            }finally {
                scanner.nextLine();
            }

            switch (choice) {
                case 1 -> {
                    System.out.print(BLUE + "Enter new First Name: " + RESET);
                    String newFirst = scanner.nextLine();
                    if(userdata.updateFirstName(userId, newFirst)){
                    System.out.println(GREEN + "First name updated!" + RESET);
                    }
                }
                case 2 -> {
                    System.out.print(BLUE + "Enter new Last Name: " + RESET);
                    String newLast = scanner.nextLine();
                    if(userdata.updateLastName(userId, newLast)){
                    System.out.println(GREEN + "Last name updated!" + RESET);}
                }
                case 3 -> {
                    System.out.print(BLUE + "Enter new Username: " + RESET);
                    String newUsername = scanner.nextLine();
                    if (userdata.updateUserName(userId, newUsername)){
                    System.out.println(GREEN + "Username updated!" + RESET);}
                }
                case 4 -> {
                    System.out.print(BLUE + "Enter new Gender: " + RESET);
                    String newGender = scanner.nextLine();
                    if(userdata.updateGender(userId, newGender)) {
                        System.out.println(GREEN + "Gender updated!" + RESET);
                    }
                }
                case 5 -> {
                    System.out.print(BLUE + "Enter new Location: " + RESET);
                    String newLocation = scanner.nextLine();
                    if(userdata.updateLocation(userId, newLocation)){
                    System.out.println(GREEN + "Location updated!" + RESET);}
                }
                case 6 -> {
                    System.out.print(BLUE + "Enter new Bio: " + RESET);
                    String newBio = scanner.nextLine();
                    if(userdata.updateBio(userId, newBio)){
                    System.out.println(GREEN + "Bio updated!" + RESET);}
                }
                case 7 -> {
                    System.out.println(YELLOW + "Exiting Edit Profile." + RESET);
                    editing = false;
                    return;
                }
                default -> System.out.println(RED + "Invalid choice. Please try again." + RESET);
            }
        }
    }

    public static int featchIdForNotification(String content , String type) throws SQLException {
        DataBase db = new DataBase();
        try (Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword())) {
            if (type.equalsIgnoreCase("message")) {
                PreparedStatement ps = conn.prepareStatement("select id from messages where  message= ? ");
                ps.setString(1, content);
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt(1);
            } else if (type.equalsIgnoreCase("Comments")) {
                PreparedStatement ps = conn.prepareStatement("select c_id from comments where content = ? ");
                ps.setString(1, content);
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt(1);
            } else if (type.equalsIgnoreCase("like")) {
                PreparedStatement ps = conn.prepareStatement("select like_id from likes where user_id = ? ");
                ps.setString(1, content);
                ResultSet rs = ps.executeQuery();
                rs.next();
                return rs.getInt(1);

            } else {
                System.out.println("Unsupported notification type: " + type);
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static void viewPost(String userid , String feedtype){
         DataBase db = new DataBase();
         DataBase.UserData userdb = db.new UserData();
         DataBase.Post postdb = db.new Post();
         DataBase.Like likedb = db.new Like();
         DataBase.ReShare resharedb = db.new ReShare();
        CommonMethods.clearConsole(); // ✅ clear screen before each render


        int userID = Integer.parseInt(userid);
        boolean feedType = Boolean.parseBoolean(feedtype);
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
    }



}
