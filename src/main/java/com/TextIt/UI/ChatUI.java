package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.Message.Messages;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.Chats.ChatListener;
import com.TextIt.service.Chats.Chats;

import java.util.List;
import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;


public class ChatUI {
    private final static DataBase db = new DataBase();
    private final static DataBase.UserData  userdb = db.new UserData();
    private final static DataBase.Profile profiledb = db.new Profile();
    private final static Chats chat = new Chats();
    private final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {





        while (true) {
            System.out.println(BOLD+PURPLE+"-------- MESSAGING --------"+RESET);
            String sender = userdb.getUserName(Integer.parseInt(args[0]));
            System.out.print(YELLOW+"Enter Username you want to chat: "+RESET);
            String receiver = sc.nextLine();
            System.out.println();
            if (profiledb.isAvailable("username", receiver)) {

                List<Messages> history = chat.getMessages(sender, receiver);
                // if there is history then enter
                if (!history.isEmpty()) {
                    System.out.println(RED + "----- Chat History -----" + RESET);
                    for (Messages m : history) {
                        System.out.println(BOLD + BLUE + m.getSender() + ": " + m.getMessage() + RESET);
                    }
                    System.out.println(RED + "------------------------" + RESET);
                }

                // Start chatting

                Thread listenerThread = new Thread(new ChatListener(sender));
                listenerThread.start();

                while (true) {
                    System.out.print(CYAN + "> " + RESET);
                    String msg = sc.nextLine();
                    if (msg.equals("exit")) {
                        System.exit(0);
                    }
                    chat.send(sender, receiver, msg);
                    db.addNotification(Integer.parseInt(args[0]), db.featchId(receiver), "message", CommonMethods.featchIdForNotification(msg, "message"));

                }
            } else {
                System.out.println(RED+"Either invalid username or profile is not available"+RESET);
                Thread.sleep(1500);
                System.out.println(CLEAR_SCREEN);

            }
        }
    }
}
