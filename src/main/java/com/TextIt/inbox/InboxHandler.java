package com.TextIt.inbox;

import java.sql.*;

public class InboxHandler extends Thread {

    @Override
    public void run() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LikeTracker LT = new LikeTracker();
        LT.start();

        FollowTracker FT = new FollowTracker();
        FT.start();

    }

    static void notification(int id, int postid) {

        String url = "";
        String databaseusename = "";
        String databasepassword = "";
        String previousLike = "select username , postid from userdatabse where userid = ? and postid = ?  ";


        try (Connection liketrack = DriverManager.getConnection(url, databaseusename, databasepassword)) {

            Statement s = liketrack.createStatement();
            ResultSet rs = s.executeQuery(previousLike);
            while (rs.next()){
                System.out.println(rs.getString(1) + "liked your post " + rs.getInt(2) );
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
        System.out.println();
    }
}


