package com.TextIt.service.user;

/**
 * To Store User Data For BackEnd Processes
 */
public class UserData {

    private  String userName;
    private  String realName;
    private  String email;

    public UserData(String userName, String realName,String email) {
        this.userName = userName;
        this.realName = realName;
        this.email = email;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
