package com.eetac.proyecto_dsa.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("password")
    private String password;

    @SerializedName("mail")
    private String mail;

    @SerializedName("monedas")
    private int monedas;

    public User() {}

    public User(String nombre, String password, String mail) {
        this.nombre   = nombre;
        this.password = password;
        this.mail     = mail;
        this.monedas  = 0;
    }

    public User(String nombre, String password, String mail, int monedas) {
        this.nombre   = nombre;
        this.password = password;
        this.mail     = mail;
        this.monedas  = monedas;
    }

    public String getNombre()   { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPassword() { return password; }
    public String getMail()     { return mail; }
    public int getMonedas()    { return monedas; }
    public void setMonedas(int monedas) { this.monedas = monedas; }
}