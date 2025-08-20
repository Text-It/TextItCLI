package com.TextIt.UI;

import com.TextIt.database.DataBase;

import java.sql.*;

public class InboxPage {
    private static final DataBase dataBase = new DataBase();
    private static final DataBase.UserData userDb = dataBase.new UserData();

    public static void main(String[] args) throws SQLException {
        int userId = Integer.parseInt(args[0]);
        Connection conn = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsername(), dataBase.getPassword());
        String query1 = "Select by_user_id,type, ref_id, created_at from notifications WHERE to_user_id = ? And seen = false ORDER BY created_at DESC";
        PreparedStatement ps = conn.prepareStatement(query1);

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getString("type").equalsIgnoreCase("message")) {

                String query = "Select message from messages where id = ? ";
                ps = conn.prepareStatement(query);
                ps.setInt(1, rs.getInt("ref_id"));
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                System.out.println("<NOTIFICATION> " + userDb.getUserName(rs.getInt("by_user_id")) + " send" + rs.getString("type") + " " + rs2.getString("message") +
                        " to You " + rs.getTimestamp("created_at"));
            }

        if (rs.getString("type").equalsIgnoreCase("comments")) {
            while (rs.next()) {
                String query = "Select content from comments where userid = ? ";
                ps = conn.prepareStatement(query);
                ps.setInt(1, rs.getInt("ref_id"));
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                System.out.println("<NOTIFICATION> " + userDb.getUserName(rs.getInt("by_user_id")) + " Commented "+ " " + rs2.getString("content") +
                        " on  your Post" + rs.getTimestamp("created_at"));
                }
            }
        }

    }
}




