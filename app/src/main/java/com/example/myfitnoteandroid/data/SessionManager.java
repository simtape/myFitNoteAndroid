package com.example.myfitnoteandroid.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "editor";
    String SESSION_KEY = "session_user";
    String id = null;

    public SessionManager(Context context){
    sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();

    }

    public void saveSession(User user){

        id = user.getId();
        editor.putString(SESSION_KEY, id).commit();


    }

    public String getSession(){
        return sharedPreferences.getString(SESSION_KEY, null);
    }

    public void removeSession(){

    editor.putString(SESSION_KEY, null).commit();

    }




}
