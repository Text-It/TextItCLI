package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;

import java.util.Scanner;

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
        //int userId = Integer.parseInt(args[0]);
        int postId = 6; //find a way to fetch  ids one by one from post
        int boxLength = 70;
        String border = "||";
        int spaceLeftForContent = boxLength - border.length() * 2;

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
        String option8 = "8)Next";

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

            System.out.println("-".repeat(boxLength));
            System.out.println(" ".repeat((boxLength - headerLength) / 2) + pageHeader + " ".repeat((boxLength - headerLength) / 2));
            System.out.println(" ".repeat((boxLength - discriptionLength) / 2) + pageDiscription + " ".repeat((boxLength - discriptionLength) / 2));
            System.out.println("-".repeat(boxLength));
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);

            String separator = " ".repeat((spaceLeftForContent - userNameLength - postTimeLength - whoPostedLength - timePostedLength) / 3);

            System.out.println(border + separator + whoPosted + userName + separator + timePosted + postTime + separator + border);
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);
            System.out.println("-".repeat(boxLength));
            System.out.println(border + content + " ".repeat(spaceLeftForContent - contentLength) + border);
            CommonMethods.paragraphDisplay(postContent, border, spaceLeftForContent);
            System.out.println(border + "-".repeat(spaceLeftForContent) + border);

            separator = " ".repeat((spaceLeftForContent - postCommentsCountLength - postLikesCountLength - commentLength - likeLength) / 3);
            System.out.println(border + separator + comment + postCommentsCount + separator + like + postLikesCount + separator + border);

            separator = " ".repeat((spaceLeftForContent - postResharesCountLength - postViewCountLength - reShareLength - viewsLength) / 3);
            System.out.println(border + separator + reShare + postResharesCount + separator + views + postViewCount + separator + border);
            System.out.println(border + "-".repeat(spaceLeftForContent) + border);
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);
            System.out.println(border + options + " ".repeat(spaceLeftForContent - optionsLength) + border);

            separator = " ".repeat((spaceLeftForContent - option1Length - option2Length) / 3);
            System.out.println(border + separator + option1 + separator + option2 + separator + border);

            separator = " ".repeat((spaceLeftForContent - option3Length - option4Length) / 3);
            System.out.println(border + separator + option3 + separator + option4 + separator + border);

            separator = " ".repeat((spaceLeftForContent - option5Length - option6Length) / 3);
            System.out.println(border + separator + option5 + separator + option6 + separator + border);

            separator = " ".repeat((spaceLeftForContent - option7Length - option8Length) / 3);
            System.out.println(border + separator + option7 + separator + option8 + separator + border);
            System.out.println("-".repeat(boxLength));

            System.out.println("Select an option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    CommonMethods.openInNewCMD("com.TextIt.UI.CommentPage " + userID + " " + postId);
                    break;
                case 2:
                    System.out.println("Liking...");
                    likePost(userID, postId);
                    break;
                case 3:
                    System.out.println("ReSharing...");
                    reSharePost(userID,postId,postContent);
                    break;
                case 4:
                    CommonMethods.openInNewCMD("com.TextIt.UI.ProfilePage " + postdb.getUserId(postId));
                    break;
                case 5:
                    System.out.println("Reporting...");
                    break;
                case 6:
                    System.out.println("ShareCode is: " + postdb.getShareCode(postId));
                    break;
                case 7:
                    System.out.println("Going to previous post...");
                    break;
                case 8:
                    System.out.println("Going to next post...");
            }
        }

    }


    public static boolean likePost(int userid , int postid){

        if(likedb.incrementLikesCount(userid,postid)){
            return true;
        }
        System.out.println("Error in liking post. Please try again later.");
        return false;
    }

    public static boolean reSharePost(int userid , int postid , String content){

        String shareCode = userdb.getUserName(userid) + (int) (Math.random() * 1000000000);

        if (resharedb.reSharePost(postid,userid)){
            if(postdb.insertPost(userid,content,shareCode)){
                return true;
            }
        }

        System.out.println("Error in resharing post. Please try again later.");
        return false;

    }
}
