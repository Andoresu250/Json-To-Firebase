package com.example.andoresu.firebasejson;


import java.io.Serializable;

@SuppressWarnings("serial")
public class DataEntry implements Serializable {

    private String gender;
    private String fistName;
    private String lastName;
    private String picture;

    public DataEntry() {

    }

    public DataEntry(String gender, String fistName, String lastName, String picture) {
        this.gender = gender;
        this.fistName = fistName;
        this.lastName = lastName;
        this.picture = picture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
