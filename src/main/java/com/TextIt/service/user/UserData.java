package com.TextIt.service.user;

/**
 * To Store User Data For BackEnd Processes
 */
public class UserData {

    private  String userName;
    private  String realName;
    private  String email;
    private int Date;
    private String Gender;

    public UserData(String userName, String realName,String email , int Date , String Gender) {
        this.userName = userName;
        this.realName = realName;
        this.email = email;
        this.Date = Date;
        this.Gender = Gender;
    }

    public UserData() {
    }

    public  String getUserName() {
        return userName;
    }

    public  String getEmail() {
        return email;
    }

    public  String getRealName() {
        return realName;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public  int getDate() {
        return Date;
    }
    public  String getGender() {
        return Gender;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBio() {
        return "1";
    }

    public int getMemberSince() {
        return 1;
    }

    public int getPostCount() {
        return 1;
    }

    public int getXP() {
        return 1;
    }

    public int getFollowingCount() {
        return 1;
    }

    public int getFollowersCount() {
        return 1;
    }

    public int getLevel() {
        return 1;
    }

    public CharSequence getUserShareCode() {
        return "dad";

    }

    public CharSequence getLocation() {
        return "<UNK>";
    }
}
