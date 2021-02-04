package com.example.myfitnoteandroid.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    User user;
    String SHARED_PREF_NAME = "editor";
    String SESSION_KEY = "session_user";
    String SESSION_NAME = "session_name";
    String SESSION_SURNAME = "session_surname";
    String SESSION_MAIL = "session_mail";


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void saveSession(User user) {

        this.user = user;
        editor.putString(SESSION_KEY, this.user.getId()).commit();
        editor.putString(SESSION_NAME, this.user.getName()).commit();
        editor.putString(SESSION_SURNAME, this.user.getSurname()).commit();
        editor.putString(SESSION_MAIL, this.user.getMail()).commit();

    }

    public void updateUser(){
        this.user = new User(getName(), getSurname(), getMail());
        this.user.setId(getSession());
    }
    public String getSession() {
        return sharedPreferences.getString(SESSION_KEY, null);
    }

    public void removeSession() {

        editor.putString(SESSION_KEY, null).commit();
        editor.putString(SESSION_NAME, null).commit();
        editor.putString(SESSION_SURNAME, null).commit();
        editor.putString(SESSION_MAIL, null).commit();
        this.user = null;

    }

    public String getName() {
        return sharedPreferences.getString(SESSION_NAME, null);
    }


    public String getSurname() {
        return sharedPreferences.getString(SESSION_SURNAME, null);
    }

    public String getMail(){
        return sharedPreferences.getString(SESSION_MAIL, null);
    }

    public User getUser(){
        return this.user;

    }
}
