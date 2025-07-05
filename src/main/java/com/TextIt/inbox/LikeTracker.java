package com.TextIt.inbox;

import java.sql.*;

public class LikeTracker extends Thread {

    @Override
    public void run() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "";
        String databaseusename = "";
        String databasepassword = "";
        String previousLike = "select likecount from previousrecords where userid = provideduser and postid = provided postid ";
        String newnotification = "select   wholikeuserid , postid from likedatabase where userid ='' and postid ='' and likeid>previouslikecount ";

        try (Connection liketrack = DriverManager.getConnection(url, databaseusename, databasepassword)) {

            PreparedStatement newLike = liketrack.prepareStatement(previousLike);

            ResultSet rs = newLike.executeQuery();
            if (rs.next()) {
                int previousLikeCount = rs.getInt(1);
            }

            PreparedStatement ps = liketrack.prepareStatement(newnotification);
            ResultSet rs2 = ps.executeQuery();

            while (rs2.next()){
                int i = rs2.getInt(1);
                int i2 = rs2.getInt(2);
                InboxHandler.notification(i,i2);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
