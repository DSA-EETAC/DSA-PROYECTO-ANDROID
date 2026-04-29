package com.eetac.proyecto_dsa.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalUserManager {

    private static final String PREFS_NAME = "dungeon_users";
    private static final String KEY_LOGGED_EMAIL = "logged_email";
    private static final String KEY_LOGGED_USERNAME = "logged_username";

    private SharedPreferences prefs;

    public LocalUserManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // -------------------------------------------------------
    // REGISTER — luego aquí irá la llamada a la API
    // -------------------------------------------------------
    public boolean register(String username, String email, String password) {
        if (prefs.contains("user_" + email)) {
            return false;
        }
        prefs.edit()
                .putString("user_" + email, password)
                .putString("username_" + email, username)
                .apply();
        return true;
    }

    // -------------------------------------------------------
    // LOGIN — luego aquí irá la llamada a la API
    // -------------------------------------------------------
    public boolean login(String email, String password) {
        String savedPassword = prefs.getString("user_" + email, null);
        return savedPassword != null && savedPassword.equals(password);
    }

    // -------------------------------------------------------
    // SESIÓN
    // -------------------------------------------------------
    public void saveSession(String email) {
        String username = prefs.getString("username_" + email, email);
        prefs.edit()
                .putString(KEY_LOGGED_EMAIL, email)
                .putString(KEY_LOGGED_USERNAME, username)
                .apply();
    }

    public boolean isLoggedIn() {
        return prefs.contains(KEY_LOGGED_EMAIL);
    }

    public String getLoggedUsername() {
        return prefs.getString(KEY_LOGGED_USERNAME, "Héroe");
    }

    public void logout() {
        prefs.edit()
                .remove(KEY_LOGGED_EMAIL)
                .remove(KEY_LOGGED_USERNAME)
                .apply();
    }
}