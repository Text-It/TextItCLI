package com.TextIt.service.Chats;

import com.TextIt.database.DataBase;
import com.TextIt.model.Message.Messages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Chats{
    private final DataBase dataBase = new DataBase();

    public Chats()  {

    }

    public void send(String sender, String receiver, String msg) throws SQLException {
        Connection conn = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsername(), dataBase.getPassword());
        String sql = "INSERT INTO messages (sender, receiver, message) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, msg);
            ps.executeUpdate();
        }
    }

    // get chat history
    public List<Messages> getMessages(String user1, String user2) throws SQLException {
        Connection conn = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsername(), dataBase.getPassword());
        List<Messages> messages = new ArrayList<>();

        String sql = "SELECT sender, receiver, message, sent_at " + "FROM messages " + "WHERE (sender = ? AND receiver = ?)  ";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user2);
            ps.setString(2, user1);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Messages m = new Messages(
                            rs.getString("sender"),
                            rs.getString("receiver"),
                            rs.getString("message"),
                            rs.getString("sent_at")
                    );
                    messages.add(m);
                }
            }
        }

        return messages;
    }
}