package com.example.repairongo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sp_listview {
    public String name;
    public String email;
    public String otherServices;
    public String phone;

    public sp_listview(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("serviceprovider");
    }

    public sp_listview(String name, String email, String otherServices, String phone) {
        this.name = name;
        this.email = email;
        this.otherServices = otherServices;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getOtherServices() {
        return otherServices;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtherServices(String otherServices) {
        this.otherServices = otherServices;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
