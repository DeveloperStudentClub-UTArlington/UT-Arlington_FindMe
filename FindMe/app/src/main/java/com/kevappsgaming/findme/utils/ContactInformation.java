package com.kevappsgaming.findme.utils;

public class ContactInformation {

    private String uid;
    private String firstName;
    private String lastName;


    public ContactInformation(){

    }

    public ContactInformation(String uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
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

}
