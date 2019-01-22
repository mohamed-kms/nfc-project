package com.example.mohamed.tchololbane.Model;

/**
 * Created by mohamed on 12/12/17.
 */

public class User {
    private String name;
    private String password;
    private String mailto;
    private String Phone;

    public User(){}



    public User(String name, String password, String mailto) {
        this.name = name;
        this.password = password;
        this.mailto = mailto;
    }


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }
}
