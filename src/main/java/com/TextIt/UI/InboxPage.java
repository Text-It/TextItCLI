package com.TextIt.UI;

import com.TextIt.database.DataBase;

import java.sql.*;

import static com.TextIt.model.utils.CommonMethods.*;

public class InboxPage {
    private static final DataBase dataBase = new DataBase();
    private static final DataBase.UserData userDb = dataBase.new UserData();

    public static void main(String[] args) throws SQLException {
        int userId = Integer.parseInt(args[0].trim());
        Connection conn = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsername(), dataBase.getPassword());
        String query1 = "Select by_user_id,type, ref_id, created_at from notifications WHERE to_user_id = ? And seen = false ORDER BY created_at DESC";
        PreparedStatement ps = conn.prepareStatement(query1);

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            System.out.println(RED + "No Notifications found" + RESET);
            System.exit(0);
        }
        while (rs.next()) {
            if (rs.getString("type").equalsIgnoreCase("message")) {

                String query = "Select message from messages where id = ? ";
                String query2 = "Update notifications set seen =true where ref_id = ? ";
                PreparedStatement ps2 = conn.prepareStatement(query2);
                ps = conn.prepareStatement(query);

                ps.setInt(1, rs.getInt("ref_id"));
                ps2.setInt(1, rs.getInt("ref_id"));
                ps2.executeUpdate();
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    System.out.println(RED + "<NOTIFICATION> " + RESET + GREEN + userDb.getUserName(rs.getInt("by_user_id")) + " send " + rs.getString("type") + " '" + rs2.getString("message") + "' to You at" + rs.getTimestamp("created_at") + RESET);
                } else {
                    System.out.println("no message found");
                    break;
                }
            }

            if (rs.getString("type").equalsIgnoreCase("comments")) {

                String query3 = "Update notifications set seen =true where ref_id = ? ";
                PreparedStatement ps2 = conn.prepareStatement(query3);
                ps2.setInt(1, rs.getInt("ref_id"));
                ps2.executeUpdate();

                String query = "Select content from comments where c_id = ? ";

                ps = conn.prepareStatement(query);
                ps.setInt(1, rs.getInt("ref_id"));
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    System.out.println(RED + "<NOTIFICATION> " + BLUE + userDb.getUserName(rs.getInt("by_user_id")) + " Commented '" + " " + rs2.getString("content") + "' on  your Post at" + rs.getTimestamp("created_at") + RESET);
                } else {
                    System.out.println("No comments found");
                    break;
                }
            }
            if (rs.getString("type").equalsIgnoreCase("like")) {

                String query3 = "Update notifications set seen =true where ref_id = ? ";
                PreparedStatement ps2 = conn.prepareStatement(query3);
                ps2.setInt(1, rs.getInt("ref_id"));
                ps2.executeUpdate();

                System.out.println(RED + "<NOTIFICATION> " + YELLOW + userDb.getUserName(rs.getInt("by_user_id")) + " Liked " + " your Post at " + rs.getTimestamp("created_at") + RESET);

            }
        }
    }
}




