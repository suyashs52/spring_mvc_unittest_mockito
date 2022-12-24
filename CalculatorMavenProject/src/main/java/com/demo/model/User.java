package com.demo.model;

public class User {

    String id;
    String firstName ;
    String  lastName ;
    String email ;
    String password;
    String repeatPass;

    public User(){

    }
    public User(String firstName, String lastName, String email, String password, String repeatPass,String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.repeatPass = repeatPass;
        this.id
                =id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPass() {
        return repeatPass;
    }

    public void setRepeatPass(String repeatPass) {
        this.repeatPass = repeatPass;
    }
}
