package com.TextIt.service.session;

import java.io.*;


/**
 * For Keeping Track of Logged-in User And for easy tracking of it
 * Create The Session To persist
 */
public class SessionManger {


    // It will Load During SignUP
    public SessionManger() {


    }

    public void autoLogin(){
        File file = new File("last_session.txt");
        if (file.exists()) {
            // Do auto Login()
        } else {
            // Do Nothing.
        }
    }


    // During First Time Login
    public void manualLogin(int user_id) {
        try {
            File file = new File("last_session.txt");
            if(file.createNewFile()){
                BufferedWriter bw = new BufferedWriter(new FileWriter("last_session.txt"));
                bw.write(user_id);
            }else{
                System.out.println("File already exists");
            }
        }catch (IOException e){
            System.out.println("Error writing to file(last_session.txt) " + e.getMessage());
        }

    }
}
