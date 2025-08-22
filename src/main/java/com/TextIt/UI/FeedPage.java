package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.data_structure.linked_list.DoublyLinkedList;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.viewPost;


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


        while (true) {
            CommonMethods.clearConsole(); // ✅ clear screen before each render



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

            System.out.println("Select an option: ");
            int option;

            try {
                option = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Invalid option, try again!");
                continue;
            }finally {
                sc.nextLine();
            }
            switch (option) {
                case 1:
                    CommonMethods.openInNewCMD("com.TextIt.UI.CommentPage " + userID + " " + postId);
                    break;
                case 2:
                    System.out.println("Liking...");
                    if (!likePost(userID, postId)) CommonMethods.pressEnterToContinue();

                    break;
                case 3:
                    System.out.println("ReSharing...");
                    if (!reSharePost(userID, postId, postContent)) CommonMethods.pressEnterToContinue();

                    break;
                case 4:
                    CommonMethods.openInNewCMD("com.TextIt.UI.ProfilePage " + postdb.getUserId(postId));
                    break;
                case 5:
                    System.out.println("Reporting...");
                    CommonMethods.pressEnterToContinue();
                    break;
                case 6:
                    System.out.println("ShareCode is: " + postdb.getShareCode(postId));
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
                        System.out.println("Already at the first post.");
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
                            System.out.println("No more posts.");
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
            return true;
        }
        System.out.println("Error in liking post. Please try again later.");
        return false;
    }
    
    private static boolean reSharePost(int userid, int postid, String content) {
        if (resharedb.reSharePost(postid, userid)) {
            return true;
        }
        System.out.println("Error in resharing post. Please try again later.");
        return false;
    }
    
    static void printOptionRow(String option1, int option1Length,
                               String option2, int option2Length,
                               String border, int spaceLeftForContent) {
        int totalWidth = option1Length + option2Length;
        int padding = spaceLeftForContent - totalWidth;
        int separator = Math.max(1, padding / 3);
        
        System.out.println(border + 
            option1 + 
            " ".repeat(separator) + 
            option2 + 
            " ".repeat(spaceLeftForContent - totalWidth - separator) + 
            border
        );
    }
}
