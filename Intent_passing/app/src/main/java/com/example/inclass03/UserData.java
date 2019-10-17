package com.example.inclass03;

import java.io.Serializable;

public class UserData implements Serializable {
    String firstname;
    String lastname;
    String gender;

    public UserData(String firstname, String lastname, String gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
