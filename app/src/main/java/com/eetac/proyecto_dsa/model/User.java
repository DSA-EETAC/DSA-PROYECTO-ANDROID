package com.eetac.proyecto_dsa.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("password")
    private String password;

    @SerializedName("mail")
    private String mail;

    public User() {}

    public User(String nombre, String password, String mail) {
        this.nombre   = nombre;
        this.password = password;
        this.mail     = mail;
    }

    public String getNombre()   { return nombre; }
    public String getPassword() { return password; }
    public String getMail()     { return mail; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}