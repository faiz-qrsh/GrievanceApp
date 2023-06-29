package com.example.grievancecell.Models;

import com.example.grievancecell.Models.Complaint;

import java.util.ArrayList;

public class User {

    String id,name,rollNum,mobNum,password;

    public User(){}

    public User(String id, String name, String rollNum, String mobNum, String password) {
        this.id = id;
        this.name = name;
        this.rollNum = rollNum;
        this.mobNum = mobNum;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNum(String rollNum) {
        this.rollNum = rollNum;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public String getRollNum() {
        return rollNum;
    }

    public String getMobNum() {
        return mobNum;
    }

    public String getPassword() {
        return password;
    }

}
