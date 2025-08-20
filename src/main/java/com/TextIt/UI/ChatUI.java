package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.Message.Messages;
import java.util.List;
import java.util.Scanner;


public class ChatUI {
    public static void main(String[] args) throws Exception {
        DataBase db = new DataBase();
        DataBase.UserData userdb = db.new UserData();
        DataBase.Profile profiledb = db.new Profile();
        DataBase.Chats chat = db.new Chats();
        Scanner sc = new Scanner(System.in);



        System.out.println("------------------------");
        String sender = userdb.getUserName(Integer.parseInt(args[0]));
        System.out.print("Enter Username you want to chat: ");
        String input = sc.nextLine();
        if(profiledb.isAvailable("username", input)) {

            List<Messages> history = chat.getMessages(sender, input);
            System.out.println("----- Chat History -----");
            for (Messages m : history) {
                System.out.println(m.getSender() + ": " + m.getMessage());
            }
            // Start chatting

            Thread listenerThread = new Thread(db.new ChatListener(sender));
            listenerThread.start();

            while (true) {
                System.out.print("> ");
                String msg = sc.nextLine();
                if(msg.equals("exit")) {
                    System.exit(0);
                }
                chat.send(sender,input,msg);

            }
        }else{
            System.out.println("Either invalid username or profile is not available");
        }





    }
}
