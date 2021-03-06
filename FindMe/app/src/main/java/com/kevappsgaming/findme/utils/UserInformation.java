package com.kevappsgaming.findme.utils;

public class UserInformation {

    private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    public UserInformation(){

    }

    public UserInformation(String uid, String firstName, String lastName, String email, String password) {

        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

}
