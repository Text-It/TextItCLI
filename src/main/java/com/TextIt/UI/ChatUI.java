package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.Message.Messages;
import com.TextIt.model.utils.CommonMethods;

import java.util.List;
import java.util.Scanner;


public class ChatUI {
    private final static DataBase db = new DataBase();
    private final static DataBase.UserData  userdb = db.new UserData();
    private final static DataBase.Profile profiledb = db.new Profile();
    private final static DataBase.Chats chat = db.new Chats();
    private final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {




        System.out.println("------------------------");
        String sender = userdb.getUserName(Integer.parseInt(args[0]));
        System.out.print("Enter Username you want to chat: ");
        String receiver = sc.nextLine();
        if(profiledb.isAvailable("username", receiver)) {

            List<Messages> history = chat.getMessages(sender, receiver);
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
                chat.send(sender,receiver,msg);
                db.addNotification(Integer.parseInt(args[0]),db.featchId(receiver),"message", CommonMethods.featchIdForNotification(msg,"message"));

            }
        }else{
            System.out.println("Either invalid username or profile is not available");
        }





    }
}
