package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;

public class FeedPage {

    public static void main(String[] args) {

        //Database connectivity to fetch data
        DataBase db = new DataBase();
        DataBase.UserData userdb = db.new UserData();
        DataBase.Post postdb = db.new Post();

        //Important Variables
        int userId = 4;
        //int userId = Integer.parseInt(args[0]);
        int postId = 1; //find a way to fetch  ids one by one from post
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


        while (true) {

            System.out.println("-".repeat(boxLength));
            System.out.println(" ".repeat((boxLength - headerLength) / 2) + pageHeader + " ".repeat((boxLength - headerLength) / 2));
            System.out.println(" ".repeat((boxLength - discriptionLength) / 2) + pageDiscription + " ".repeat((boxLength - discriptionLength) / 2));
            System.out.println("-".repeat(boxLength));
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);
            System.out.println(border + " ".repeat((spaceLeftForContent - postdb.getPostUsername(postId).length() - postdb.getPostTime(postId).length() - whoPostedLength - timePostedLength) / 3) + whoPosted + postdb.getPostUsername(postId) + " ".repeat((spaceLeftForContent - postdb.getPostUsername(postId).length() - postdb.getPostTime(postId).length() - whoPostedLength - timePostedLength) / 3) + timePosted + postdb.getPostTime(postId) + " ".repeat((spaceLeftForContent - postdb.getPostUsername(postId).length() - postdb.getPostTime(postId).length() - whoPostedLength - timePostedLength) / 3) + border);
            System.out.println(border + " ".repeat(spaceLeftForContent) + border);
            System.out.println("-".repeat(boxLength));
            System.out.println(border + content + " ".repeat(spaceLeftForContent-contentLength) + border);
            CommonMethods.paragraphDisplay(postdb.getPostContent(postId),border,spaceLeftForContent);
            System.out.println(border + "-".repeat(spaceLeftForContent) + border);
            System.out.println(border + " ".repeat((spaceLeftForContent - postdb.getPostCommentsCount(postId) - postdb.getPostLikesCount(postId) - commentLength - likeLength) / 3) + comment + postdb.getPostCommentsCount(postId) + " ".repeat((spaceLeftForContent - postdb.getPostCommentsCount(postId) - postdb.getPostLikesCount(postId) - commentLength - likeLength) / 3) + like + postdb.getPostLikesCount(postId) + " ".repeat((spaceLeftForContent - postdb.getPostCommentsCount(postId) - postdb.getPostLikesCount(postId) - commentLength - likeLength) / 3) +border);
            System.out.println(border + " ".repeat((spaceLeftForContent - postdb.getPostResharesCount(postId) - postdb.getPostViewCount(postId) - reShareLength - viewsLength) / 3) + reShare + postdb.getPostResharesCount(postId) + " ".repeat((spaceLeftForContent - postdb.getPostResharesCount(postId) - postdb.getPostViewCount(postId) - reShareLength - viewsLength) / 3) + views + postdb.getPostViewCount(postId) + " ".repeat((spaceLeftForContent - postdb.getPostResharesCount(postId) - postdb.getPostViewCount(postId) - reShareLength - viewsLength) / 3) +border);
            System.out.println(border + "-".repeat(spaceLeftForContent) + border);
        }


    }
}
