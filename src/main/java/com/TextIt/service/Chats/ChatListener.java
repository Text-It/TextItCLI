package com.TextIt.service.Chats;

import com.TextIt.database.DataBase;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static com.TextIt.model.utils.CommonMethods.*;

public class ChatListener implements Runnable {
    private final Connection conn;
    private final PGConnection pgConn;
    private final String username;

    public ChatListener(String username) throws Exception {
        DataBase dataBase = new DataBase();
        this. conn = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsername(), dataBase.getPassword());

        this.pgConn = conn.unwrap(PGConnection.class);
        this.username = username;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("LISTEN new_message");
        }
    }

    @Override
    public void run() {
        try (Statement stmt = conn.createStatement()) {
            while (true) {
                // force Postgres to deliver pending notifications
                stmt.execute("SELECT 1");

                PGNotification[] notifications = pgConn.getNotifications();

                if (notifications != null ) {
                    for (PGNotification n : notifications) {

                        String[] parts = n.getParameter().split(":", 3);
                        if (parts.length < 3) continue;

                        String receiver = parts[0];
                        String sender = parts[1];
                        String msg = parts[2];

                        if (receiver.trim().equalsIgnoreCase(username.trim())) {
                           // System.out.println();
                            System.out.println(GREEN+sender + ": " + msg+RESET);
                            System.out.print(CYAN+"> "+RESET); // restore prompt
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