package com.TextIt.UI;

import com.TextIt.database.DataBase;

import java.util.Scanner;

public class Post {
    private static String content;
    private static String shareCode;
    private static Scanner sc = new Scanner(System.in);
    private static final DataBase db = new DataBase();
    private static final DataBase.UserData userdb = db.new UserData();
    private static final DataBase.Post postbd = db.new Post();

    public static void main(String[] args) {

        int userid = Integer.parseInt(args[0]);
        System.out.print( "Enter your post content: ");
        content = sc.nextLine();

        shareCode = userdb.getUserName(userid) + (int) (Math.random() * 1000000000);
        boolean insert = postbd.insertPost(userid, content, shareCode);

        if (insert) {
            System.out.println("Post successfully posted!");
        } else {
            System.out.println("Post failed to post!");
        }
    }
}