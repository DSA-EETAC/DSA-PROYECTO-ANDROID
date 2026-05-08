package com.eetac.proyecto_dsa.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                .putInt("coins_" + email, 500) // 500 monedas iniciales
                .putStringSet("inventory_" + email, new HashSet<>()) // Mochila vacía
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
        int coins = prefs.getInt("coins_" + email, 500);
        Set<String> inventory = prefs.getStringSet("inventory_" + email, new HashSet<>());

        prefs.edit()
                .putString(KEY_LOGGED_EMAIL, email)
                .putString(KEY_LOGGED_USERNAME, username)
                .putInt(KEY_LOGGED_COINS, coins)
                .putStringSet(KEY_LOGGED_INVENTORY, inventory)
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


    // Añade estas constantes
    private static final String KEY_LOGGED_COINS = "logged_coins";
    private static final String KEY_LOGGED_INVENTORY = "logged_inventory";

    // Actualiza saveSession para guardar monedas e inventario
    public void saveSession(String email, String username, int coins, List<String> inventory) {
        prefs.edit()
                .putString(KEY_LOGGED_EMAIL, email)
                .putString(KEY_LOGGED_USERNAME, username)
                .putInt(KEY_LOGGED_COINS, coins)
                .putStringSet(KEY_LOGGED_INVENTORY, new HashSet<>(inventory))
                .apply();
    }

    public int getCoins() {
        return prefs.getInt(KEY_LOGGED_COINS, 0);
    }

    public void updateCoins(int newCoins) {
        String email = prefs.getString(KEY_LOGGED_EMAIL, null);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_LOGGED_COINS, newCoins);
        if (email != null) {
            editor.putInt("coins_" + email, newCoins);
        }
        editor.apply();
    }

    // Metodo para añadir un objeto a la mochila local
    public void añadirAlInventario(String nombreObjeto) {
        String email = prefs.getString(KEY_LOGGED_EMAIL, null);
        
        // Recuperamos la mochila actual de la sesión
        Set<String> inventarioActual = obtenerInventario();
        Set<String> nuevoInventario = new HashSet<>(inventarioActual);
        nuevoInventario.add(nombreObjeto);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_LOGGED_INVENTORY, nuevoInventario);
        
        if (email != null) {
            editor.putStringSet("inventory_" + email, nuevoInventario);
        }
        editor.apply();
    }

    // Metodo para ver qué hay en la mochila
    public Set<String> obtenerInventario() {
        return prefs.getStringSet(KEY_LOGGED_INVENTORY, new HashSet<>());
    }
}