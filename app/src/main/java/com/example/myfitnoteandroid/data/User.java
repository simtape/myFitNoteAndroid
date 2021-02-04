package com.example.myfitnoteandroid.data;

public class User {

    private String name;
    private String surname;
    private String mail;
    private String id;


    public User(String name, String surname, String mail ) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId(){
        return this.id;

    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }
}
