package com.TextIt.inbox;

import com.TextIt.database.DataBase;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class NotificationListener implements Runnable {
    private static final DataBase DATA_BASE = new DataBase();
    private final PGConnection pgConn;
    private final String username;

    public NotificationListener(String username) throws Exception {
        Connection conn = DriverManager.getConnection(DATA_BASE.getUrl(), DATA_BASE.getUsername(), DATA_BASE.getPassword());
        this.pgConn = conn.unwrap(PGConnection.class);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("LISTEN new_message");
            stmt.execute("LISTEN new_comment");
        }
        this.username = username;
    }
    //public NotificationListener(){}

    @Override
    public void run() {
        try {
            while (true) {
                PGNotification[] notifications = pgConn.getNotifications();
                if (notifications != null) {
                    for (PGNotification n : notifications) {
                        String[] parts = n.getParameter().split(":", 3);
                        String receiver = parts[0];
                        String sender = parts[1];
                        String content = parts[2];

                        if (receiver.equalsIgnoreCase(username)) {
                            if ("new_message".equals(n.getName())) {
                                System.out.println("\nNew message from " + sender + ": " + content);
                            } else if ("new_comment".equals(n.getName())) {
                                System.out.println("\nNew comment by " + sender + ": " + content);
                            }
                            System.out.print("> "); // keeps CLI prompt
                        }
                    }
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

