//package com.TextIt.service.pages;
//
//import com.TextIt.database.DataBase;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//public class Feed {
//    // Load feed from database Post to ArrayList to print in front end.
//    public ArrayList<String> loadAllPost() throws SQLException {
//        DataBase db  = new DataBase();
//            ArrayList<String> postArry  = new ArrayList<>();
//        try(Connection con = DriverManager.getConnection(db.getUrl(),db.getUsername(),db.getPassword())){
//           String query = "Select content from  Posts ; ";
//            Statement st = con.createStatement();
//            ResultSet rs =st.executeQuery(query);
//            int i = 0;
//            while (rs.next()){
//                postArry.add(i, rs.getString(1));
//                i++;
//            }
//        }catch (Exception e ){
//            System.out.println(e.getMessage());
//        }
//       return  postArry;
//    }
//}
