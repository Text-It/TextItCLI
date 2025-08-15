package com.TextIt.UI;

import com.TextIt.database.DataBase;

import java.util.Scanner;


public class ChatUI {
    public static void main(String[] args) throws Exception {
        DataBase db = new DataBase();
        DataBase.UserData userdb = db.new UserData();
        DataBase.Profile profiledb = db.new Profile();
        Scanner sc = new Scanner(System.in);


        String sender = userdb.getUserName(Integer.parseInt(args[0]));
        Thread listenerThread = new Thread(db.new ChatListener(sender));
        listenerThread.start();

        System.out.print("Enter Username you want to chat: ");
        String input = sc.nextLine();
        if(profiledb.isAvailable("username", input)) {
                // Start chatting
            DataBase.Chats chat = db.new Chats();

            while (true) {
                System.out.print("> ");
                String msg = sc.nextLine();
                if(msg.equals("exit")) {
                    break;
                }
                chat.send(sender,input,msg);

            }
        }else{
            System.out.println("Either invalid username or profile is not available");
        }


    }
}
