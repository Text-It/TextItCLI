package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.data_structure.linked_list.DoublyLinkedList;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;


public class FeedPage {

    //Database connectivity to fetch data
    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdb = db.new UserData();
    private static final DataBase.Post postdb = db.new Post();
    private static final DataBase.Like likedb = db.new Like();
    private static final DataBase.ReShare resharedb = db.new ReShare();

    public static void main(String[] args) {

        //Objects
        Scanner sc = new Scanner(System.in);

        //Important Variables
        int userID = Integer.parseInt(args[0]);
        boolean feedType = Boolean.parseBoolean(args[1]);
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
        int shareLength = share.length();
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


        while (true) {
            CommonMethods.clearConsole(); // ✅ clear screen before each render

            if (buffer.isEmpty()) {
                System.out.println(RED + "No posts available." + RESET);
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
            System.out.println(BRIGHT_CYAN + BOLD + "-".repeat(boxLength) + RESET);
            System.out.println(" ".repeat((boxLength - headerLength) / 2) + BRIGHT_WHITE + BOLD + pageHeader + RESET);
            System.out.println(" ".repeat((boxLength - discriptionLength) / 2) + BRIGHT_CYAN + pageDiscription + RESET);
            System.out.println(BRIGHT_CYAN + BOLD + "-".repeat(boxLength) + RESET);
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);

            // User and Post Time
            int userTimeTotalWidth = whoPostedLength + userNameLength + timePostedLength + postTimeLength;
            int userTimePadding = spaceLeftForContent - userTimeTotalWidth;
            int userTimeSeparator = Math.max(1, userTimePadding / 3);
            System.out.println(border + 
                YELLOW + whoPosted + RESET + BLUE + userName + RESET + 
                " ".repeat(userTimeSeparator) + 
                YELLOW + timePosted + RESET + BRIGHT_WHITE + postTime + RESET + 
                " ".repeat(spaceLeftForContent - userTimeTotalWidth - userTimeSeparator) + 
                border
            );
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);
            System.out.println(GRAY + "-".repeat(boxLength) + RESET);

            // Post Content
            System.out.println(border + YELLOW + content + RESET + " ".repeat(spaceLeftForContent - contentLength) + border);
            CommonMethods.paragraphDisplay(postContent, border, spaceLeftForContent);
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);
            System.out.println(border + GRAY + "-".repeat(spaceLeftForContent) + RESET + border);

            // Likes and Comments
            int stats1TotalWidth = commentLength + postCommentsCountLength + likeLength + postLikesCountLength;
            int stats1Padding = spaceLeftForContent - stats1TotalWidth;
            int stats1Separator = Math.max(1, stats1Padding / 3);
            System.out.println(border + 
                YELLOW + comment + RESET + GREEN + postCommentsCount + RESET + 
                " ".repeat(stats1Separator) + 
                YELLOW + like + RESET + BRIGHT_RED + postLikesCount + RESET + 
                " ".repeat(spaceLeftForContent - stats1TotalWidth - stats1Separator) + 
                border
            );

            // Reshares and Views
            int stats2TotalWidth = reShareLength + postResharesCountLength + viewsLength + postViewCountLength;
            int stats2Padding = spaceLeftForContent - stats2TotalWidth;
            int stats2Separator = Math.max(1, stats2Padding / 3);
            System.out.println(border + 
                YELLOW + reShare + RESET + BRIGHT_PURPLE + postResharesCount + RESET + 
                " ".repeat(stats2Separator) + 
                YELLOW + views + RESET + BRIGHT_CYAN + postViewCount + RESET + 
                " ".repeat(spaceLeftForContent - stats2TotalWidth - stats2Separator) + 
                border
            );
            
            System.out.println(border + GRAY + "-".repeat(spaceLeftForContent) + RESET + border);
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);
            
            // Options Header
            System.out.println(border + BRIGHT_YELLOW + BOLD + options + RESET + " ".repeat(spaceLeftForContent - optionsLength) + border);

            // Option Rows (2 options per row)
            printOptionRow(option1, option1Length, option2, option2Length, border, spaceLeftForContent);
            printOptionRow(option3, option3Length, option4, option4Length, border, spaceLeftForContent);
            printOptionRow(option5, option5Length, option6, option6Length, border, spaceLeftForContent);
            printOptionRow(option7, option7Length, option8, option8Length, border, spaceLeftForContent);
            
            System.out.println(BRIGHT_CYAN + BOLD + "-".repeat(boxLength) + RESET);

            System.out.print(GREEN + "Select an option: " + RESET);
            int option;

            try {
                option = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println(RED + "Invalid option, try again!" + RESET);
                continue;
            }finally {
                sc.nextLine();
            }
            switch (option) {
                case 1:
                    CommonMethods.openInNewCMD("com.TextIt.UI.CommentPage " + userID + " " + postId);
                    break;
                case 2:
                    System.out.println(YELLOW + "Liking..." + RESET);
                    if (!likePost(userID, postId)) CommonMethods.pressEnterToContinue();

                    break;
                case 3:
                    System.out.println(YELLOW + "ReSharing..." + RESET);
                    if (!reSharePost(userID, postId, postContent)) CommonMethods.pressEnterToContinue();

                    break;
                case 4:
                    CommonMethods.openInNewCMD("com.TextIt.UI.ProfilePage " + postdb.getUserId(postId));
                    break;
                case 5:
                    System.out.println(YELLOW + "Reporting..." + RESET);
                    CommonMethods.pressEnterToContinue();
                    break;
                case 6:
                    System.out.println(BLUE + "ShareCode is: " + RESET + BRIGHT_PURPLE + postdb.getShareCode(postId) + RESET);
                    CommonMethods.pressEnterToContinue();
                    break;
                case 7: // Previous Post
                    if (bufferIndex > 0) {
                        bufferIndex--;
                    } else if (offset > 0) {
                        offset -= pageSize;
                        if (feedType) {
                            buffer = postdb.getPostIds(pageSize, offset);
                        } else {
                            buffer = postdb.getPostIdsforParticularUser(pageSize, offset, userID);
                        }
                        bufferIndex = buffer.size() - 1;
                    } else {
                        System.out.println(YELLOW + "Already at the first post." + RESET);
                        CommonMethods.pressEnterToContinue();
                    }
                    break;
                case 8: // Next Post
                    if (bufferIndex < buffer.size() - 1) {
                        bufferIndex++;
                    } else {
                        offset += pageSize;
                        DoublyLinkedList<Integer> nextBatch;
                        if (feedType) {
                            nextBatch = postdb.getPostIds(pageSize, offset);
                        } else {
                            nextBatch = postdb.getPostIdsforParticularUser(pageSize, offset, userID);
                        }
                        if (!nextBatch.isEmpty()) {
                            buffer = nextBatch;
                            bufferIndex = 0;
                        } else {
                            System.out.println(YELLOW + "No more posts." + RESET);
                            CommonMethods.pressEnterToContinue();
                            offset -= pageSize;
                        }
                    }
                    break;
            }
        }
    }

    private static boolean likePost(int userid, int postid) {
        if (likedb.incrementLikesCount(userid, postid)) {
            System.out.println(GREEN + "✓ Post liked successfully!" + RESET);
            return true;
        }
        System.out.println(RED + "Error in liking post. Please try again later." + RESET);
        return false;
    }
    
    private static boolean reSharePost(int userid, int postid, String content) {
        if (resharedb.reSharePost(postid, userid)) {
            System.out.println(GREEN + "✓ Post reshared successfully!" + RESET);
            return true;
        }
        System.out.println(RED + "Error in resharing post. Please try again later." + RESET);
        return false;
    }
    
    private static void printOptionRow(String option1, int option1Length, 
                                     String option2, int option2Length,
                                     String border, int spaceLeftForContent) {
        int totalWidth = option1Length + option2Length;
        int padding = spaceLeftForContent - totalWidth;
        int separator = Math.max(1, padding / 3);
        
        System.out.println(border + 
            CYAN + option1 + RESET + 
            " ".repeat(separator) + 
            CYAN + option2 + RESET + 
            " ".repeat(spaceLeftForContent - totalWidth - separator) + 
            border
        );
    }
}
