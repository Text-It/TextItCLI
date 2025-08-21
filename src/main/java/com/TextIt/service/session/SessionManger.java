package com.TextIt.service.session;


import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * For Keeping Track of Logged-in User And for easy tracking of it
 * Create The Session To persist
 */
public class SessionManger {

    private static int userid;

    // It will Load During SignUP
    public SessionManger() {
    }

    public static int getUserid() {
        return userid;
    }

    public static void setUserid(int userid) {
        SessionManger.userid = userid;
    }

    public boolean autoLogin(){
        File file = new File("last_session.txt");
        if (file.exists()) {
            // Do auto Login()
            FileReader fr = null;
            try {
                fr = new FileReader(file);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
            try(BufferedReader br = new BufferedReader(fr)){
                userid = Integer.parseInt(br.readLine());


                    // Formater for the Date
                    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss.n");
                    // fetch Date used in file
                    LocalDate date = LocalDate.parse(br.readLine(), dateformatter);
                    // fetch Today Date
                    LocalDate currentDate = LocalDate.now();

                    if(date.isBefore(currentDate)){
                        return  false;
                    }
                    // Fetch Time from File
                    LocalTime time = LocalTime.parse(br.readLine(), timeformatter);
                    // Fetch CurrentTime
                    LocalTime currentTime = LocalTime.now();
                    // Difference between time and currentTime Object
                    Duration duration = Duration.between(time, currentTime);
                    // The Session will Last 30 min and Then you have to Login again
                    if(duration.toMinutes() > 30) {
                        return false;
                    }

                return true;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        return false;
    }


    // During First Time Login
    public void manualLogin(int user_id) {
        try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("last_session.txt"));
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                bw.write(String.valueOf(user_id));
                bw.newLine();
                bw.write(String.valueOf(localDate));
                bw.newLine();
                bw.write(String.valueOf(localTime));
                bw.flush();
                bw.close();

        }catch (IOException e){
            System.out.println("Error writing to file(last_session.txt) " + e.getMessage());
        }

    }

}
