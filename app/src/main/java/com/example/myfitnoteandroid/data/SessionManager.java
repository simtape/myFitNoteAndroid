package com.example.myfitnoteandroid.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    User user;
    String SHARED_PREF_NAME = "editor";
    String SESSION_KEY = "session_user";
    String SESSION_NAME = "session_name";
    String SESSION_SURNAME = "session_surname";
    String SESSION_MAIL = "session_mail";
    String REGISTRATION_PASSWORD = "session_password";
    String REGISTRATION_PESO = "registration_peso";
    String REGISTRATION_ALTEZZA = "registration_altezza";
    String REGISTRATION_DATA = "registration_data";
    String LAST_ACCESS = "last_access_user";
    String FAVOURITE_SHEET = "favourite_sheet";


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void saveSession(User user) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        editor.putInt(LAST_ACCESS, calendar.DATE);
        this.user = user;
        editor.putString(SESSION_KEY, this.user.getId()).commit();
        editor.putString(SESSION_NAME, this.user.getName()).commit();
        editor.putString(SESSION_SURNAME, this.user.getSurname()).commit();
        editor.putString(SESSION_MAIL, this.user.getMail()).commit();

    }

    public void updateUser() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        editor.putInt(LAST_ACCESS, calendar.DATE);
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

    public String getMail() {
        return sharedPreferences.getString(SESSION_MAIL, null);

    }

    public User getUser() {
        return this.user;

    }

    public void setSessionName(String name) {
        editor.putString(SESSION_NAME, name).commit();

    }

    public void setSessionSurname(String surname) {
        editor.putString(SESSION_SURNAME, surname).commit();

    }

    public void setSessionMail(String mail) {
        editor.putString(SESSION_MAIL, null).commit();
        editor.putString(SESSION_MAIL, mail).commit();
    }

    public void setSessionPassword(String password) {
        editor.putString(REGISTRATION_PASSWORD, password).commit();

    }

    public String getPassword() {
        return sharedPreferences.getString(REGISTRATION_PASSWORD, null);

    }

    public String getPeso() {

        return sharedPreferences.getString(REGISTRATION_PESO, null);
    }

    public String getAltezza() {
        return sharedPreferences.getString(REGISTRATION_ALTEZZA, null);

    }

    public void setPeso(String peso) {
        editor.putString(REGISTRATION_PESO, peso).commit();
    }

    public void setAltezza(String altezza) {
        editor.putString(REGISTRATION_ALTEZZA, altezza).commit();
    }

    public String getData() {
        return sharedPreferences.getString(REGISTRATION_DATA, null);
    }

    public void setData(String data) {
        editor.putString(REGISTRATION_DATA, data).commit();
    }

    public void setLastAccess(int newAccess) {
        //Log.d("datas attuale salvata", String.valueOf(sharedPreferences.getInt(LAST_ACCESS, 0)));
        if (newAccess != sharedPreferences.getInt(LAST_ACCESS, 0)) {
            editor.putInt(LAST_ACCESS, newAccess).commit();
            Log.d("datas AC", String.valueOf(sharedPreferences.getInt(LAST_ACCESS, 0)));
        }
    }


    public void setLastAccessNoControl(int newAccess) {
        //Log.d("datas attuale salvata", String.valueOf(sharedPreferences.getInt(LAST_ACCESS, 0)));
        editor.putInt(LAST_ACCESS, newAccess).commit();
        Log.d("datas NoAC", String.valueOf(sharedPreferences.getInt(LAST_ACCESS, 0)));

    }

    public int getAccess() {

        return sharedPreferences.getInt(LAST_ACCESS, 0);
    }

    public int getFavouriteSheet(){
        return sharedPreferences.getInt(FAVOURITE_SHEET, 0);
    }
    public void setFavouriteSheet(int position){

            editor.putInt(FAVOURITE_SHEET, position).commit();

    }

    public void removeFavourite(){

        editor.putInt(FAVOURITE_SHEET, -1).commit();
    }

}
