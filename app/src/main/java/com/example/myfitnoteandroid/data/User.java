package com.example.myfitnoteandroid.data;

public class User {

    private static User instance = new User();

    private User() {

    }

    private String name;
    private String surname;
    private String mail;


    public static User getInstance() {
        return instance;
    }

    public void setCurrentUser(String name, String surname, String mail) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;

    }
}
