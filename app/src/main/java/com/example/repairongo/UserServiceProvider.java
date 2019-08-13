package com.example.repairongo;

public class UserServiceProvider {

    String serviceProviderId;
    String name;
    String phoneNo;
    String email;
    String serviceType;
    String otherServices;
    String pass;

    public UserServiceProvider(){

    }

    public UserServiceProvider(String serviceProviderId, String name, String phoneNo, String email, String serviceType,String otherServices, String pass){//, String serviceType) {
        this.serviceProviderId = serviceProviderId;
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
        this.serviceType = serviceType;
        this.otherServices=otherServices;
        this.pass=pass;
    }


    public String getServiceType() {
        return serviceType;
    }

    public String getOtherServices() {
        return otherServices;
    }

    public String getPass() {
        return pass;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    //public String getServiceType() {
        //return ServiceType;
    //}
}
