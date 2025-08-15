package com.TextIt.UI;

import com.TextIt.database.DataBase;

import java.util.Scanner;


public class ChatUI {
    public static void main(String[] args) throws Exception {
        DataBase db = new DataBase();
        String SenderUserName = "vraj";
        String ReceiveruserName = "Dhruv";
        Thread listenerThread = new Thread(db.new ChatListener(SenderUserName));
        listenerThread.start();

        // Simple CLI send loop

        Scanner sc = new Scanner(System.in);
        DataBase.Chats chat = db.new Chats();
        while (true) {
            System.out.print("> ");
            String msg = sc.nextLine();
            chat.send(SenderUserName, ReceiveruserName,msg);
        }
    }
}
