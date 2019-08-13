package com.example.repairongo;

public class UserSign_up {
    String userId;
    String userName;
    String userPhoneNo;
    String userEmail;
    String userPass;
    String userAddress;
    String userCity;
    String userState;
    String userCountry;



    public UserSign_up(){

    }

    public UserSign_up(String userId, String userName, String userPhoneNo, String userEmail,String userAddress, String userPass,String userCity,String userState,String userCountry) {
        this.userId = userId;
        this.userName = userName;
        this.userPhoneNo = userPhoneNo;
        this.userEmail = userEmail;
        this.userAddress=userAddress;
        this.userPass=userPass;
        this.userCity=userCity;
        this.userState=userState;
        this.userCountry=userCountry;

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserState() {
        return userState;
    }

    public String getUserCountry() {
        return userCountry;
    }
}
